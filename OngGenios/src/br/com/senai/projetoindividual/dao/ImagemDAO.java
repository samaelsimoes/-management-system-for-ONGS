package br.com.senai.projetoindividual.dao;

import java.util.List;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Imagem;

public interface ImagemDAO {

	void adicionarImagem(Imagem imagem) throws OngException;
	List<Imagem> buscar(int id_galeria) throws OngException;
	void editar(Imagem imagem) throws OngException;
	void inativar(int id) throws OngException;;
}
