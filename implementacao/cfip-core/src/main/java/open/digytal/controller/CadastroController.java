package open.digytal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.service.CadastroService;

@Controller
public class CadastroController implements CadastroService {
	@Autowired
	private ContaRepository repository;

	@Autowired
	private NaturezaRepository naturezaRepository;

	public List<EntidadeConta> listarCartoesCredito(String login) {
		return repository.listarCartoesCredito(login);
	}
	public List<EntidadeConta> listarCorrentesPoupanca(String login) {
		return repository.listarCorrentesPoupanca(login);
	}

	public List<EntidadeConta> listarContas(Integer id) {
		return repository.listar(id);
	}

	public List<EntidadeConta> listarContas(String login, String nome) {
		if (login == null || login.trim().isEmpty())
			return repository.listar(login);
		else
			return repository.listar(login, nome);
	}

	public List<EntidadeNatureza> listarNaturezas(String login, String nome) {
		if (login == null || login.trim().isEmpty())
			return naturezaRepository.listar(login);
		else
			return naturezaRepository.listar(login, nome);
	}

	public List<EntidadeNatureza> listarNaturezas(String login, TipoMovimento tipo) {
		return naturezaRepository.listar(login, tipo);
	}

	@Override
	public EntidadeConta salvarConta(EntidadeConta entidade) {
		return repository.save(entidade);
	}

	@Override
	public EntidadeNatureza salvarNatureza(EntidadeNatureza entidade) {
		return naturezaRepository.save(entidade);
	}
}