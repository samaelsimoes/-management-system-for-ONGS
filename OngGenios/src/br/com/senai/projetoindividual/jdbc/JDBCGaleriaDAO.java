package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.GaleriaDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;

public class JDBCGaleriaDAO implements GaleriaDAO {

	private Connection connection;

	public JDBCGaleriaDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Galeria galeria) throws OngException {

		String sql = "insert into galeria" 
					 + "(id,nome, id_evento, informacaoGaleria, nomeImagem)" 
				     + "values (?,?,?,?,?)";
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			
			p.setInt(1, galeria.getId());
			p.setString(2, galeria.getNome());
			p.setInt(3, galeria.getId_evento().getId());
			p.setString(4, galeria.getInformacaoGaleria());
			p.setString(5, galeria.getNomeImagem());
			
			p.execute();
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Galeria> buscar(String nome,String tipo) throws OngException {
		
		String sql = "";
		
		PreparedStatement p2;
		List<Galeria> listGaleria = new ArrayList<Galeria>();
		Galeria galeria = null;
		
		if(tipo.equals("1")){
			
			sql= "select id,nome from galeria";
			
			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				p2 = this.connection.prepareStatement(sql);
							
				while(rs.next()){
					
					galeria = new Galeria();
					
					galeria.setId(rs.getInt("id"));
					galeria.setNome(rs.getString("nome"));
					
					listGaleria.add(galeria);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(tipo.equals("2")){
			
			sql = "select id, informacaoGaleria, id_evento, nomeImagem, nome from galeria";
			
			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				p2 = this.connection.prepareStatement(sql);
				
				while(rs.next()){
					
					galeria = new Galeria();
					Evento evento = new Evento();
					
					galeria.setId(rs.getInt("id"));
					galeria.setInformacaoGaleria(rs.getString("informacaoGaleria"));
					evento.setId(rs.getInt("id_evento"));
					galeria.setId_evento(evento);
					galeria.setNomeImagem(rs.getString("nomeImagem"));
					galeria.setNome(rs.getString("nome"));
					
					listGaleria.add(galeria);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
			
		}
		
		return listGaleria;
	}

	@Override
	public Galeria consularPorId(int id) throws OngException {

		String sql = "select id, nome, informacaoGaleria, nomeImagem  from Galeria where id=?";	//Obs imagem não posso trazer por conta, que nas imagem salvas na hora do upload nao troca o nome! deu pra sacar
		Galeria galeria = null;
		
		try{

			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();

			while(rs.next()){
				
				galeria = new Galeria();
							
				galeria.setId(rs.getInt("id"));
				galeria.setNome(rs.getString("nome"));
				galeria.setInformacaoGaleria(rs.getString("informacaoGaleria"));
				galeria.setNomeImagem(rs.getString("nomeImagem"));
			}

			return galeria;
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void editar(Galeria galeria) throws OngException {
		
		if(galeria.getNomeImagem() == null || galeria.getNomeImagem().equals("")){
			
			String sql= "update galeria set nome=?, id_evento=?, informacaoGaleria=? where id=?";
			PreparedStatement p1;
			
			try{
				
				p1 = this.connection.prepareStatement(sql);
				
				p1.setString(1, galeria.getNome());
				p1.setInt(2, galeria.getId_evento().getId());
				p1.setString(3, galeria.getInformacaoGaleria());
				p1.setInt(4, galeria.getId());
				
				p1.execute();
			}catch (Exception e) {
				
				throw new OngException(e);
			}
			
		}else if(!galeria.getNomeImagem().equals("null") ||galeria.getNomeImagem() != null || !galeria.getNomeImagem().equals("") ){
				
			String sql= "update galeria set nome=?, id_evento=?,  informacaoGaleria=?, nomeImagem=? where id=?";
			PreparedStatement p1;

			try{
				
				p1 = this.connection.prepareStatement(sql);
				
				p1.setString(1, galeria.getNome());
				p1.setInt(2, galeria.getId_evento().getId());
				p1.setString(3, galeria.getInformacaoGaleria());
				p1.setString(4, galeria.getNomeImagem());
				p1.setInt(5, galeria.getId());
				
				p1.execute();
			}catch (Exception e) {
				throw new OngException(e);
			}
		} 	
	}

	@Override
	public void inatir(int id) throws OngException {

		String sql =" delete from galeria where id = " + id;	
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}
