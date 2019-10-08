package com.exemple.controlefinancerio.api.repository.lancamento;

import java.util.List;

import com.exemple.controlefinancerio.api.model.Lancamento;
import com.exemple.controlefinancerio.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery  {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
	

}
