 package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.senai.projetoindividual.dao.MembroDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Membro;

public class JDBCMembroDAO implements MembroDAO {
	
	private Connection connection;
	
	public JDBCMembroDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Membro membro) throws OngException{
		
		String sql1 = "insert into membro " 
				+"(id, login, senha)" 
				+ " values(?,?,?)";
		PreparedStatement p2;
		
		try{
			
			p2 = this.connection.prepareStatement(sql1);
							
			p2.setInt(1, membro.getId());
			p2.setString(2, membro.getLogin());
			p2.setString(3, membro.getSenha());
							
			p2.execute();
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Membro> consultarNome(String nome) throws OngException {

		String sql = "select a.id, b.id , a.nome, a.sobrenome, a.dataNascimento, a.endereco, a.telResidencial, a.telCelular, a.cep, a.email, b.login " +
				" from pessoas a JOIN membro b on a.id = b.id";
		
		if(!nome.equals("null") && !nome.equals("") ){

			sql += " where a.nome like '" + nome + "%'";
		}
		
		List<Membro> listMembro = new ArrayList<Membro>();
		Membro memb = null;
		
		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				memb = new Membro();
				
				memb.setId(rs.getInt("id"));
				memb.setNome(rs.getString("nome"));
				memb.setSobreNome(rs.getString("sobrenome"));
				Date data = new Date();
				data.setTime(rs.getDate("dataNascimento").getTime());				
				memb.setDataNascimento(data);				
				memb.setEndereco(rs.getString("endereco"));
				memb.setTelResidencial(rs.getLong("telResidencial"));
				memb.setTelCelular(rs.getLong("telCelular"));
				memb.setCep(rs.getLong("cep"));
				memb.setEmail(rs.getString("email"));
				memb.setLogin(rs.getString("login"));
				
				listMembro.add(memb);				
			}
		}catch(Exception e){
			
			throw new OngException(e);
		}
		
		return listMembro;
	}

	@Override
	public Membro consularPorId(int id) throws OngException {
		
		Membro memb = null;
		
		String sql = "select a.id, b.id , a.nome, a.sobrenome, a.dataNascimento, a.endereco, a.telResidencial, a.telCelular, a.cep, a.email, b.login "+ 
		" from pessoas a JOIN membro b on a.id = b.id where a.id=?";	
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				memb = new Membro();
				
				memb.setId(rs.getInt("id"));
				memb.setNome(rs.getString("nome"));
				memb.setSobreNome(rs.getString("sobrenome"));
				Date data = new Date();
				data.setTime(rs.getDate("dataNascimento").getTime());				
				memb.setDataNascimento(data);				
				memb.setEndereco(rs.getString("endereco"));
				memb.setTelResidencial(rs.getLong("telResidencial"));
				memb.setTelCelular(rs.getLong("telCelular"));
				memb.setCep(rs.getLong("cep"));
				memb.setEmail(rs.getString("email"));
				memb.setLogin(rs.getString("login"));
				
			}
			
			return memb;
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void editar(Membro membro) throws OngException {
		String sql= "";
		if(membro.getSenha() != "" ){
			sql = "update membro set login=?, senha=? where id=?";
		}else{
			
			sql = "update membro set login=? where id=?";
		}
		
		PreparedStatement p2;

		try{
			if(membro.getSenha() != "" ){
				p2 = this.connection.prepareStatement(sql);
				p2.setString(1, membro.getLogin());
				p2.setString(2, membro.getSenha());
				p2.setInt(3, membro.getId());
			}else{
				p2 = this.connection.prepareStatement(sql);
				p2.setString(1, membro.getLogin());
				p2.setInt(2, membro.getId());
			}
			
			p2.execute();
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
	
	public void inativar(int id) throws OngException {

		String sql = "delete from membro where id = " + id;
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public Membro buscaPorLogin(Membro membro) throws OngException{

		Membro memb = null;
		
		String sql = "select a.id, a.nome, a.sobrenome, a.email, a.cep, b.login from pessoas a JOIN membro b on a.id = b.id where b.login ='" + membro.getLogin() +
				"' and b.senha  ='" + membro.getSenha() +"'"; 
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				memb = new Membro();
				
				memb.setId(rs.getInt("id"));
				memb.setNome(rs.getString("nome"));
				memb.setSobreNome(rs.getString("sobrenome"));
				memb.setEmail(rs.getString("email"));
				memb.setCep(rs.getLong("cep"));
				memb.setLogin(rs.getString("login"));
			}
			
		}catch(Exception e){
			
			throw new OngException(e);		
		}
		
		return memb;
	}
}
