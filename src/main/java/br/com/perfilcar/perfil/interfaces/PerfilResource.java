package br.com.perfilcar.perfil.interfaces;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.perfilcar.perfil.application.PerfilService;
import br.com.perfilcar.perfil.domain.Perfil;
import br.com.perfilcar.perfil.domain.service.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/perfil")
@CrossOrigin(origins = "*")
@Api(value = "API REST Perfis")
public class PerfilResource {

	private final PerfilService service;
	private final Producer producerkafka;

	@Autowired
	public PerfilResource(PerfilService service, Producer producer) {
		this.service = service;
		this.producerkafka = producer;
	}

	@GetMapping("/perfis")
	@ApiOperation(value="Retorna uma lista de perfis")
	public List<Perfil> listaPerfis() {
		String msgKafka = "Pedido de Lista de perfil" + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);
		return service.listaPerfis();
	}

	@GetMapping("/perfis/{emailProprietario}")
	@ApiOperation(value="Retorna uma lista de perfis do e-mail especificado")
	public List<Perfil> listaPerfisPorEmail(@PathVariable(value = "emailProprietario") String emailProprietario) {
		String msgKafka = "Pedido de Lista de perfil por e-mail" + "-" + emailProprietario + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);
		return service.listaPerfisPorEmail(emailProprietario);
	}

	@GetMapping("/perfil/{id}")
	@ApiOperation(value="Retorna um perfil unico do Id especificado")
	public Perfil listaPerfilUnico(@PathVariable(value = "id") long id) {
		String msgKafka = "Pedido de Lista de perfil por ID" + "-" + id + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);
		return service.listaPerfilUnico(id);
	}

	@PostMapping("/perfil")
	@ApiOperation(value="Cria um perfil com os dados informados")
	public ResponseEntity<Void> salvaPerfil(@RequestBody Perfil perfil) {
		String msgKafka = "Pedido de criacao de Perfil" + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);

		Perfil perfilInserido = service.salvaPerfil(perfil);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
												  .path("/{id}")
												  .buildAndExpand(perfilInserido.getId())
												  .toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/perfil")
	@ApiOperation(value="Atualiza um perfil com os dados informados")
	public Perfil atualizaPerfil(@RequestBody Perfil perfil) {
		String msgKafka = "Pedido de atualizacao de perfil" + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);
		return service.atualizaPerfil(perfil);
	}

	@DeleteMapping("/perfil/{id}")
	@ApiOperation(value="Deleta o perfil Informado")
	public void deletaPerfil(@PathVariable(value = "id") long id) {
		String msgKafka = "Pedido de delecao de perfil" + "-" + ZonedDateTime.now().toString();
		this.producerkafka.sendMessage(msgKafka);
		service.deletaPerfil(id);
	}
}
