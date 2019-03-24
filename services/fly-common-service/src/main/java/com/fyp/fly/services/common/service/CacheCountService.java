package com.fyp.fly.services.common.service;

import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fyp
 * @crate 2019/3/17 10:03
 * @project fly
 */
@Service
public class CacheCountService implements CountService {

    @Value("${count.bucket.size}")
    private Long bucketSize;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ZSetOperations<String, Object> zsetOps;

    /**
     * 采用hash结构保存计数
     * Key: service:common:count:article_browse_1   k:bizId v:count
     * 10000001   34 (帖子ID 10000001，浏览数 34)
     * 10000002   58 (帖子ID 10000002，浏览数 58)
     */
    @Autowired
    private HashOperations<String, String, String> hashOps;

    private static final String CACHE_COUNT_KEY_PREFIX = "service:common:count:";
    private static final String CACHE_COUNT_SORTED_KEY_PREFIX = "service:common:sortedcount:";


    private String getCountKeyByBizType(int type, Long bizId) {
        String key = CountBizType.valueOf(type).getKey();
        long sequence = bizId / bucketSize;
        return String.format("%s%s_%d", CACHE_COUNT_KEY_PREFIX, key, sequence);
    }

    private String getSortedCountKeyByBizType(int type) {
        String key = CountBizType.valueOf(type).getKey();
        return String.format("%s%s_%d", CACHE_COUNT_SORTED_KEY_PREFIX, key, 0);
    }

    /**
     * 添加一条计数
     */
    @Override
    public JsonResult increment(int type, Long bizId) {
        return changeCount(type, bizId, 1L);
    }

    @Override
    public JsonResult decrement(int type, Long bizId) {
        return changeCount(type, bizId, -1L);
    }

    private JsonResult changeCount(int type, Long bizId, Long count) {
        String key = getCountKeyByBizType(type, bizId);
        Long newCount = hashOps.increment(key, bizId.toString(), count);

        addSortedRecords(type, bizId, newCount);
        return ResultUtils.success(newCount);
    }

    /**
     * 添加可以排序的缓存
     */
    private void addSortedRecords(int type, Long bizId, Long count) {
        String key = getSortedCountKeyByBizType(type);
        zsetOps.add(key, bizId, count);
    }


    private static final JsonResult<List<CountVo>> EMPTY_COUNT_RESULT = ResultUtils.success(new ArrayList<CountVo>(0));

    @Override
    public JsonResult<List<CountVo>> getListByBizIds(int bizType, List<Long> bizIds) {
        if (bizIds.size() == 0) {
            return EMPTY_COUNT_RESULT;
        }
        //先计算key，在取值。
        Map<String, List<String>> keyMaps = new HashMap<>(bizIds.size());
        for (Long id : bizIds) {
            String key = getCountKeyByBizType(bizType, id);
            List<String> idList;
            if (!keyMaps.containsKey(key)) {
                idList = new ArrayList<>(bizIds.size());
                idList.add(id.toString());

            } else {
                idList = keyMaps.get(key);
                idList.add(id.toString());
            }
            keyMaps.put(key, idList);
        }
        List<CountVo> resultList = new ArrayList<>(bizIds.size());
        for (Map.Entry<String, List<String>> entry : keyMaps.entrySet()) {
            List<String> values = hashOps.multiGet(entry.getKey(), entry.getValue());
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    //ignore null value or use default 0 ?
                    if (values.get(i) != null) {
                        resultList.add(new CountVo(bizType, Long.valueOf(entry.getValue().get(i)), Integer.valueOf(values.get(i))));
                    }
                }
            }
        }
        return ResultUtils.success(resultList);
    }

    @Override
    public JsonResult<List<CountVo>> getListByBizTypes(Long bizId, List<Integer> bizTypes) {
        List<CountVo> resultList = new ArrayList<>(bizTypes.size());
        for (Integer bizType : bizTypes) {
            String key = getCountKeyByBizType(bizType, bizId);
            String value = hashOps.get(key, bizId.toString());
            resultList.add(new CountVo(bizType, bizId, value == null ? 0 : Integer.valueOf(value)));
        }
        return ResultUtils.success(resultList);
    }

    @Override
    public JsonResult<List<CountVo>> getSortedListByBizType(Integer bizType, Integer start, Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = start + 10;
        }
        String key = getSortedCountKeyByBizType(bizType);

        Set<ZSetOperations.TypedTuple<Object>> values = zsetOps.reverseRangeWithScores(key, start, end);

        List<CountVo> results = values.stream().map(v ->
                new CountVo(CountBizType.ARTICLE_COMMENT.getCode()
                        , (Long) v.getValue()
                        , v.getScore().intValue()))
                .collect(Collectors.toList());

        return ResultUtils.success(results);
    }
}
