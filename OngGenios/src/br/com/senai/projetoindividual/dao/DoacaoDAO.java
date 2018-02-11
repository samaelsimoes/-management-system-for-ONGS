package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Doacao;

public interface DoacaoDAO {

	void adicionar(Doacao doacao) throws OngException;
	List<Doacao> consultarDoacao(String json, String nomeDoacao) throws OngException;
	Doacao consularPorId(int id) throws OngException;
	void editar(Doacao doacao) throws OngException;
	void inativar(int id) throws OngException;
}
