package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;

public interface BeneficiarioDAO {

	void adicionar(Beneficiario beneficiario) throws OngException;
	public List<Beneficiario> consultarNome(String nome) throws OngException;
	Beneficiario consularPorId(int id) throws OngException;
	void editar(Beneficiario beneficiario) throws OngException;
	void inativar(int id) throws OngException;
}
