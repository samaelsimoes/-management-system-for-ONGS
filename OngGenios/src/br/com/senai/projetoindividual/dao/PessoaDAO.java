package br.com.senai.projetoindividual.dao;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Pessoa;

public interface PessoaDAO {

	void adicionar(Pessoa pessoa) throws OngException;
	void editar(Pessoa pessoa) throws OngException;
	void inativar(int id) throws OngException;
}
