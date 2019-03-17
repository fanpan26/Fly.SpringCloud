package com.fyp.fly.services.common.repository.mapper;

import com.fyp.fly.services.common.domain.Count;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author fyp
 * @crate 2019/3/17 10:13
 * @project fly
 */
public class CountProvider {

    /**
     * 构造批量插入语句
     * */
    public String batchInsert(Map map) {
        List<Count> users = (List<Count>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `fly_count` (`id`,`biz_id`,`biz_type`,`biz_count`,`create_at`,`udpate_at`)");
        sb.append("VALUES ");
        MessageFormat mf = new MessageFormat("(#{list[{0}].id},#{list[{0}].bizId},#{list[{0}].bizType},#{list[{0}].bizCount},#{list[{0}].createAt},#{list[{0}].updateAt})");
        for (int i = 0; i < users.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < users.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
