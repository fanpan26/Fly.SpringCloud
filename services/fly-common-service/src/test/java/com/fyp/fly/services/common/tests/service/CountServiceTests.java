package com.fyp.fly.services.common.tests.service;

import com.fyp.fly.services.common.service.CountService;
import com.fyp.fly.services.common.tests.CommonServiceApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fyp
 * @crate 2019/3/17 10:55
 * @project fly
 */
public class CountServiceTests extends CommonServiceApplicationTests {

    @Autowired
    private CountService countService;

    @Test
    public void addCountTest(){
        countService.add(1,100001L);
    }
}
