package com.example.java.was.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping("/basic")
	public String textBasic(Model model) {
		//HttpServletRequest request -> request.setAttribute
		model.addAttribute("data", "Hello Spring!");
		//System.out.println("11");
		return "test";
	}
}
