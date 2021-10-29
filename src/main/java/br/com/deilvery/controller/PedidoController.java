package br.com.deilvery.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import com.jayway.jsonpath.Option;

import br.com.deilvery.domain.Cliente;
import br.com.deilvery.domain.Item;
import br.com.deilvery.domain.Pedido;
import br.com.deilvery.repository.ClienteRepository;
import br.com.deilvery.repository.ItemRepository;
import br.com.deilvery.repository.PedidoRepository;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	private final ItemRepository itemRepository;
	private final ClienteRepository clienteRepository;
	private final PedidoRepository pedidoRepository;
	private final String ITEM_URI = "pedidos/";

	public PedidoController(PedidoRepository pedidoRepository, ItemRepository itemRepository,
			ClienteRepository clienteRepository) {
		this.pedidoRepository = pedidoRepository;
		this.itemRepository = itemRepository;
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/")
	public ModelAndView list() {
		Iterable<Pedido> pedidos = this.pedidoRepository.findAll();
		return new ModelAndView(ITEM_URI + "list", "pedidos", pedidos);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Pedido pedido) {
		return new ModelAndView(ITEM_URI + "view", "pedido", pedido);
	}

	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Pedido pedido) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("todosItens", itemRepository.findAll());
		model.put("todosClientes", clienteRepository.findAll());

		return new ModelAndView(ITEM_URI + "form", model);
	}

	@PostMapping(params = "form")
	public ModelAndView create(@Valid Pedido pedido,BindingResult result,RedirectAttributes redirect) {
		if (result.hasErrors()) { return new ModelAndView(ITEM_URI + "form","formErrors",result.getAllErrors()); }
		
		if(pedido.getId() != null) {
			Optional<Pedido> pedidoOpt = pedidoRepository.findById(pedido.getId());
			Pedido pd = pedidoOpt.get();
			
			Optional<Cliente> clienteOpt = clienteRepository.findById(pd.getCliente().getId());
			Cliente cl = clienteOpt.get();
			
			pd.setItens(pedido.getItens());
			double valorTotal =0;
			for(Item i: pedido.getItens()) {
				valorTotal+=i.getPreco();
			}
			pd.setData(pedido.getData());
			pd.setValorTotal(valorTotal);
			
			cl.getPedidos().remove(pd);
			cl.getPedidos().add(pd);
			
			this.clienteRepository.save(cl);
		}else {
			Optional<Cliente> clOpt = clienteRepository.findById(pedido.getCliente().getId());
			Cliente c = clOpt.get();
			
			double valorTotal =0;
			for(Item i : pedido.getItens()) {
				valorTotal+=i.getPreco();
			}
			pedido.setValorTotal(valorTotal);
			pedido = this.pedidoRepository.save(pedido);
			c.getPedidos().add(pedido);
			this.clienteRepository.save(c);
			
		}
		//pedido = this.pedidoRepository.save(pedido);
		redirect.addFlashAttribute("globalMessage","Pedido gravado com sucesso");
		return new ModelAndView("redirect:/" + ITEM_URI + "{pedido.id}","pedido.id",pedido.getId());
	}

	@GetMapping(value = "alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Pedido pedido) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("todosItens", itemRepository.findAll());
		model.put("todosClientes", clienteRepository.findAll());
		model.put("pedido", pedido);
		
		return new ModelAndView(ITEM_URI + "form", model);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		
		Optional<Pedido> optPedidoRemove = pedidoRepository.findById(id);
		Pedido removePedido = optPedidoRemove.get();
		
		Optional<Cliente> opCliente = clienteRepository.findById(removePedido.getCliente().getId());
		Cliente cl = opCliente.get();
		
		this.clienteRepository.save(cl);
		this.pedidoRepository.deleteById(id);
		
		Iterable<Pedido> pedidos = this.pedidoRepository.findAll();

		ModelAndView mv = new ModelAndView(ITEM_URI + "list", "pedidos", pedidos);
		mv.addObject("globalMessage", "Pedido removido com sucesso");

		return mv;
	}
}
