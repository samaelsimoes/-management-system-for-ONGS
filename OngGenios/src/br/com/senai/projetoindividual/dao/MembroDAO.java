package br.com.senai.projetoindividual.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Membro;

public interface MembroDAO {

	void adicionar(Membro membro) throws OngException;
	List<Membro> consultarNome(String nome) throws OngException;
	Membro consularPorId(int id) throws OngException;
	void editar(Membro membro) throws OngException;
	void inativar(int id) throws OngException;
	Membro buscaPorLogin(Membro membro) throws OngException;
}
