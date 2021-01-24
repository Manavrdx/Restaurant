package com.company.Restaurants.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
	
	@GetMapping("/")
	public String mainpage(Model theModel) {
		
		return "redirect:/restaurant/main";
	}
	
}
