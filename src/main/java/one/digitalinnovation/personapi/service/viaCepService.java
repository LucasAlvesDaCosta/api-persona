package one.digitalinnovation.personapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import one.digitalinnovation.personapi.entity.Address;

@Service
public class viaCepService {

	private RestTemplate restTemplate = new RestTemplate();

	public Address viaCep(String cep) {

		StringBuilder stringBuilder = new StringBuilder(100);
		stringBuilder.append("https://viacep.com.br/ws/");
		stringBuilder.append(cep);
		stringBuilder.append("/json/");

		String url = stringBuilder.toString();
		Address endereco = restTemplate.getForObject(url, Address.class);

		return endereco;
	}
}
