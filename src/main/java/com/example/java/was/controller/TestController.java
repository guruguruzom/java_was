package com.example.java.was.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*@Controller*/
public class TestController {
	/* @GetMapping("/basic") */
//	public String textBasic(Model model) {
//		//HttpServletRequest request -> request.setAttribute
//		model.addAttribute("data", "Hello Spring!");
//		
//		List<String> error = new ArrayList<>();
//		String aa = error.get(2);
//		return "test";
//	}
	
	public String textBasic() {
		//HttpServletRequest request -> request.setAttribute
		//model.addAttribute("data", "Hello Spring!");
		System.out.println("Hello Spring!");
		
		return "test";
	}
}
