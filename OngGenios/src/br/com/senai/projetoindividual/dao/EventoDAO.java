package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Evento;

public interface EventoDAO {

	void adicionar(Evento evento) throws OngException;
	List<Evento> buscar(String nome, String categoria) throws OngException;
	Evento consularPorId(int id, String categoria) throws OngException;
	void editar(Evento evento) throws OngException;
	void inativar(int id) throws OngException;

}
