package com.exemple.controlefinancerio.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.exemple.controlefinancerio.api.model.Lancamento;
import com.exemple.controlefinancerio.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery  {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

	
	
	
	

}
