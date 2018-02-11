package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.BeneficiarioDAO;
import br.com.senai.projetoindividual.dao.PessoaDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCBeneficiarioDAO;
import br.com.senai.projetoindividual.jdbc.JDBCPessoaDAO;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.validador.ValidadorBeneficiario;

public class BeneficiarioService {

	public void adicionar(Beneficiario beneficiario) throws OngException{

		ValidadorBeneficiario.validaAdicionar(beneficiario);
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
						
			PessoaDAO pessoaDAO = new JDBCPessoaDAO(conexao);
			BeneficiarioDAO beneDAO = new JDBCBeneficiarioDAO(conexao);
			Membro membro = new Membro();
			
			pessoaDAO.adicionar(beneficiario);
			
			beneDAO.adicionar(beneficiario);
			
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

	public List<Beneficiario> consultarNome(String nome)throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Beneficiario> beneList = null;

		try{
			
			conexao = conec.abrirConexao();
			
			BeneficiarioDAO beneDAO = new JDBCBeneficiarioDAO(conexao);
			beneList = beneDAO.consultarNome(nome);			
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
		return beneList;
	}

	public Beneficiario consultarPorId(int id) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		Beneficiario retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			
			BeneficiarioDAO beneDAO = new JDBCBeneficiarioDAO(conexao);
			retorno = beneDAO.consularPorId(id);
			//consultarPorId
			
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
		return (Beneficiario) retorno;
	}

	public void editar(Beneficiario beneficiario) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			
			PessoaDAO pessoaDAO = new JDBCPessoaDAO(conexao);
			pessoaDAO.editar(beneficiario);
			
			BeneficiarioDAO beneDAO = new JDBCBeneficiarioDAO(conexao);
			beneDAO.editar(beneficiario);
			
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

			BeneficiarioDAO beneDAO = new JDBCBeneficiarioDAO(conexao);
			beneDAO.inativar(id);
			
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
}
