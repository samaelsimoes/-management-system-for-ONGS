package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.dao.EventoDAO;
import br.com.senai.projetoindividual.dao.FinanceiroDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCEventoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCFinanceiroDAO;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.validador.OngValidador;

public class FinanceiroService {

	public void depositar(Financeiro financeiro) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			OngValidador.validaDeposito(financeiro);
			
			conexao = conec.abrirConexao();
			FinanceiroDAO financeiroDAO = new JDBCFinanceiroDAO(conexao);
			financeiroDAO.depositar(financeiro);

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

	public void saque(Financeiro financeiro) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;

		try{

			OngValidador.validaFinanceiro(financeiro);
			conexao = conec.abrirConexao();
			FinanceiroDAO financeiroDAO = new JDBCFinanceiroDAO(conexao);
			financeiroDAO.saque(financeiro);

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

	public List<Financeiro> buscar(String categoria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Financeiro> financeiroList = null;

		try{
			
			conexao = conec.abrirConexao();

			FinanceiroDAO financeDAO = new JDBCFinanceiroDAO(conexao);
			financeiroList = financeDAO.buscar( categoria); 
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
		return financeiroList;
	}

	public double listaTotal(String categoria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		double finan = 0;

		try{
			
			conexao = conec.abrirConexao();
			FinanceiroDAO finanDAO = new JDBCFinanceiroDAO(conexao);
			
			if(categoria.equals("1")){
				
				finan = finanDAO.listaTotaldeposito();
			}else if(categoria.equals("2")){
				
				finan = finanDAO.listaTotalsaque();
			}
		}catch(OngException e){
			
			throw e;
		}catch(Exception e){
			
			throw new OngException(e);
		}finally{
			
			try{
				
				if(conexao != null){
					
					conec.fecharConexao();
				}
			}catch (Exception e){
				
				throw new OngException(e);
			}
		}
		
		
		return finan;
	}

	public void inativar(int id) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			
			FinanceiroDAO finan = new JDBCFinanceiroDAO(conexao);
			finan.inativar(id);
			
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

	public Financeiro consultarPorID(int id, String tipo) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;
		Financeiro finanList = null;	

		try{
			conexao = conec.abrirConexao();
			FinanceiroDAO finan = new JDBCFinanceiroDAO(conexao);
			finanList = finan.consularPorId(id,tipo);
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
		return finanList;
	}

	public void editar(Financeiro finan) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			FinanceiroDAO finanDAO = new JDBCFinanceiroDAO(conexao);
			finanDAO.editar(finan);
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
