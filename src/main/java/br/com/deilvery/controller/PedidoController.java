package br.com.deilvery.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.deilvery.domain.Item;
import br.com.deilvery.domain.Pedido;
import br.com.deilvery.repository.ItemRepository;
import br.com.deilvery.repository.PedidoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	//private final ItemRepository itemRepository;
	private final PedidoRepository pedidoRepository;
	private final String ITEM_URI = "pedidos/";
	
	public PedidoController(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	@GetMapping("/")
	public ModelAndView list() {
		Iterable<Pedido> pedidos = this.pedidoRepository.findAll();
		return new ModelAndView(ITEM_URI + "list","pedidos",pedidos);
	}
	
	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Pedido pedido) {
		return new ModelAndView(ITEM_URI + "view","pedido",pedido);
	}
	
	@GetMapping("/novo")
	public String createForm(@ModelAttribute Pedido pedido) {
		return ITEM_URI + "form";
	}
	
	@PostMapping(params = "form")
	public ModelAndView create(@Valid Pedido pedido,BindingResult result,RedirectAttributes redirect) {
		if (result.hasErrors()) { return new ModelAndView(ITEM_URI + "form","formErrors",result.getAllErrors()); }
		pedido = this.pedidoRepository.save(pedido);
		redirect.addFlashAttribute("globalMessage","Pedido gravado com sucesso");
		return new ModelAndView("redirect:/" + ITEM_URI + "{pedido.id}","pedido.id",pedido.getId());
	}
	
	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Pedido pedido) {
		return new ModelAndView(ITEM_URI + "form","pedido",pedido);
	}
	
	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id,RedirectAttributes redirect) {
		this.pedidoRepository.deleteById(id);
		Iterable<Pedido> pedidos = this.pedidoRepository.findAll();
		
		ModelAndView mv = new ModelAndView(ITEM_URI + "list","pedidos",pedidos);
		mv.addObject("globalMessage","Pedido removido com sucesso");
	
		return mv;
	}
}
