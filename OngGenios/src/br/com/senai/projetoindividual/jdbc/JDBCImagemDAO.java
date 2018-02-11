package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.ImagemDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;

public class JDBCImagemDAO implements ImagemDAO{

	private Connection connection;

	public JDBCImagemDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionarImagem(Imagem imagem) throws OngException {

		String sql = "insert into imagem" 
				 + "(nome, galeria_id, informacaoImagem)" 
			     + "values (?,?,?)";
		
		PreparedStatement p;
	
		try{
			
			p = this.connection.prepareStatement(sql);
			
			p.setString(1, imagem.getNome());
			p.setInt(2, imagem.getGaleria_id());
			p.setString(3, imagem.getInformacaoImagem());
			
			p.execute();
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Imagem> buscar(int id) throws OngException {

		List<Imagem> listimagem = new ArrayList<Imagem>();
		String sql = "select i.id, i.informacaoImagem, i.galeria_id, i.nome, g.nome as nomeGaleria from imagem i, galeria g where i.galeria_id = g.id and i.galeria_id=?";
		
		Imagem imagem = null;
		Galeria galeria = null;
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				imagem = new Imagem();
				galeria = new Galeria();
				
				imagem.setId(rs.getInt("id"));
				imagem.setInformacaoImagem(rs.getString("informacaoImagem"));
				imagem.setGaleria_id(rs.getInt("galeria_id"));
				imagem.setNome(rs.getString("nome"));
				galeria.setNome(rs.getString("nomeGaleria"));
				imagem.setNomeGaleria(galeria);
				
				listimagem.add(imagem);
			}
			
			return listimagem;
		}catch (Exception e) {
			
			throw new OngException(e);
		}
		
	}

	@Override
	public void editar(Imagem imagem) throws OngException {
				
		if(imagem.getNome() == null || imagem.getNome().equals("")){
					
			String sql = "update imagem set informacaoImagem=? where id=?";
			PreparedStatement p1;
			
			try{
				
				p1 = this.connection.prepareStatement(sql);
				
				p1.setString(1, imagem.getInformacaoImagem());
				p1.setInt(2, imagem.getId());
	
				p1.execute();
			}catch (Exception e) {
				
				throw new OngException(e);
			}
		
		}else if(imagem.getNome() != null || !imagem.getNome().equals("")){
				
			String sql = "update imagem set informacaoImagem=?,  nome=? where id=?";
			PreparedStatement p1;
			
			try{
				
				p1 = this.connection.prepareStatement(sql);
				
				p1.setString(1, imagem.getInformacaoImagem());
				p1.setString(2, imagem.getNome());
				p1.setInt(3, imagem.getId());
	
				p1.execute();
			}catch (Exception e) {
				
				throw new OngException(e);
			}
		}
		
	}

	@Override
	public void inativar(int id) throws OngException {
		
		String sql =" delete from imagem where id = " + id;	
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
		
	}
}
