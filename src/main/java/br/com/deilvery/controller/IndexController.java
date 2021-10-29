package br.com.deilvery.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.deilvery.dto.MensagemDTO;

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
	
	@GetMapping("/delivery")
	public String delivery() {
		return "delivery/index";
	}
	
	@Value("${mensagem:nenhuma}")
	private String message;
	
	@Value("${debug:0}")
	private String debug;
	
	@GetMapping("/oferta")
	@ResponseBody
	public MensagemDTO getMessage(HttpServletRequest request) {
		return new MensagemDTO(this.message, request.getServerName() + " : "+ request.getServerPort(), this.debug);
	}
	
	@GetMapping("/servidor")
	@ResponseBody
	public String server(HttpServletRequest request) {
		System.out.println(">>> Chamada end-point servidor...");
		return  request.getServerName() + " : " + request.getServerPort();
	}
}
