package br.com.senai.projetoindividual.objeto;

public class Imagem {
	
	private int galeria_id;
	private String informacaoImagem;
	private String nome;
	private int id;
	private Galeria nomeGaleria;
	
	public int getGaleria_id() {
		
		return galeria_id;
	}
	
	public void setGaleria_id(int galeria_id) {
		
		this.galeria_id = galeria_id;
	}
	
	public String getInformacaoImagem() {
		
		return informacaoImagem;
	}
	
	public void setInformacaoImagem(String informacaoImagem) {
		
		this.informacaoImagem = informacaoImagem;
	}
	
	public String getNome() {
		
		return nome;
	}
	
	public void setNome(String nomeImagem) {
		
		this.nome = nomeImagem;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}

	public Galeria getNomeGaleria() {
		return nomeGaleria;
	}

	public void setNomeGaleria(Galeria nomeGaleria) {
		this.nomeGaleria = nomeGaleria;
	}

}
