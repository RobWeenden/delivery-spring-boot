package br.com.deilvery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/ambiente")
	public String ambiente() {
		return "ambiente";
	}
}
