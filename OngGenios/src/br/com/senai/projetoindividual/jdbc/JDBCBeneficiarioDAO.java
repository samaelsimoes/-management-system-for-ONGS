package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.senai.projetoindividual.dao.BeneficiarioDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Beneficiario;

public class JDBCBeneficiarioDAO implements BeneficiarioDAO{
	
	private Connection connection;

	public JDBCBeneficiarioDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Beneficiario beneficiario) throws OngException{
		
		String sql1 = "insert into beneficiario " 
				+"(id ,renda, tamanhoFamilia, membro_id)" 
				+ " values(?,?,?,?)";
		
		PreparedStatement p2;
		
		try{
						
			p2 = this.connection.prepareStatement(sql1);
			
			p2.setInt(1, beneficiario.getId());
			p2.setDouble(2, beneficiario.getRenda());
			p2.setInt(3, beneficiario.getTamanhoFamilhia());
			p2.setInt(4, beneficiario.getMembro().getId());
			
			p2.execute();
		}catch(SQLException e){
			
			throw new OngException(e);
		}
	}

	public List<Beneficiario> consultarNome(String nome) throws OngException {
		
		String sql = "select a.id, b.id , a.nome, a.sobrenome, a.dataNascimento, a.endereco, a.telResidencial, a.telCelular, a.cep, a.email, b.renda, b.tamanhoFamilia " +
		" from pessoas a JOIN beneficiario b on a.id = b.id ";

		if(!nome.equals("null") && !nome.equals("") ){

			sql += " where a.nome like '" + nome + "%'";
		}
			
		List<Beneficiario> listBene = new ArrayList<Beneficiario>();
		Beneficiario bene = null;

		try{
			
			java.sql.Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
					
				bene = new Beneficiario();
				
				
				
				bene.setId(rs.getInt("id"));
				bene.setNome(rs.getString("nome"));
				bene.setSobreNome(rs.getString("sobrenome"));
				Date data = new Date();
				data.setTime(rs.getDate("dataNascimento").getTime());				
				bene.setDataNascimento(data);				
				bene.setEndereco(rs.getString("endereco"));
				bene.setTelResidencial(rs.getLong("telResidencial"));
				bene.setTelCelular(rs.getLong("telCelular"));
				bene.setCep(rs.getLong("cep"));
				bene.setEmail(rs.getString("email"));
				

				bene.setRenda(rs.getFloat("renda"));
				bene.setTamanhoFamilia(rs.getInt("tamanhoFamilia"));
				
				listBene.add(bene);				
			}
					
		}catch(Exception e){
			
			throw new OngException(e);
		}

		return listBene;
	}

	@Override
	public Beneficiario consularPorId(int id)throws OngException {
		
		Beneficiario bene = null;
		
		String sql = "select a.id, b.id , a.nome, a.sobrenome, a.dataNascimento, a.endereco, a.telResidencial, a.telCelular, a.cep, a.email, b.renda, b.tamanhoFamilia" + 
				" from pessoas a JOIN beneficiario b on a.id = b.id where a.id=?";
		
		try{
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
					
				bene = new Beneficiario();
				
				bene.setId(rs.getInt("id"));
				bene.setNome(rs.getString("nome"));
				bene.setSobreNome(rs.getString("sobrenome"));
				Date data = new Date();
				data.setTime(rs.getDate("dataNascimento").getTime());				
				bene.setDataNascimento(data);				
				bene.setEndereco(rs.getString("endereco"));
				bene.setTelResidencial(rs.getLong("telResidencial"));
				bene.setTelCelular(rs.getLong("telCelular"));
				bene.setCep(rs.getLong("cep"));
				bene.setEmail(rs.getString("email"));
				bene.setRenda(rs.getFloat("renda"));
				bene.setTamanhoFamilia(rs.getInt("tamanhoFamilia"));
				
			}
			
			return bene;
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	@Override
	public void editar(Beneficiario beneficiario)throws OngException {
		
		String sql = "update beneficiario set renda=?, tamanhoFamilia=? where id=?";
		PreparedStatement p2;
				
		try{
			
			p2 = this.connection.prepareStatement(sql);					
			p2.setDouble(1, beneficiario.getRenda());
			p2.setInt(2, beneficiario.getTamanhoFamilhia());
			p2.setInt(3, beneficiario.getId());
			
			p2.execute();
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}

	public void inativar(int id) throws OngException {

		String sql = "delete from beneficiario where id = " + id;
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}
