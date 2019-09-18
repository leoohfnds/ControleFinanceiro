package com.exemple.controlefinancerio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemple.controlefinancerio.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
