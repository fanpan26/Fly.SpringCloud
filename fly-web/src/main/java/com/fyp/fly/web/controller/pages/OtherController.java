package com.fyp.fly.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fyp
 * @crate 2019/3/17 22:44
 * @project fly
 */
@Controller
public class OtherController {

    @GetMapping("/404")
    public String pageNotFound(){
        return "404";
    }
}
