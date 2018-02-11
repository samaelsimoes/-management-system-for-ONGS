package br.com.senai.projetoindividual.objeto;

public class Doacao {
	
	private int id;
	private String doacao;
	private Membro responsavel;
	private Categoria categoria;
	private int quantidade;
	
	public int getQuantidade() {
		
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		
		this.quantidade = quantidade;
	}
	
	public Categoria getCategoria() {
		
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		
		this.categoria = categoria;
	}
	
	public Membro getResponsavel() {
		
		return responsavel;
	}
	
	public void setResponsavel(Membro responsavel) {
		
		this.responsavel = responsavel;
	}
	
	public String getDoacao() {
		
		return doacao;
	}
	
	public void setDoacao(String doacao) {
		
		this.doacao = doacao;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
}
