package br.com.senai.projetoindividual.objeto;

public class Membro extends Pessoa{
	
	private String login;
	private String senha;
	
	public void setLogin(String login){
		this.login = login;
	}
	
	public void setSenha(String senha){
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getSenha() {
		return senha;
	}
}
