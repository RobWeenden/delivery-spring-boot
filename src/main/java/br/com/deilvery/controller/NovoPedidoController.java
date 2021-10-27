package br.com.deilvery.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.deilvery.domain.Cliente;
import br.com.deilvery.dto.RespostaDTO;
import br.com.deilvery.repository.ClienteRepository;
import br.com.deilvery.repository.ItemRepository;

@RestController
public class NovoPedidoController {
	private final ClienteRepository clienteRepository;
	private final ItemRepository itemRepository;

	public NovoPedidoController(ClienteRepository clienteRepository, ItemRepository itemRepository) {
		this.clienteRepository = clienteRepository;
		this.itemRepository = itemRepository;
	}
	
//	public RespostaDTO novo(@PathVariable("clienteId") Long clienteId, @PathVariable("listaDeItens") String listaDeItens) {
//		RespostaDTO dto = new RespostaDTO();
//		
//		Cliente c = clienteRepository.findOne(clienteId);
//		String[] listaDeItensID = listaDeItens.split(",");
//		
//	}
}
