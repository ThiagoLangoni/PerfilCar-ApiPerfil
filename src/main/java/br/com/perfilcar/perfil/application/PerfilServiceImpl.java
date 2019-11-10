package br.com.perfilcar.perfil.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.perfilcar.perfil.domain.Perfil;
import br.com.perfilcar.perfil.domain.repository.PerfilRepository;

@Service
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository repository;

    @Autowired
    public PerfilServiceImpl(PerfilRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Perfil> listaPerfis() {
        return repository.findAll();
    }

    @Override
    public List<Perfil> listaPerfisPorEmail(String emailProprietario) {
        return repository.findByEmailProprietario(emailProprietario);
    }

    @Override
    public Perfil listaPerfilUnico(long id) {
        return repository.findById(id);
    }

    @Override
    public Perfil salvaPerfil(Perfil perfil) {
        return repository.save(perfil);
    }

    @Override
    public Perfil atualizaPerfil(Perfil perfil) {
        return repository.save(perfil);
    }

    @Override
    public void deletaPerfil(long id) {
        repository.deleteById(id);
    }
}