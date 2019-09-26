package com.exemple.controlefinancerio.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.controlefinancerio.api.event.RecursoCriadoEvent;
import com.exemple.controlefinancerio.api.model.Categoria;
import com.exemple.controlefinancerio.api.repository.CategoriaRepository;
import com.exemple.controlefinancerio.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaService categoriaService;

	@Autowired
	private ApplicationEventPublisher publisher; // publicador do evendto

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("{/codigo}")
	public Optional<Categoria> buscarPorCodigo(@PathVariable Long codigo) {

		return categoriaService.buscarPorCodigo(codigo);
	}

	@DeleteMapping("/{codigo}")
	public void deletar(@PathVariable Long codigo) {
		categoriaRepository.deleteById(codigo);
	}

	@PutMapping("{/codigo}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria) {

		Categoria categoriaSalva = categoriaService.atualizar(codigo, categoria);

		return ResponseEntity.ok(categoriaSalva);

	}

}