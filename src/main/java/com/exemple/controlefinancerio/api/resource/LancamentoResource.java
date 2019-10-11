package com.exemple.controlefinancerio.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemple.controlefinancerio.api.event.RecursoCriadoEvent;
import com.exemple.controlefinancerio.api.exceptionhandler.ControleFinanceiroExceptionHandler.Erro;
import com.exemple.controlefinancerio.api.model.Lancamento;
import com.exemple.controlefinancerio.api.repository.LancamentoRepository;
import com.exemple.controlefinancerio.api.repository.filter.LancamentoFilter;
import com.exemple.controlefinancerio.api.service.LancamentoService;
import com.exemple.controlefinancerio.api.service.exception.PessoaInexistenteOuPessoaInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	LancamentoService lancamentoService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ApplicationEventPublisher publisher; 

	@Autowired
	LancamentoRepository lancamentoRepository;

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter,pageable);
	}

	@GetMapping("/{codigo}")
	private Optional<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		

		return lancamentoService.buscarPorCodigo(codigo);

	}
	
	@PostMapping
	private ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response ) {
		
		Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
	
	@ExceptionHandler({PessoaInexistenteOuPessoaInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuPessoaInativaException(PessoaInexistenteOuPessoaInativaException ex){
		
		String mensagemUsuario = messageSource.getMessage("pessoa-inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolverdor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolverdor));
		
		return ResponseEntity.badRequest().body(erros);
	}
	
	
	@DeleteMapping("/{codigo}")
	public void deletar(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}

	
	
	@PutMapping("/{codigo}")
	private ResponseEntity<Lancamento> atualizarPorCodigo(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento ){
		
		Lancamento lancamentos = lancamentoService.atualizar(codigo, lancamento);
		
		return ResponseEntity.ok(lancamentos);
		
		
		
		
	}
	
	

}
