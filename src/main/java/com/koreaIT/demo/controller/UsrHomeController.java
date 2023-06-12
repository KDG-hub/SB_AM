package com.koreaIT.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsrHomeController {
	@RequestMapping("/usr/home/main")
	public String showMain(Model model, @RequestParam(defaultValue = "") String searchKeyword) {
		model.addAttribute("searchKeyword", searchKeyword);
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
}
