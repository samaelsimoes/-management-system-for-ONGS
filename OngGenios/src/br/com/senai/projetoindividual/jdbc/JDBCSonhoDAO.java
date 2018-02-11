package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.senai.projetoindividual.dao.SonhoDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.objeto.Prioridade;
import br.com.senai.projetoindividual.objeto.Sonho;

public class JDBCSonhoDAO implements SonhoDAO {

	private Connection connection;
	
	public JDBCSonhoDAO(Connection connection) {
		
		this.connection = connection;	
	}

	@Override
	public void adicionar(Sonho sonho) throws OngException {

		String sql = "insert into sonho (id_beneficiario, id_responsavel, tipoSonho, prioridade) " + "  values(?, ?, ?, ?)";
		PreparedStatement p2;
		try{
			p2 = this.connection.prepareStatement(sql);
			
			p2.setInt(1, sonho.getBeneficiario().getId());
			p2.setInt(2, sonho.getResponsavel().getId());
			p2.setString(3, sonho.getSonho());
			
			p2.setInt(4, sonho.getPrioridade().getId());
			
			p2.execute();
		}catch(SQLException e){ 
			
			throw new OngException(e);
		}
	}

	@Override
	public List<Sonho> consultarSonho(String busca, String prioridade) throws OngException {
		
		String sql="select s.id, p.nome as nomeBeneficiario, p.sobrenome as sobreNomeBeneficiario, s.tipoSonho as sonho, pr.nomePrioridade, pm.nome as responsavel " +
				   " from pessoas p " +
				   " Inner join beneficiario b on (b.id = p.id) "+
			       " Inner join sonho s on (s.id_beneficiario = b.id) " +
			       " Inner join prioridade pr on (s.prioridade = pr.id) " +
				   " Inner join membro m on (m.id = s.id_responsavel) " +
				   " Inner join pessoas pm on (pm.id = m.id) ";
		/*
		 * null test 3
		 * teste test 1
		 */
		
		if(busca.equals("null") && !prioridade.equals("null")){
			sql += " where pr.id = '"  + prioridade + "'";
		}
		
		if(!prioridade.equals("null") && !busca.equals("null")){
			sql += " where pr.id = " + prioridade;
			sql += " and s.tipoSonho like '%" + busca + "%'";
		}
			
		List<Sonho> listSonho = new ArrayList<Sonho>();
		Sonho sonho = null;
		
		Beneficiario beneficiario = null;
		Prioridade prioridad = null;
		Membro membro = null;
		
		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				sonho = new Sonho();
				beneficiario = new Beneficiario();
				prioridad = new Prioridade();
				membro = new Membro();
								
				sonho.setId(rs.getInt("id"));
				beneficiario.setNome(rs.getString("nomeBeneficiario"));
				beneficiario.setSobreNome(rs.getString("sobreNomeBeneficiario"));
				sonho.setSonho(rs.getString("sonho"));
				prioridad.setNomePrioridade(rs.getString("nomePrioridade"));
				membro.setNome(rs.getString("responsavel"));
				membro.setId(rs.getInt("id"));
				sonho.setBeneficiario(beneficiario);
				sonho.setPrioridade(prioridad);
				sonho.setResponsavel(membro);
				
				listSonho.add(sonho);				
			}
		}catch(Exception e){
			
			throw new OngException(e);
		}
		
		return listSonho;
	}

	@Override
	public Sonho consultarPorId(int id) throws OngException {
		
		String sql = "select s.id, pr.id, p.nome as nomeBeneficiario, p.sobrenome as sobreNomeBeneficiario, s.tipoSonho as Sonho, " + 
					"pr.nomePrioridade, pm.nome as responsavel  from pessoas p  " +
					"Inner join beneficiario b on (b.id = p.id)  " +
					"Inner join sonho s on (s.id_beneficiario = b.id) " +
					"Inner join prioridade pr on (s.prioridade = pr.id) " +
					"Inner join membro m on (m.id = s.id_responsavel)  " +
					"Inner join pessoas pm on (pm.id = m.id) where s.id =?";
		
		Sonho sonho = null;
		Beneficiario beneficiario = null;
		Prioridade prioridad = null;
		Membro membro = null;		
		Categoria cate = null;
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				cate = new Categoria();
				sonho = new Sonho();
				beneficiario = new Beneficiario();
				prioridad = new Prioridade();
				membro = new Membro();
				cate = new Categoria();
				
				sonho.setId(rs.getInt("id"));
				beneficiario.setNome(rs.getString("nomeBeneficiario"));
				beneficiario.setSobreNome(rs.getString("sobreNomeBeneficiario"));
				sonho.setSonho(rs.getString("Sonho"));
				
				prioridad.setId(rs.getInt("id"));
				prioridad.setNomePrioridade(rs.getString("nomePrioridade"));
				membro.setNome(rs.getString("responsavel"));
				membro.setId(rs.getInt("id"));
				sonho.setBeneficiario(beneficiario);
				sonho.setPrioridade(prioridad);
				sonho.setResponsavel(membro);
			}
			
			return sonho;
		}catch(Exception e){
			
			throw new OngException(e);
		}		
	}

	@Override
	public void editar(Sonho sonho) throws OngException {

		String sql = " update sonho set tipoSonho=?, prioridade=?, id_beneficiario=? where id=?";
		PreparedStatement p2;

		try{
			
			p2 = this.connection.prepareStatement(sql);
			
			p2.setString(1, sonho.getSonho());
			p2.setInt(2, sonho.getPrioridade().getId());
			p2.setInt(3, sonho.getBeneficiario().getId());
			p2.setInt(4, sonho.getId());
			p2.execute();
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void inativar(int id) throws OngException {
		
		String sql = "delete from sonho where id = " + id;	
		PreparedStatement p;
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}













