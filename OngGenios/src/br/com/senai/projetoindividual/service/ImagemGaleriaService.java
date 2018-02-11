package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.GaleriaDAO;
import br.com.senai.projetoindividual.dao.ImagemDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCGaleriaDAO;
import br.com.senai.projetoindividual.jdbc.JDBCImagemDAO;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;
import br.com.senai.projetoindividual.validador.OngValidador;

public class ImagemGaleriaService {
	
	public void cadastrarImagens(Imagem imagem) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			OngValidador.validaGaleriaImagens(imagem);
			
			ImagemDAO galeriadao = new JDBCImagemDAO(conexao);
			galeriadao.adicionarImagem(imagem);
			
		}catch (OngException e) {
			
			throw e;
		} catch (Exception e) {
			
			throw new OngException(e);
		}finally{
			
			try {
				
				if(conexao != null){
				
					conec.fecharConexao();
				}	
			} catch (Exception e) {
			
				throw new OngException(e);
			}
		}
	}

	public List<Imagem> consultarImagem(int id_galeria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Imagem> imagemList = null;	
		
		try{

			conexao = conec.abrirConexao();
			ImagemDAO imagemdao = new JDBCImagemDAO(conexao);
			imagemList =  imagemdao.buscar(id_galeria);
			
		}catch (OngException e) {
			
			throw e;
		} catch (Exception e) {
			
			throw new OngException(e);
		}finally{
			
			try {
				
				if(conexao != null){
				
					conec.fecharConexao();
				}	
			} catch (Exception e) {
			
				throw new OngException(e);
			}
		}
		return imagemList;
		
	}

	public void editar(Imagem imagem) throws OngException {
			
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			
			ImagemDAO imagemdao = new JDBCImagemDAO(conexao);
			imagemdao.editar(imagem);
		}catch(OngException e){
			
			throw e;
		}catch(Exception e){
			
			throw new OngException(e);
		}finally{
			
			try {
				
				if(conexao != null){
				
					conec.fecharConexao();
				}	
			} catch (Exception e) {
			
				throw new OngException(e);
			}
		}
	}

	public void inativar(int id) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;	
		
		try{
			
			conexao = conec.abrirConexao();
			ImagemDAO jdbInativar = new JDBCImagemDAO(conexao);
			
			jdbInativar.inativar(id);
			
		}catch(OngException e){
			
			throw e;
		}catch(Exception e){
			
			throw new OngException(e);
		}finally{
			
			try {
				
				if(conexao != null){
				
					conec.fecharConexao();
				}	
			} catch (Exception e) {
			
				throw new OngException(e);
			}
		}
		
	}

}
