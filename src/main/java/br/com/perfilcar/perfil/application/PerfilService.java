package br.com.perfilcar.perfil.application;

import java.util.List;

import br.com.perfilcar.perfil.domain.Perfil;

public interface PerfilService {
 
    List<Perfil> listaPerfis();

    List<Perfil> listaPerfisPorEmail(String emailProprietario);

    Perfil listaPerfilUnico(long id);

    Perfil salvaPerfil(Perfil perfil);

    Perfil atualizaPerfil(Perfil perfil);

    void deletaPerfil(long id);
}