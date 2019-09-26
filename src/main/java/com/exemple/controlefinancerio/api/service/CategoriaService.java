package com.exemple.controlefinancerio.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.exemple.controlefinancerio.api.model.Categoria;
import com.exemple.controlefinancerio.api.repository.CategoriaRepository;



@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria atualizar(Long codigo, Categoria categoria ) {
		
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(codigo);
		
		if (categoriaSalva.isPresent() == false) {
			throw new EmptyResultDataAccessException(1);
		}
		Categoria conCategoria = categoriaSalva.get();
		BeanUtils.copyProperties(categoria, conCategoria, "codigo");
		
		return categoriaRepository.save(conCategoria);
		
	}
	
	public Optional<Categoria> buscarPorCodigo(Long codigo){
		
		Optional<Categoria> categorias = categoriaRepository.findById(codigo);
		
		if (categorias.isPresent() == false) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categorias;
		
	}
	
	
	
}
