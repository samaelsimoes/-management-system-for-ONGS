package br.com.senai.projetoindividual.objeto;

import java.util.Date;


public class Evento {
	
	private int id;
	private Membro responsavel;
	private String endereco;
	private double latitude;
	private double longetude;
	private String  horario;
	private long contato;
	private Date dataEvento;
	private int id_sonho;
	private String mensagem;
	private Pessoa beneficiario;
	private String nomeEvento;
	private String valorLista;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Membro getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Membro responsavel) {
		this.responsavel = responsavel;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongetude() {
		return longetude;
	}
	public void setLongetude(double longetude) {
		this.longetude = longetude;
	}

	public String  getHorario() {
		return horario;
	}
	public void setHorario(String  horario) {
		this.horario = horario;
	}
	public long getContato() {
		return contato;
	}
	public void setContato(long contato) {
		this.contato = contato;
	}

	public int getId_sonho() {
		return id_sonho;
	}
	public void setId_sonho(int id_sonho) {
		this.id_sonho = id_sonho;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	public String getNomeEvento() {
		return nomeEvento;
	}
	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}
	public Pessoa getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(Pessoa beneficiario) {
		this.beneficiario = beneficiario;
	}
	public String getValorLista() {
		return valorLista;
	}
	public void setValorLista(String valorLista) {
		this.valorLista = valorLista;
	}

}
