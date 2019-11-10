package br.com.perfilcar.perfil.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "perfis")
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String apelido;
    private String marca;
    private String modelo;
    private String quilometragemAtual;
    private String anoFabricacao;
    private String anoModelo;
    private String nomeProprietario;
    private String informacoesAdicionais;
    private String timestamp;
    private String emailProprietario;
 
}