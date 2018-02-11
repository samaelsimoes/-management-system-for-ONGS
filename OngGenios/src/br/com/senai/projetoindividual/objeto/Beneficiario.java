package br.com.senai.projetoindividual.objeto;

public class Beneficiario extends Pessoa{
	
	private float renda;
	private int tamanhoFamilia;
	private int prioridade;
	private Membro membro;
	
	public void setRenda(float renda){
		this.renda = renda;
	}
	
	public void setTamanhoFamilia(int tamanhoFamilia){
		this.tamanhoFamilia = tamanhoFamilia;
	}
	
	public void setPrioridade(int prioridade){
		this.prioridade = prioridade;
	}
	
	public double getRenda(){
		return renda;
	}
	
	public int getTamanhoFamilhia(){
		return tamanhoFamilia;
	}
	
	public int getPrioridade(){
		return prioridade;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	


	

	
	
}
