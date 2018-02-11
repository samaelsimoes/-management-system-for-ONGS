package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.senai.projetoindividual.dao.EventoDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.objeto.Pessoa;

public class JDBCEventoDAO implements EventoDAO {
	private Connection connection;

	public JDBCEventoDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Evento evento) throws OngException {
		
		String sql = "insert into evento (id_responsavel, endereco, latitude, longetude, dataEvento, contato, mensagem,horario, nomeEvento)" + "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement p2;

		try{
			
			p2 = this.connection.prepareStatement(sql);
			p2.setInt(1,evento.getResponsavel().getId());
			p2.setString(2, evento.getEndereco());
			p2.setDouble(3, evento.getLatitude());
			p2.setDouble(4, evento.getLongetude());
			
			p2.setDate(5,  new java.sql.Date(evento.getDataEvento().getTime()));	
			p2.setLong(6, evento.getContato());
			p2.setString(7, evento.getMensagem());
			p2.setString(8, evento.getHorario());
			p2.setString(9, evento.getNomeEvento());
			p2.execute();

		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Evento> buscar(String nome, String categoria) throws OngException {
		
		List<Evento> listEvento = new ArrayList<Evento>();
		
		Evento evento = null;
		Pessoa pessoa = null;
		Membro membro = null;
		
		if(categoria.equals("1")){
			
			String sql = "select e.id, e.nomeEvento, e.endereco, e.dataEvento, e.horario , p.nome, e.contato as contatoResponsavel, e.mensagem" + 
					     " from evento e inner join pessoas p on p.id = e.id_responsavel";
			
			PreparedStatement p2;
			
			if(nome != null && !nome.equals("null")){
				 
				sql += " where e.nomeEvento like'" + nome + "%'";
			}
		
			try{
	
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				p2 = this.connection.prepareStatement(sql);				
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					membro = new Membro();
					
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					evento.setEndereco(rs.getString("endereco"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setHorario(rs.getString("horario"));
					membro.setId(rs.getInt("id"));
					membro.setNome(rs.getString("nome"));
					evento.setResponsavel(membro);
					evento.setContato(rs.getLong("contatoResponsavel"));
					evento.setMensagem(rs.getString("mensagem"));
					
					listEvento.add(evento);
				}
				
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("2")){
			
			String sql = "select e.id, e.nomeEvento, e.dataEvento, e.horario, p.nome, p.sobrenome from evento e "
						+ "inner join pessoas p on p.id = id_beneficiario";
			
			if(nome != null && !nome.equals("null")){
				 
				sql += " where e.nomeEvento like'" + nome + "%'";
			}
			
			PreparedStatement p2;

			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				p2 = this.connection.prepareStatement(sql);
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					pessoa = new Pessoa();
					
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setHorario(rs.getString("horario"));
				
					pessoa.setId(rs.getInt("id"));
					pessoa.setNome(rs.getString("nome"));
					pessoa.setSobreNome(rs.getString("sobrenome"));
					
					evento.setBeneficiario(pessoa);
					
					listEvento.add(evento);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("3")){
			
			String sql = "select id, nomeEvento, dataEvento, horario from evento ";
			PreparedStatement p2;

			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				p2 = this.connection.prepareStatement(sql);
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
				
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setHorario(rs.getString("horario"));
										
					listEvento.add(evento);
				}
				
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("4")){
			
			String sql = "SELECT id, latitude, longetude, dataEvento, endereco FROM evento WHERE dataEvento BETWEEN CURDATE() AND CURDATE() + INTERVAL 30 DAY order by dataEvento desc";
			PreparedStatement p2;

			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				p2 = this.connection.prepareStatement(sql);
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();

					evento.setId(rs.getInt("id"));
					evento.setLatitude(rs.getDouble("latitude"));
					evento.setLongetude(rs.getDouble("longetude"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setEndereco(rs.getString("endereco"));
					
					listEvento.add(evento);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("5")){
			
			String sql = "select e.id, e.nomeEvento, e.endereco, e.latitude, e.longetude, e.dataEvento, e.contato, e.mensagem, e.horario, p.nome, p.sobrenome from evento e "+
			"inner join pessoas p on e.id_responsavel = p.id";
			
			PreparedStatement p2;

			try{
				
				java.sql.Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				p2 = this.connection.prepareStatement(sql);
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					pessoa = new Pessoa();

					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					evento.setEndereco(rs.getString("endereco"));
					evento.setLatitude(rs.getDouble("latitude"));
					evento.setLongetude(rs.getDouble("longetude"));
				
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setContato(rs.getLong("contato"));
					evento.setMensagem(rs.getString("mensagem"));
					evento.setHorario(rs.getString("horario"));
					pessoa.setId(rs.getInt("id"));
					pessoa.setNome(rs.getString("nome"));
					pessoa.setSobreNome(rs.getString("sobrenome"));

					
					listEvento.add(evento);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		return listEvento;
	}

	@Override
	public Evento consularPorId(int id, String categoria) throws OngException {
		
		Evento evento = null;
		Pessoa pessoa = null;
		Membro membro = null;

		if(categoria.equals("1")){
			
			String sql = "select e.id, e.nomeEvento, e.endereco, e.dataEvento, e.horario , p.nome,e.contato as contatoResponsavel, e.mensagem " 
                    +" from evento e inner join pessoas p on p.id = e.id_responsavel where e.id=?";

			try{
				
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					membro = new Membro();
					
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					evento.setEndereco(rs.getString("endereco"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setHorario(rs.getString("horario"));
					membro.setId(rs.getInt("id"));
					membro.setNome(rs.getString("nome"));
					evento.setResponsavel(membro);
					evento.setContato(rs.getLong("contatoResponsavel"));
					evento.setMensagem(rs.getString("mensagem"));
				}
			}catch(Exception e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("2")){
			
			String sql = "select e.id, e.nomeEvento, e.dataEvento, e.horario, p.nome, p.sobrenome from evento e "
						+ "inner join pessoas p on p.id = id_beneficiario where e.id=?";
			try{
				
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					pessoa = new Pessoa();
					
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setHorario(rs.getString("horario"));
					pessoa.setId(rs.getInt("id"));
					pessoa.setNome(rs.getString("nome"));
					pessoa.setSobreNome(rs.getString("sobrenome"));
					evento.setBeneficiario(pessoa);
				}
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		
		if(categoria.equals("3")){
			
			String sql = "select e.id, e.nomeEvento, e.endereco, e.latitude, e.longetude, e.dataEvento, e.contato, e.mensagem," 
						+" e.horario, p.nome, p.sobrenome from evento e inner join pessoas p on e.id_responsavel = p.id where e.id=?";
			try{
				
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					
					evento = new Evento();
					Date data = new Date();
					membro = new Membro();
					
					evento.setId(rs.getInt("id"));
					evento.setNomeEvento(rs.getString("nomeEvento"));
					evento.setEndereco(rs.getString("endereco"));
					evento.setLatitude(rs.getDouble("latitude"));
					evento.setLongetude(rs.getDouble("longetude"));
					data.setTime(rs.getTimestamp("dataEvento").getTime());				
					evento.setDataEvento(data);	
					evento.setContato(rs.getLong("contato"));
					evento.setMensagem(rs.getString("mensagem"));
					evento.setHorario(rs.getString("horario"));
					membro.setId(rs.getInt("id"));
					membro.setNome(rs.getString("nome"));
					evento.setResponsavel(membro);
				}
					
			}catch(SQLException e){
				
				throw new OngException(e);
			}
		}
		return evento;
	}

	@Override
	public void editar(Evento evento) throws OngException {

		try{
			if(evento.getValorLista().equals("2")){
				
				String sql = "update evento set nomeEvento=?, dataEvento=?, horario=?" + "where id=?";;
				PreparedStatement p2;
	
				p2 = this.connection.prepareStatement(sql);
				
				p2.setString(1, evento.getNomeEvento());
				p2.setDate(2,  new java.sql.Date(evento.getDataEvento().getTime()));	
				p2.setString(3, evento.getHorario());
				p2.setInt(4, evento.getId());
				p2.execute();

			}else if(evento.getValorLista().equals("1")){
								
				String sql = "update evento set nomeEvento=?, dataEvento=?, horario=?, contato=?,mensagem=?" + "where id=?";
				PreparedStatement p2;
				
				p2 = this.connection.prepareStatement(sql);
				
				p2.setString(1, evento.getNomeEvento());
				p2.setDate(2,  new java.sql.Date(evento.getDataEvento().getTime()));	
				p2.setString(3, evento.getHorario());
				p2.setLong(4, evento.getContato());
				p2.setString(5, evento.getMensagem());
				p2.setInt(6, evento.getId());
				p2.execute();
			}
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void inativar(int id) throws OngException {

		String sql = "delete from evento where id = " + id;	
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}
