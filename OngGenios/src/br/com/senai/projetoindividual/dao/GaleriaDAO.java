package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;

public interface GaleriaDAO {

	void adicionar(Galeria galeria) throws OngException;
	void editar(Galeria galeria) throws OngException;
	void inatir(int id) throws OngException;
	
	List<Galeria> buscar(String tipo, String tipo2) throws OngException;
	Galeria consularPorId(int id) throws OngException;
}
