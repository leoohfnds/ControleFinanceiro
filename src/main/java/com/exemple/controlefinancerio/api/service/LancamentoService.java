package com.exemple.controlefinancerio.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.exemple.controlefinancerio.api.model.Lancamento;
import com.exemple.controlefinancerio.api.model.Pessoa;
import com.exemple.controlefinancerio.api.repository.LancamentoRepository;
import com.exemple.controlefinancerio.api.repository.PessoaRepository;
import com.exemple.controlefinancerio.api.service.exception.PessoaInexistenteOuPessoaInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Optional<Lancamento> buscarPorCodigo(Long codigo){
		
		Optional<Lancamento> status = lancamentoRepository.findById(codigo);
		
		if (status.isPresent() == false) {
			
			throw new EmptyResultDataAccessException(1);
		}
		
		return status;
		
	}
	
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento ) {
		
		Optional<Lancamento> lancamentoSalva = lancamentoRepository.findById(codigo);
		
		Lancamento lancamentoCon = lancamentoSalva.get();
		
		BeanUtils.copyProperties(lancamento, lancamentoCon, "codigo");
		
		return lancamentoRepository.save(lancamentoCon);
		
	}


	public Lancamento salvar(@Valid Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		Pessoa pessoaCon = pessoa.get();
		
		if (pessoa == null || pessoaCon.IsInativo()) {
			
			throw new PessoaInexistenteOuPessoaInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
	

}
