package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.dao.EventoDAO;
import br.com.senai.projetoindividual.dao.FinanceiroDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.CampoVaziuException;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCEventoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCFinanceiroDAO;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.validador.OngValidador;

public class EventoService {

	public void adicionar(Evento evento) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
		
			OngValidador.validaEvento(evento);
			conexao = conec.abrirConexao();
			
			EventoDAO eventodao = new JDBCEventoDAO(conexao);
			eventodao.adicionar(evento);
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

	public List<Evento> consultarNome(String nome, String categoria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Evento> eventoList = null;

		try{
			
			conexao = conec.abrirConexao();
			EventoDAO eventoBusca = new JDBCEventoDAO(conexao);
			
			eventoList = eventoBusca.buscar(nome, categoria);
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
		return eventoList;
	}

	public Evento consultarPorId(int id, String categoria) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;
		Evento retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			EventoDAO eventodao = new JDBCEventoDAO(conexao);
			retorno = eventodao.consularPorId(id,categoria);
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
		return retorno;
	}

	public void editar(Evento evento) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			EventoDAO eventodao =new JDBCEventoDAO(conexao);
			eventodao.editar(evento);
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
			EventoDAO eventoDAO = new JDBCEventoDAO(conexao);
			eventoDAO.inativar(id);
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
