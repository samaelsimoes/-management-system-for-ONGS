package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Prioridade;

public interface PrioridadeDAO {

	List<Prioridade> consultar() throws OngException;
	void adicionar(Prioridade prioridade) throws OngException;
	void inativar(int id) throws OngException;
	void editar(Prioridade prio) throws OngException;
	Prioridade consularPorId(int id) throws OngException;
}
