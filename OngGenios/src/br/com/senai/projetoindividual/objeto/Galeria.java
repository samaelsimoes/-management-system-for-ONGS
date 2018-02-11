package br.com.senai.projetoindividual.objeto;

public class Galeria {

	
	private int id;
	private Evento id_evento;
	private String informacaoGaleria;
	private String informacaoImagem;
	private String nome;
	private String nomeImagem;

	
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		
		this.id = id;
	}
	public Evento getId_evento() {
		
		return id_evento;
	}
	public void setId_evento(Evento id_evento) {
		
		this.id_evento = id_evento;
	}
	public String getInformacaoGaleria() {
		
		return informacaoGaleria;
	}
	public void setInformacaoGaleria(String informacaoGaleria) {
		
		this.informacaoGaleria = informacaoGaleria;
	}

	public String getInformacaoImagem() {
		
		return informacaoImagem;
	}
	public void setInformacaoImagem(String informacaoImagem) {
		
		this.informacaoImagem = informacaoImagem;
	}
	
	
	public void setNomeImagem(String nomeImagem) {
		
		this.nomeImagem = nomeImagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	public String getNomeImagem() {
		
		return nomeImagem;
	}
}
