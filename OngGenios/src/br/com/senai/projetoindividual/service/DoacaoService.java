package br.com.senai.projetoindividual.service;


import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCDoacaoDAO;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.validador.OngValidador;


public class DoacaoService {

	public void adicionar(Doacao doacao) throws OngException{
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		try{
			OngValidador.validaAdicionarDoacao(doacao);
			conexao = conec.abrirConexao();
			DoacaoDAO doacaoDAO = new JDBCDoacaoDAO(conexao);
			doacaoDAO.adicionar(doacao);

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

	public List<Doacao> consultarNome(String nome, String categoria) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Doacao> doacaoList = null;	
		try{
			conexao = conec.abrirConexao();
			DoacaoDAO doacaoDAO = new JDBCDoacaoDAO(conexao);
			doacaoList = doacaoDAO.consultarDoacao(nome, categoria);
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
		return doacaoList;
	}

	public Doacao consultarPorId(int id) throws OngException{
		Conexao conec = new Conexao();
		Connection conexao = null;
		Doacao retorno;
		try{
			conexao = conec.abrirConexao();
			DoacaoDAO doacaoDAO = new JDBCDoacaoDAO(conexao);
			retorno = doacaoDAO.consularPorId(id);
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

	public void editar(Doacao doacao) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;
		try{
			conexao = conec.abrirConexao();
			DoacaoDAO doaDAO = new JDBCDoacaoDAO(conexao);
			doaDAO.editar(doacao);	
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
			DoacaoDAO doacaoDAO = new JDBCDoacaoDAO(conexao);
			doacaoDAO.inativar(id);
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
