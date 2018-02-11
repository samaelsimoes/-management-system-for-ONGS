package br.com.senai.projetoindividual.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.senai.projetoindividual.dao.PessoaDAO;
import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.objeto.Pessoa;

public class JDBCPessoaDAO implements PessoaDAO{
	
	private Connection connection;
	
	public JDBCPessoaDAO(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void adicionar(Pessoa pessoa) throws OngException{
				
		String sql1 = "insert into pessoas " 
				+ "(nome, sobrenome, datanascimento, endereco, telResidencial, telcelular, cep, email )" 
				+ "values(?,?,?,?,?,?,?,?)";
		
		ResultSet rs = null;
		PreparedStatement p1;
		
		try{
			
			p1 = this.connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
			
			p1.setString(1, pessoa.getNome());
			p1.setString(2, pessoa.getSobreNome());
			p1.setDate(3,  new java.sql.Date(pessoa.getDataNascimento().getTime()));	
			p1.setString(4, pessoa.getEndereco());
			p1.setLong(5, pessoa.getTelResidencial());
			p1.setLong(6, pessoa.getTelCelular());
			p1.setLong(7,pessoa.getCep());
			p1.setString(8, pessoa.getEmail());
						
			p1.execute();
			rs = p1.getGeneratedKeys();
			
			if(rs.next()){
	
				pessoa.setId(rs.getInt(1));
			}else{
				
				throw new OngException(" Erro id da pessoa não foi gerado");
			}
		}catch(Exception e){
			
			throw new OngException(e);
		}

	}

	@Override
	public void editar(Pessoa pessoa)throws OngException {
		
		int id = pessoa.getId();
				
		String sql1 = "UPDATE pessoas SET nome=?, sobrenome=?, dataNascimento=?, endereco=?, telResidencial=?, telCelular=?, cep=?, email=? " +
		"where id=?";
		
		PreparedStatement p1;		
		ResultSet rs = null;
				
		try{
			
			p1 = this.connection.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
			
			p1.setString(1, pessoa.getNome());
			p1.setString(2, pessoa.getSobreNome());
						
			p1.setDate(3,  new java.sql.Date(pessoa.getDataNascimento().getTime()));	
			p1.setString(4, pessoa.getEndereco());
			p1.setLong(5, pessoa.getTelResidencial());
			p1.setLong(6, pessoa.getTelCelular());
			p1.setLong(7,pessoa.getCep());
			p1.setString(8, pessoa.getEmail());
			p1.setInt(9,id);
			
			p1.executeUpdate();
			rs = p1.getGeneratedKeys();
				
			pessoa.setId(id);
		}catch(Exception e){
			throw new OngException(e);
		}
	}

	public void inativar(int id) throws OngException {

		String sql = "delete from pessoas where id = " + id;
		PreparedStatement p;
		
		try{
			
			p = this.connection.prepareStatement(sql);
			p.execute(sql); 
		}catch(Exception e){
			
			throw new OngException(e);
		}
	}
}
