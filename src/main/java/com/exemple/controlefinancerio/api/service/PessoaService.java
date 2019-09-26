package com.exemple.controlefinancerio.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.exemple.controlefinancerio.api.model.Pessoa;
import com.exemple.controlefinancerio.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(codigo);
		Pessoa conpessoaSalva = pessoaSalva.get(); // aqui eu converto pessoa salva para a entidade Pessoa. pq save
													// aceita apenas Pessoa.

		BeanUtils.copyProperties(pessoa, conpessoaSalva, "codigo");
		return pessoaRepository.save(conpessoaSalva);
	}

	public void AtualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = buscarPessoaPeloCodigo(codigo);
		Pessoa pessoaCon = pessoaSalva.get();
		pessoaCon.setAtivo(ativo);

		pessoaRepository.save(pessoaCon);
	}

	public Optional<Pessoa> buscarPessoaPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);

		if (pessoaSalva.isPresent() == false) {

			throw new EmptyResultDataAccessException(1);

		}
		return pessoaSalva;
	}
}
