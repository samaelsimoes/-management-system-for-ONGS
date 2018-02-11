package br.com.senai.projetoindividual.objeto;

public class Prioridade extends Sonho{
	
	private int id;
	private String nomePrioridade;
	private Membro responsavelCadastro;
	
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}

	public String getNomePrioridade() {
		
		return nomePrioridade;
	}
	public void setNomePrioridade(String nomePrioridade) {
		
		this.nomePrioridade = nomePrioridade;
	}
	public Membro getResponsavelCadastro() {
		return responsavelCadastro;
	}
	public void setResponsavelCadastro(Membro responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}
}
