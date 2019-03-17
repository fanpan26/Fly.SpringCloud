package com.fyp.fly.services.common.repository.mapper;

import com.fyp.fly.services.common.domain.Count;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/17 9:57
 * @project fly
 */
public interface CountMapper {

    @InsertProvider(type = CountProvider.class, method = "batchInsert")
    void add(@Param("list") List<Count> counts);
}
