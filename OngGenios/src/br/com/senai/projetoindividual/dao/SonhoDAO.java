package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Sonho;

public interface SonhoDAO {

	void adicionar(Sonho sonho) throws OngException;
	List<Sonho> consultarSonho(String nome, String prioridade) throws OngException;
	Sonho consultarPorId(int id) throws OngException;
	void editar(Sonho sonho) throws OngException;
	void inativar(int id) throws OngException;
}
