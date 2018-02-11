package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.dao.EventoDAO;
import br.com.senai.projetoindividual.dao.FinanceiroDAO;
import br.com.senai.projetoindividual.dao.GaleriaDAO;
import br.com.senai.projetoindividual.dao.SonhoDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCEventoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCFinanceiroDAO;
import br.com.senai.projetoindividual.jdbc.JDBCGaleriaDAO;
import br.com.senai.projetoindividual.jdbc.JDBCSonhoDAO;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;
import br.com.senai.projetoindividual.validador.OngValidador;

public class GaleriaService {

	public void cadastrar(Galeria galeria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			OngValidador.validaGaleria(galeria);
			conexao = conec.abrirConexao();
			
			GaleriaDAO galeriadao = new JDBCGaleriaDAO(conexao);
			galeriadao.adicionar(galeria);
		
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

	public List<Galeria> consultarGaleria(String nome, String tipo) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Galeria> galeriaList = null;	
		
		try{

			conexao = conec.abrirConexao();
			GaleriaDAO galeriaDAO = new JDBCGaleriaDAO(conexao);
			galeriaList = galeriaDAO.buscar(nome,tipo);
			
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
		return galeriaList;
	}



	public Galeria consultarPorId(int id) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		Galeria retorno;
		
		try{

			conexao = conec.abrirConexao();
			GaleriaDAO galeriaDao = new JDBCGaleriaDAO(conexao);
			retorno = galeriaDao.consularPorId(id);
			
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

	public void editar(Galeria galeria) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;		
		
		try{
			
			conexao = conec.abrirConexao();
			
			GaleriaDAO galeriaDAO = new JDBCGaleriaDAO(conexao);
			galeriaDAO.editar(galeria);
			
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
			GaleriaDAO jdbInativar = new JDBCGaleriaDAO(conexao);
			
			jdbInativar.inatir(id);
			
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
