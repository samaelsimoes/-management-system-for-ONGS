package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Categoria;

public interface CategoriaDAO {

	List<Categoria> buscar() throws OngException;

	void adicionar(Categoria categoria) throws OngException;

	Categoria consularPorId(int id) throws OngException;

	void inativar(int id) throws OngException;

	void editar(Categoria cate) throws OngException;
}
