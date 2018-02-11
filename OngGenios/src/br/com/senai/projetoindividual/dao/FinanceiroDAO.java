package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Financeiro;

public interface FinanceiroDAO {

	void depositar(Financeiro financeiro) throws OngException;
	void saque(Financeiro financeiro) throws OngException;
	List<Financeiro> buscar( String categoria) throws OngException;
	void inativar(int id) throws OngException;
	Financeiro consularPorId(int id, String tipo) throws OngException;
	void editar(Financeiro finan) throws OngException;
	double listaTotalsaque() throws OngException;
	double listaTotaldeposito() throws OngException;
}
