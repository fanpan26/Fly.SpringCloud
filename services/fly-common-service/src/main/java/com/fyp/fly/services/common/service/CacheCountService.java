package com.fyp.fly.services.common.service;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String COUNT_KEY_PREFIX = "service:common:count:";

    /**
     * 采用hash结构保存计数
     * Key: service:common:count:article_browse_1   k:bizId v:count
     * 10000001   34 (帖子ID 10000001，浏览数 34)
     * 10000002   58 (帖子ID 10000002，浏览数 58)
     */
    private HashOperations<String, String, String> countOps() {
        return redisTemplate.opsForHash();
    }

    private String getKeyByBizType(int type, Long bizId) {
        String key = CountBizType.valueOf(type).getKey();
        long sequence = bizId / bucketSize;
        return String.format("%s%s_%d", COUNT_KEY_PREFIX, key, sequence);
    }

    /**
     * 添加一条计数
     */
    @Override
    public JsonResult increment(int type, Long bizId) {
        return changeCount(type,bizId,1L);
    }

    @Override
    public JsonResult decrement(int type, Long bizId) {
       return changeCount(type,bizId,-1L);
    }

    private JsonResult changeCount(int type, Long bizId,Long count) {
        String key = getKeyByBizType(type, bizId);
        //如果redis缓存丢失，会有数据不准的问题，不处理，交给计划去做，检查
        Long res = countOps().increment(key, bizId.toString(), count);
        return ResultUtils.success(res);
    }


    private static final JsonResult<List<CountDto>> EMPTY_COUNT_RESULT = ResultUtils.success(new ArrayList<CountDto>(0));

    @Override
    public JsonResult<List<CountDto>> getListByBizIds(int bizType, List<Long> bizIds) {
        if (bizIds.size() == 0) {
            return EMPTY_COUNT_RESULT;
        }
        //先计算key，在取值。
        Map<String, List<String>> keyMaps = new HashMap<>(bizIds.size());
        for (Long id : bizIds) {
            String key = getKeyByBizType(bizType, id);
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
        List<CountDto> resultList = new ArrayList<>(bizIds.size());
        for (Map.Entry<String, List<String>> entry : keyMaps.entrySet()) {
            List<String> values = countOps().multiGet(entry.getKey(), entry.getValue());
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    //ignore null value or use default 0 ?
                    if (values.get(i) != null) {
                        resultList.add(new CountDto(bizType, Long.valueOf(entry.getValue().get(i)), Integer.valueOf(values.get(i))));
                    }
                }
            }
        }
        return ResultUtils.success(resultList);
    }

    @Override
    public JsonResult<List<CountDto>> getListByBizTypes(Long bizId, List<Integer> bizTypes) {
        List<CountDto> resultList = new ArrayList<>(bizTypes.size());
        for (Integer bizType : bizTypes) {
            String key = getKeyByBizType(bizType, bizId);
            String value = countOps().get(key, bizId.toString());
            resultList.add(new CountDto(bizType, bizId, value == null ? 0 : Integer.valueOf(value)));
        }
        return ResultUtils.success(resultList);
    }
}
