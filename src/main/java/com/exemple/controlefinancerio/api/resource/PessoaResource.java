package com.exemple.controlefinancerio.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.exemple.controlefinancerio.api.model.Pessoa;
import com.exemple.controlefinancerio.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listar() {

		return pessoaRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {

		Pessoa pessoaSalva = pessoaRepository.save(pessoa);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Pessoa>> buscarPorCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> status = pessoaRepository.findById(codigo);
		
		if (status.isPresent() == false) {
			return ResponseEntity.notFound().build();
		}
			return ResponseEntity.ok(status);
		
	}

	@DeleteMapping("/{codigo}")
	public void deletar(@PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
		
		if (pessoaSalva.isPresent() == false) {
			
			throw new EmptyResultDataAccessException(1);
			
		}
		Pessoa conpessoaSalva = pessoaSalva.get(); 		// aqui eu converto pessoa salva para a entidade Pessoa. pq save aceita apenas Pessoa.
		
		BeanUtils.copyProperties(pessoa, conpessoaSalva, "codigo");
		pessoaRepository.save(conpessoaSalva);
		
		return ResponseEntity.ok(conpessoaSalva);
		
	}

}
