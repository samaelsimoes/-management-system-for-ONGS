package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.senai.projetoindividual.dao.FinanceiroDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.objeto.Membro;

public class JDBCFinanceiroDAO implements FinanceiroDAO{
	
	private Connection connection;

	public JDBCFinanceiroDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void depositar(Financeiro financeiro) throws OngException {
				
		String sql = "insert into financeiro (id_responsavel,dataDeposito,deposito)" +  "values(?,?,?)";
		PreparedStatement p2;
		
		try{
			
			Timestamp data = new Timestamp(financeiro.getDataDeposito().getTime());		
			
			p2 = this.connection.prepareStatement(sql);
			p2.setInt(1, financeiro.getId_responsavel().getId());
			p2.setTimestamp(2, data);
			p2.setDouble(3, financeiro.getDeposito());
			p2.execute();
		}catch(SQLException e){
			throw new OngException(e);
		}
	}

	@Override
	public void saque(Financeiro financeiro) throws OngException {
		String sql = "insert into financeiro (id_responsavel, dataSaque, saque, motivoEditacao)" +  "values(?,?,?,?)";
		PreparedStatement p2;
		try{
			
			Timestamp data = new Timestamp(financeiro.getDataSaque().getTime());
			
			p2 = this.connection.prepareStatement(sql);
			p2.setInt(1,financeiro.getId_responsavel().getId());
			p2.setTimestamp(2, data);
			p2.setDouble(3, financeiro.getSaque());
			p2.setString(4, financeiro.getMotivo());
			p2.execute();
			
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Financeiro> buscar(String categoria) throws OngException {

		String sql = "";
		
		if(categoria.equals("1")){
			sql = " select f.id , f.deposito, f.dataDeposito,f.id_responsavel, p.nome as NomeResponsavel, p.sobrenome as SobreNomeResponsavel, " + 
				  " f.motivoEditacao from financeiro f inner join pessoas p on p.id = f.id_responsavel  and f.deposito is not null ";
		}

		if(categoria.equals("2")){		
			sql = "select f.id , f.saque, f.dataSaque, f.id_responsavel,  p.nome as NomeResponsavel, p.sobrenome as SobreNomeResponsavel," +  
				  "f.motivoEditacao from financeiro f inner join pessoas p on p.id = f.id_responsavel  and f.saque is not null ";
		}
		
		PreparedStatement p2;
		List<Financeiro> listFinanceiro = new ArrayList<Financeiro>();
		Financeiro financeiro = null;
		Membro membro = null;
		
		try{
		
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			p2 = this.connection.prepareStatement(sql);
					
			if(categoria.equals("1")){
				while(rs.next()){
					
					membro = new Membro();
					financeiro = new Financeiro();
					Date data = new Date();
					
					financeiro.setId(rs.getInt("id"));
					financeiro.setDeposito(rs.getFloat("deposito"));
					
					membro.setId(rs.getInt("id_responsavel"));
					membro.setNome(rs.getString("nomeResponsavel"));
					membro.setSobreNome(rs.getString("sobreNomeResponsavel"));
					
					financeiro.setMotivo(rs.getString("motivoEditacao"));
					financeiro.setId_responsavel(membro);

					if(rs.getDate("dataDeposito") != null){
						
						data.setTime(rs.getTimestamp("dataDeposito").getTime());				
						financeiro.setDataDeposito(data);	
					}	
					
					listFinanceiro.add(financeiro);
				}
			}

			if(categoria.equals("2")){

				while(rs.next()){
					
					membro = new Membro();
					financeiro = new Financeiro();
					Date data = new Date();
					
					financeiro.setId(rs.getInt("id"));					
					financeiro.setSaque(rs.getFloat("saque"));
					
					membro.setId(rs.getInt("id"));
					membro.setId(rs.getInt("id_responsavel"));

					membro.setNome(rs.getString("nomeResponsavel"));
					membro.setSobreNome(rs.getString("sobreNomeResponsavel"));
					
					financeiro.setMotivo(rs.getString("motivoEditacao"));
					financeiro.setId_responsavel(membro);
					
					if(rs.getDate("dataSaque") != null){
						
						data.setTime(rs.getTimestamp("dataSaque").getTime());				
						financeiro.setDataSaque(data);	
					}	
					
					listFinanceiro.add(financeiro);
				}
			}
		}catch(SQLException e){
			throw new OngException(e);
		}
		return listFinanceiro;
	}

	@Override
	public double listaTotaldeposito() throws OngException {
		
		String sql = "select (SELECT SUM(deposito) FROM financeiro)  as totaldeposito";
		
		PreparedStatement p2;
		Financeiro financeiro = null;

		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			p2 = this.connection.prepareStatement(sql);
			
			while(rs.next()){
				
				financeiro = new Financeiro();
				financeiro.setListaTotaldeposito(rs.getDouble("totaldeposito"));
			}
			
		}catch(SQLException e){
			
			throw new OngException(e);
		}
		double retorno = financeiro.getListaTotaldeposito();
		return retorno;
	}
	
	@Override
	public double listaTotalsaque() throws OngException {
		
		String sql = "select (SELECT SUM(saque) FROM financeiro)  as totalsaque";
		
		PreparedStatement p2;
		Financeiro financeiro = null;

		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			p2 = this.connection.prepareStatement(sql);
			
			while(rs.next()){
				
				financeiro = new Financeiro();
				financeiro.setListaTotalsaque(rs.getDouble("totalsaque"));
			}
			
		}catch(SQLException e){
			
			throw new OngException(e);
		}
		double retorno = financeiro.getListaTotalsaque();
		return retorno;
	}

