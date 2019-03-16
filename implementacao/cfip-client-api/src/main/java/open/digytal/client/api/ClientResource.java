package open.digytal.client.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import open.digytal.model.Sessao;
import open.digytal.util.Texto;

public abstract class ClientResource {
	private final String ROOT_URL = "http://localhost:8080/";
	private String getUrl(Serializable... path) {
		String sufix = Texto.concatenar("/", path);
		String url = String.format("%s%s", ROOT_URL, sufix);
		return url;
	}	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				if (Sessao.getInstance()!=null && Sessao.getInstance().ativa() ) {
					request.getHeaders().set("Authorization", Sessao.getInstance().getToken());
				}

				return execution.execute(request, body);
			}
		});
		return restTemplate;
	}

	protected <T> T post(Object request, Serializable... path) {
		return post(request.getClass(), request, path);
	}

	protected <T> T post(Class response, Object request, Serializable... path) {
		String url = getUrl(path);
		System.out.println("POST --> " + url);
		Object result = getRestTemplate().postForObject(url, request, response);
		return (T) result;
	}

	protected <T> T put(Object entidade, Serializable... path) {
		String url = getUrl(path);
		System.out.println("PUT --> " + url);
		getRestTemplate().put(url, entidade);
		return (T) entidade;
	}

	protected <T> T get(ParameterizedTypeReference type, Serializable... path) {
		String url = getUrl(path);
		System.out.println("GET --> " + url);
		ResponseEntity<T> resposta = getRestTemplate().exchange(url, HttpMethod.GET, null, type);
		Object entidade = resposta.getBody();
		return (T) entidade;
	}

	protected List getLista(ParameterizedTypeReference type, Serializable... path) {
		String url = getUrl(path);
		System.out.println("GET LISTA --> " + url);
		ResponseEntity<List> resposta = getRestTemplate().exchange(url, HttpMethod.GET, null, type);
		List lista = resposta.getBody();
		return lista;
	}
	/*
	 * @Override public <T> T incluir(Object entidade) { return post(entidade); }
	 * 
	 * @Override public <T> T alterar(Object entidade) { return put(entidade); }
	 * 
	 * @Override public <T> T buscar(Class classe, Serializable id) { return
	 * get(getEntidadeType(),id); }
	 */
}