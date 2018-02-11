package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.senai.projetoindividual.dao.CategoriaDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Categoria;

public class JDBCCategoriaDAO implements CategoriaDAO {

	private Connection connection;

	public JDBCCategoriaDAO(Connection connection) {
		
		this.connection = connection;
	}
	
	@Override
	public void adicionar(Categoria categoria)throws OngException {

		String sql = "insert into categoria (tipoCategoria, responsavelCadastro)" + "value(?,?)";		
		PreparedStatement p;
		
		try{
			
			p=this.connection.prepareStatement(sql);
			p.setString(1, categoria.getTipoCategoria());
			p.setInt(2, categoria.getResponsavelCadastro().getId());
			
			p.execute();
			
		}catch (Exception e) {
			throw new OngException(e);
		}
	}
			
	@Override
	public List<Categoria> buscar() throws OngException {
		
		String sql = "select * from categoria";
		
		List<Categoria> listCategoria = new ArrayList<Categoria>();
		Categoria cate = null;
		
		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
								
				cate = new Categoria();
				
				cate.setId(rs.getInt("id"));
				cate.setTipoCategoria(rs.getString("tipoCategoria"));

				listCategoria.add(cate);				
			}
		}catch(Exception e){	
			throw new OngException(e);
		}
		return listCategoria;
	}

	@Override
	public Categoria consularPorId(int id) throws OngException {
		
		Categoria cate=null;
		String sql = "select id, tipoCategoria from categoria where id=?";
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
					
				cate = new Categoria();
				
				cate.setId(rs.getInt("id"));
				cate.setTipoCategoria(rs.getString("tipoCategoria"));
			}

			return cate;
		}catch(Exception e){			
			throw new OngException(e);
		}
	}

	@Override
	public void inativar(int id) throws OngException {

		String sql = "delete from categoria where id = " + id;
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			throw new OngException(e);
		}
	}

	@Override
	public void editar(Categoria cate) throws OngException {
		
		String sql= "update categoria set tipoCategoria=? where id=?";
		PreparedStatement p1;

		try{
			p1 = this.connection.prepareStatement(sql);
			p1.setString(1, cate.getTipoCategoria());
			p1.setInt(2, cate.getId());

			p1.execute();
		}catch (Exception e) {
			throw new OngException(e);
		}
		
	}
}
