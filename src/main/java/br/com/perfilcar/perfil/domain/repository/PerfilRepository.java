package br.com.perfilcar.perfil.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.perfilcar.perfil.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Perfil findById(long id);

	List<Perfil> findByEmailProprietario(String emailProprietario);

}
