package br.com.senai.projetoindividual.service;

import java.sql.Connection;
import java.util.List;

import br.com.senai.projetoindividual.dao.BeneficiarioDAO;
import br.com.senai.projetoindividual.dao.CategoriaDAO;
import br.com.senai.projetoindividual.dao.EventoDAO;
import br.com.senai.projetoindividual.dao.PessoaDAO;
import br.com.senai.projetoindividual.db.Conexao;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.jdbc.JDBCBeneficiarioDAO;
import br.com.senai.projetoindividual.jdbc.JDBCCategoriaDAO;
import br.com.senai.projetoindividual.jdbc.JDBCEventoDAO;
import br.com.senai.projetoindividual.jdbc.JDBCPessoaDAO;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.validador.OngValidador;

public class CategoriaService {

	public List<Categoria> buscar() throws OngException {

		Conexao conec = new Conexao();
		Connection conexao = null;
		List<Categoria> categoriaList = null;
		
		try{
			
			conexao = conec.abrirConexao();
			
			CategoriaDAO categoriaDAO = new JDBCCategoriaDAO(conexao);
			categoriaList = categoriaDAO.buscar();			
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
		return categoriaList;
	}

	public void adicionar(Categoria categoria) throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			conexao = conec.abrirConexao();

			OngValidador.validaCategoria(categoria);
			
			CategoriaDAO categoriaDAO  = new JDBCCategoriaDAO(conexao);
			categoriaDAO.adicionar(categoria);
			
			
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

	public Categoria consultarPorId(int id) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;
		Categoria retorno;
		
		try{
			
			conexao = conec.abrirConexao();
			
			CategoriaDAO categoria = new JDBCCategoriaDAO(conexao);
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

	public void inativar(int id) throws OngException {
		Conexao conec = new Conexao();
		Connection conexao = null;	
				
		try{
			
			conexao = conec.abrirConexao();

			CategoriaDAO cateDAO = new JDBCCategoriaDAO(conexao);
			cateDAO.inativar(id);
			
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

	public void editar(Categoria cate)throws OngException {
		
		Conexao conec = new Conexao();
		Connection conexao = null;
		
		try{
			
			conexao = conec.abrirConexao();
			CategoriaDAO catedao =new JDBCCategoriaDAO(conexao);
			catedao.editar(cate);
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
