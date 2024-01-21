package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/ghello")
    @ResponseBody
    public String gethello() {
        return "Hello World6(GET)";
    }

    @GetMapping("/jump")
    @ResponseBody
    public String gethello2() {
        return "점프 투 스프링 부트";
    }

    @PostMapping("/phello")
    @ResponseBody
    public String posthello() {
        return "Hello World(POST)";
    }
}
