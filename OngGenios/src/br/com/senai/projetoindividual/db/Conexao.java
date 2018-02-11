package br.com.senai.projetoindividual.db;

import java.sql.Connection;

import br.com.senai.projetoindividual.exception.OngException;
import br.com.senai.projetoindividual.exception.OngException;

public class Conexao {
	
	private Connection connection;
	
	public Connection abrirConexao() throws Exception{
		try{
			
			Class.forName("org.gjt.mm.mysql.Driver");
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/onggenios","root","root");
		}catch(Exception e){	
			e.printStackTrace();
			throw new OngException("Erro inesperado no banco", e);
		}
		return connection;
	}
	
	public void fecharConexao() throws Exception{					
		if(connection == null){
			
			throw new OngException("Algum erro inesperaaado no banco");
		}else{
			
			connection.close();
		}
	}
}