	@Override
	public void inativar(int id) throws OngException {
		String sql = "delete from financeiro where id = " + id;	
		PreparedStatement p;
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
		
	}

	@Override
	public Financeiro consularPorId(int id, String tipo) throws OngException {
		
		Financeiro finan = null;
		Membro membro = null;
		if(tipo.equals("1")){
			
			String sql = "select id,deposito, id_responsavel from financeiro where id=?";
			
			try{
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					
					finan = new Financeiro();
					membro = new Membro();
					
					finan.setId(rs.getInt("id"));
					finan.setDeposito(rs.getFloat("deposito"));
					membro.setId(rs.getInt("id_responsavel"));
					finan.setId_responsavel(membro);
				}
			}catch (Exception e) {
				throw new OngException(e);
			}

		}else if(tipo.equals("2")){
			
			String sql = "select id,saque, id_responsavel, motivoEditacao from financeiro where id=?";
			try{
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()){
					
					finan = new Financeiro();
					membro = new Membro();
					
					finan.setId(rs.getInt("id"));
					finan.setSaque(rs.getFloat("saque"));
					membro.setId(rs.getInt("id_responsavel"));
					finan.setId_responsavel(membro);
					finan.setMotivo(rs.getString("motivoEditacao"));
				}
			}catch (Exception e) {
				throw new OngException(e);
			}
		}		
		return finan;
	}

	@Override
	public void editar(Financeiro finan) throws OngException {
		try{

			if(finan.getCategoria().equals("1")){
				String sql = "update financeiro set deposito=?, motivoEditacao=? where id=?";
				PreparedStatement p2;
				try{
					
					p2 = this.connection.prepareStatement(sql);
					
					p2.setDouble(1, finan.getDeposito());
					p2.setString(2, finan.getMotivoEditacao());
					p2.setInt(3, finan.getId());
					p2.execute();					
				}catch (Exception e) {
					throw new OngException(e);
				}
				
			}else if(finan.getCategoria().equals("2")){
				
				String sql = "update financeiro set saque=?, motivoEditacao=? where id=?";
				PreparedStatement p2;
				try{
					
					p2 = this.connection.prepareStatement(sql);
					
					p2.setDouble(1, finan.getSaque());
					p2.setString(2, finan.getMotivoEditacao());
					p2.setInt(3, finan.getId());
					p2.execute();					
				}catch (Exception e) {
					throw new OngException(e);
				}
			}
		}catch (Exception e){
			throw new OngException(e);
		}
	}

	
}
