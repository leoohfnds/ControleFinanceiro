package com.exemple.controlefinancerio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.controlefinancerio.api.model.Lancamento;
import com.exemple.controlefinancerio.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
