package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sample")
@Controller
public class SampleController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello Sample";
    }
}
