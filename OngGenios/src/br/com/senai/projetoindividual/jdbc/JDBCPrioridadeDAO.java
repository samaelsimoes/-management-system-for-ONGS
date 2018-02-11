package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.PrioridadeDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Prioridade;

public class JDBCPrioridadeDAO implements PrioridadeDAO {

	private Connection connection;
	
	public JDBCPrioridadeDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Prioridade prioridade) throws OngException {

		String sql = "insert into prioridade (nomePrioridade,responsavelCadastro) " + "  values(?,?)";
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			
			p.setString(1, prioridade.getNomePrioridade());
			p.setInt(2, prioridade.getResponsavelCadastro().getId());
			
			p.execute();

		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Prioridade> consultar() throws OngException{
		
		String sql= "select * from prioridade";
		
		List<Prioridade> listPrioridade = new ArrayList<Prioridade>();
		Prioridade prioridade = null;
		
		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				prioridade = new Prioridade();
				
				
				prioridade.setId(rs.getInt("id"));
				prioridade.setNomePrioridade(rs.getString("nomePrioridade"));

				listPrioridade.add(prioridade);
			}
			
		}catch(Exception e){
			throw new OngException(e);
		}
		return listPrioridade;
	}

	@Override
	public void inativar(int id) throws OngException {
		
		String sql="delete from prioridade where id=" + id;
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){			
			throw new OngException(e);
		}
		
	}

	@Override
	public void editar(Prioridade prio) throws OngException {
		

		String sql= "update prioridade set nomePrioridade=? where id=?";
		PreparedStatement p1;

		try{
			p1 = this.connection.prepareStatement(sql);
			
			p1.setString(1, prio.getNomePrioridade());
			p1.setInt(2, prio.getId());

			p1.execute();
		}catch (Exception e) {
			throw new OngException(e);
		}
	}
	
	@Override
	public Prioridade consularPorId(int id) throws OngException {
		
		Prioridade prio =null;
		String sql = "select id, nomePrioridade from prioridade where id=?";
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
					
				prio = new Prioridade();
				
				prio.setId(rs.getInt("id"));
				prio.setNomePrioridade(rs.getString("nomePrioridade"));
			}

			return prio;
		}catch(Exception e){			
			throw new OngException(e);
		}
	}
}
