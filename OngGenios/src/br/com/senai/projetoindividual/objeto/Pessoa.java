package br.com.senai.projetoindividual.objeto;

import java.util.Date;

public class Pessoa {

	private int id;
	private String nome;
	private String sobreNome;
	private String endereco;
	private String email;
	private long telCelular;
	private long telResidencial;
	private long cep;
	private Date dataNascimento ;
	private boolean ativo ;
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getNome() {
		
		return nome;
	}
	
	public void setNome(String nome) {
		
		this.nome = nome;
	}
	
	public String getSobreNome() {
		
		return sobreNome;
	}
	
	public void setSobreNome(String sobrenome) {
		
		this.sobreNome = sobrenome;
	}
	
	public String getEndereco() {
		
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		
		this.endereco = endereco;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
		
		this.email = email;
	}
	
	public long getTelCelular() {
		
		return telCelular;
	}
	
	public void setTelCelular(long telCelular) {
		
		this.telCelular = telCelular;
	}
	
	public long getTelResidencial() {
		
		return telResidencial;
	}
	
	public void setTelResidencial(long telResidencial) {
		
		this.telResidencial = telResidencial;
	}
	
	
	public long getCep() {
		
		return cep;
	}
	
	public void setCep(long cep) {
		
		this.cep = cep;
	}
	
	public Date getDataNascimento() {
		
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		
		this.dataNascimento = dataNascimento;
	}
	
	public boolean isAtivo() {
		
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		
		this.ativo = ativo;
	}
}
