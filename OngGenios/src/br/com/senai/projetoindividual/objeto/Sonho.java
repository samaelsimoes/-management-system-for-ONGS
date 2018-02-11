package br.com.senai.projetoindividual.objeto;

public class Sonho extends Pessoa{
	
	private int id;
	private Beneficiario beneficiario;
	private String sonho;
	private Membro responsavel;
	private Prioridade prioridade;
	
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}

	public Beneficiario getBeneficiario() {
		
		return beneficiario;
	}
	
	public void setBeneficiario(Beneficiario beneficiario) {
		
		this.beneficiario = beneficiario;
	}
	
	public String getSonho() {
		
		return sonho;
	}
	
	public void setSonho(String sonho) {
		
		this.sonho = sonho;
	}
	
	public Membro getResponsavel() {
		
		return responsavel;
	}
	
	public void setResponsavel(Membro responsavel) {
		
		this.responsavel = responsavel;
	}
	public Prioridade getPrioridade() {
		
		return prioridade;
	}
	public void setPrioridade(Prioridade prioridade) {
		
		this.prioridade = prioridade;
	}
	

	
}
