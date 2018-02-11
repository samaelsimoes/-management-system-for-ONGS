package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.dao.SonhoDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCSonhoDAO;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Sonho;
import br.com.senai.projetoindividual.validador.OngValidador;

public class SonhoService {

	public void adicionar(Sonho sonho) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			OngValidador.validaSonho(sonho);
			conexao = conec.abrirConexao();
			
			SonhoDAO sonhoDAO = new JDBCSonhoDAO(conexao);
			sonhoDAO.adicionar(sonho);
			
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

	public List<Sonho> consultarSonho(String nome, String prioridade) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Sonho> sonhoList = null;
		
		try{

			conexao = conec.abrirConexao();
			
			SonhoDAO sonhoDAO = new JDBCSonhoDAO(conexao);
			sonhoList = sonhoDAO.consultarSonho(nome, prioridade);
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
		return sonhoList;
	}

	public Sonho consultarPorId(int id) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		Sonho retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			SonhoDAO sonhoDAO = new JDBCSonhoDAO(conexao);
			retorno = sonhoDAO.consultarPorId(id);
			
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

		return retorno;
	}

	public void editar(Sonho sonho) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;		
		
		try{
			
			conexao = conec.abrirConexao();
			
			SonhoDAO sonhoDAO = new JDBCSonhoDAO(conexao);
			sonhoDAO.editar(sonho);
			
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
			SonhoDAO sonhoDAO = new JDBCSonhoDAO(conexao);
			sonhoDAO.inativar(id);
			
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
