package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.DoacaoDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Membro;

public class JDBCDoacaoDAO implements DoacaoDAO {

	private Connection connection;
	
	public JDBCDoacaoDAO(Connection connection) {

		this.connection = connection;
	}

	@Override
	public void adicionar(Doacao doacao) throws OngException {

		String sql = "insert into doacao(id_categoria, id_responsavel, doacao, quantidade) "
				+ "value(?,?,?,?) ";
		
		PreparedStatement p2;
		
		try{
	
			p2 = this.connection.prepareStatement(sql);
			
			p2.setInt(1, doacao.getCategoria().getId());
			p2.setInt(2, doacao.getResponsavel().getId());
			p2.setString(3, doacao.getDoacao());
			p2.setInt(4, doacao.getQuantidade());
			p2.execute();
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	public  List<Doacao> consultarDoacao(String nome, String categoria) throws OngException { 
				
		String sql =  "select d.id, d.doacao, p.nome, p.sobrenome, c.tipoCategoria, d.quantidade from  doacao d JOIN pessoas p on d.id_responsavel = p.id " 
					  + " JOIN categoria c on d.id_categoria = c.id ";
				
		if(!nome.equals("null") && categoria.equals("1")){
			
			sql += " where d.doacao like '" + nome + "%'";
		}

		if(!nome.equals("null") && !categoria.equals("1")){
			
			sql += " where d.doacao like '" + nome + "%' " + " and d.id_categoria = " + categoria;;
		}

		if(nome.equals("null") && !categoria.equals("null") && !categoria.equals("1")){

			sql += " where d.id_categoria =" + categoria;
		}
		
		List<Doacao> listDoacao = new ArrayList<Doacao>();
		
		Doacao doacao = null;
		Membro membro = null;
		Categoria cate = null;
				
		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				membro = new Membro();
				cate = new Categoria();
				doacao = new Doacao();
				
				doacao.setId(rs.getInt("id"));
				doacao.setDoacao(rs.getString("doacao"));
				membro.setNome(rs.getString("nome"));
				membro.setSobreNome(rs.getString("sobrenome"));
				cate.setTipoCategoria(rs.getString("tipoCategoria"));
				doacao.setQuantidade(rs.getInt("quantidade"));
				
				doacao.setResponsavel(membro);
				doacao.setCategoria(cate);
				
				listDoacao.add(doacao);
			}
			
		}catch(Exception e){
			
			throw new OngException(e);
		}

		return listDoacao;
	}

	@Override
	public Doacao consularPorId(int id) throws OngException {

		String	sql = " select d.id as id_doacao, c.id as id_categoria, d.doacao, c.tipoCategoria, d.quantidade from  doacao d  JOIN categoria c on d.id_categoria = c.id where d.id=? ";
		
		Doacao doacao = null;
		Categoria cate = null;
		
		try{

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){

				cate = new Categoria();
				doacao = new Doacao();
				
				doacao.setId(rs.getInt("id_doacao"));
				cate.setId(rs.getInt("id_categoria"));
				doacao.setDoacao(rs.getString("doacao"));
				cate.setTipoCategoria(rs.getString("tipoCategoria"));
				doacao.setQuantidade(rs.getInt("quantidade"));
				doacao.setCategoria(cate);
			}

			return doacao;
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void editar(Doacao doacao) throws OngException {
		
		String sql = "update doacao set doacao=?, id_categoria=?, quantidade=? where id=?";
		PreparedStatement p2;
		try{
			
			p2 = this.connection.prepareStatement(sql);
			
			p2.setString(1, doacao.getDoacao());
			p2.setInt(2, doacao.getCategoria().getId());
			p2.setInt(3, doacao.getQuantidade());
			p2.setInt(4, doacao.getId());
			p2.execute();
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void inativar(int id) throws OngException {
		
		String sql = "delete from doacao where id = " + id;	
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}
