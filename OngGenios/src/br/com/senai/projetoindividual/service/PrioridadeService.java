package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.CategoriaDAO;
import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.dao.PrioridadeDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCCategoriaDAO;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCPrioridadeDAO;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Prioridade;
import br.com.senai.projetoindividual.validador.OngValidador;

public class PrioridadeService {
	public void adicionar(Prioridade prioridade) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			PrioridadeDAO prioridadeDAO = new JDBCPrioridadeDAO(conexao);
			prioridadeDAO.adicionar(prioridade);

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
	public List<Prioridade> consultar() throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;		
		List<Prioridade> prioridadeList = null;	

		try{
			
			conexao = conec.abrirConexao();
			
			PrioridadeDAO prioridaDAO = new JDBCPrioridadeDAO(conexao);
			prioridadeList = prioridaDAO.consultar();
			
		}catch (OngException e) {		
			throw e;
		} catch (Exception e) {
			throw new OngException(e);
		}finally{
			try {
				if(conexao != null){
					conec.fecharConexao();
				}	
			} catch (Exception e){
				throw new OngException(e);
			}
		}
	
		return prioridadeList;
	}
	
	public void inativar(int id) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;	
				
		try{
			
			conexao = conec.abrirConexao();

			PrioridadeDAO priDAO = new JDBCPrioridadeDAO(conexao);
			priDAO.inativar(id);
			
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
	public void editar(Prioridade prio) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			PrioridadeDAO catedao =new JDBCPrioridadeDAO(conexao);
			catedao.editar(prio);
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
	public Prioridade consultarPorId(int id) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;
		
		Prioridade retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			
			PrioridadeDAO categoria = new JDBCPrioridadeDAO(conexao);
			retorno = categoria.consularPorId(id);
			
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
		return  retorno;
	}
}
