package br.com.deilvery.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.deilvery.domain.Cliente;
import br.com.deilvery.domain.Item;
import br.com.deilvery.domain.Pedido;
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
	
	@GetMapping("/rest/pedido/novo/{clienteId}/{listaDeItens}")
	public RespostaDTO novo(@PathVariable("clienteId") Long clienteId,
			@PathVariable("listaDeItens") String listaDeItens) {
		RespostaDTO dto = new RespostaDTO();
		try {
			Optional<Cliente> optCl = clienteRepository.findById(clienteId);
			Cliente c = optCl.get();
			String[] listaDeItensID = listaDeItens.split(",");

			Pedido pedido = new Pedido();
			double valorTotal = 0;

			List<Item> itensPedidos = new ArrayList<Item>();
			for (String itemId : listaDeItensID) {
				Optional<Item> optItem = itemRepository.findById(Long.parseLong(itemId));
				Item item = optItem.get();

				itensPedidos.add(item);
				valorTotal += item.getPreco();
			}

			pedido.setItens(itensPedidos);
			pedido.setValorTotal(valorTotal);
			pedido.setData(new Date());
			pedido.setCliente(c);
			c.getPedidos().add(pedido);

			this.clienteRepository.saveAndFlush(c);

			List<Long> pedidosID = new ArrayList<Long>();
			for (Pedido p : c.getPedidos()) {
				pedidosID.add(p.getId());
			}
			Long ultimoPedido = Collections.max(pedidosID);
			dto = new RespostaDTO(ultimoPedido, valorTotal, "Pedido efetuado com sucesso");
		} catch (Exception ex) {
			dto.setMensagem("Erro: " + ex.getMessage());
		}
		return dto;
	}
}
