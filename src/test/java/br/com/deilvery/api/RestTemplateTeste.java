package br.com.deilvery.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.deilvery.domain.CEP;

@SpringBootTest
public class RestTemplateTeste {
	
	@Test
	public void testPedidoCompleto() {
		RestTemplate restTemplate = new RestTemplate();
		String cepURL = "https://viacep.com.br/ws/49037475/json/";
		ResponseEntity<CEP> responseCEP = restTemplate.getForEntity(cepURL, CEP.class);
		
		boolean resultado = false;
		CEP wsCEP = responseCEP.getBody();
		if(wsCEP != null) {
			if(wsCEP.getUf().equals("SE") && wsCEP.getLocalidade().equals("Aracaju")) {
				
			}
		}


	}
}
