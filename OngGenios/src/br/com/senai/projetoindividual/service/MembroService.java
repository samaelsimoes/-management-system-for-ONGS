package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import javax.websocket.Session;

import br.com.senai.projetoindividual.criptografia.Decode64;
import br.com.senai.projetoindividual.criptografia.Md5;
import br.com.senai.projetoindividual.dao.MembroDAO;
import br.com.senai.projetoindividual.dao.PessoaDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.CampoVaziuException;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCMembroDAO;
import br.com.senai.projetoindividual.jdbc.JDBCPessoaDAO;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.validador.OngValidador;

public class MembroService {

	public void adicionar(Membro membro) throws OngException{
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try {
			
			OngValidador.validaAdicionar(membro);
			
			conexao = conec.abrirConexao();
			PessoaDAO pessoaDAO = new JDBCPessoaDAO(conexao);

			Decode64 base64 = new Decode64();
			String decodeBase64 = base64.decode64(membro.getSenha());
			membro.setSenha(Md5.criptografar(decodeBase64));
			
			pessoaDAO.adicionar(membro);
			
			MembroDAO membroDAO = new JDBCMembroDAO(conexao);
			membroDAO.adicionar(membro);
			
		} catch (OngException e) {
			
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
	public List<Membro> consultarNome(String nome)throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Membro> membroList = null;

		try{
			
			conexao = conec.abrirConexao();
			
			MembroDAO membDAO = new JDBCMembroDAO(conexao);
			membroList = membDAO.consultarNome(nome);			
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
		
		return membroList;
	}
	
	public Membro consultarPorId(int id) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		Membro retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			
			MembroDAO mebDAO = new JDBCMembroDAO(conexao);
			retorno = mebDAO.consularPorId(id);
			
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
		
		return (Membro) retorno;
	}
	
	public void editar(Membro membro) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			Decode64 base64 = new Decode64();
						
			if(!membro.getSenha().equals("") || membro.getSenha() != null){
				
				String decodeBase64 = base64.decode64(membro.getSenha());
				
				membro.setSenha(Md5.criptografar(decodeBase64));
			}
						
			PessoaDAO pessoaDAO = new JDBCPessoaDAO(conexao);			
			pessoaDAO.editar(membro);
			
			MembroDAO membroDAO = new JDBCMembroDAO(conexao);
			membroDAO.editar(membro);
			
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
			
			MembroDAO membroDAO = new JDBCMembroDAO(conexao);
			membroDAO.inativar(id);
			
			PessoaDAO pessoaDAO = new JDBCPessoaDAO(conexao);
			pessoaDAO.inativar(id);
			
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
	
	public Membro login(String login, String senha) throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;	
		Membro retorno = null;
		
		try {
			
			Decode64 base64 = new Decode64();
			
			Membro membro = new Membro();
			membro.setLogin(login);
			membro.setSenha(senha);
			
			String decodeBase64 = base64.decode64(membro.getSenha());
			membro.setSenha(Md5.criptografar(decodeBase64));
			
			OngValidador.validaVaziuLogin(membro);
			conexao = conec.abrirConexao();

			MembroDAO membroJDBC = new JDBCMembroDAO(conexao);
			retorno = membroJDBC.buscaPorLogin(membro);
			
			if(retorno == null){
				
				throw new OngException(" <br> <br> Login Invalidos"+ "<br>" );
			}
			
		} catch(OngException e){
			
			throw e;
		}catch (Exception e) {

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
}

