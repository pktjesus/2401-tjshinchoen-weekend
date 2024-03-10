package com.test1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
	
	@GetMapping("/pages/main")
	public ModelAndView pageMain() {
		return new ModelAndView("main");	// main.html
	}
}
