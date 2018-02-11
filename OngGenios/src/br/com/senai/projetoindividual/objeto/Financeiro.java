package br.com.senai.projetoindividual.objeto;

import java.sql.Time;
import java.util.Date;

public class Financeiro {
	
	private int id;
	private Membro id_responsavel;
	private Float saque;
	private Float deposito;
	private Date dataDeposito;
	private Date dataSaque;
	private String motivo;
	private int id_evento;
	private Time horario;
	private Double listaTotalsaque;
	private Double listaTotaldeposito;

	private String motivoEditacao;
	private String categoria;
	
	public int getId_evento() {
		
		return id_evento;
	}
	
	public void setId_evento(int id_evento) {
		
		this.id_evento = id_evento;
	}
	
	public String getMotivo() {
		
		return motivo;
	}
	
	public void setMotivo(String motivo) {
		
		this.motivo = motivo;
	}
	
	public Date getDataSaque() {
		
		return dataSaque;
	}
	
	public void setDataSaque(Date dataSaque) {
		
		this.dataSaque = dataSaque;
	}
	
	public Date getDataDeposito() {
		
		return dataDeposito;
	}
	
	public void setDataDeposito(Date dataDeposito) {
		
		this.dataDeposito = dataDeposito;
	}
	
	public Float getDeposito() {
		
		return deposito;
	}
	
	public void setDeposito(Float deposito) {
		
		this.deposito = deposito;
	}
	
	public Float getSaque() {
		
		return saque;
	}
	
	public void setSaque(Float saque) {
		
		this.saque = saque;
	}
	public Membro getId_responsavel() {
		
		return id_responsavel;
	}
	
	public void setId_responsavel(Membro id_responsavel) {
		
		this.id_responsavel = id_responsavel;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}

	public Time getHorario() {
		
		return horario;
	}

	public void setHorario(Time horario) {
		
		this.horario = horario;
	}



	

	public String getMotivoEditacao() {
		return motivoEditacao;
	}

	public void setMotivoEditacao(String motivoEditacao) {
		this.motivoEditacao = motivoEditacao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categorias) {
		this.categoria = categorias;
	}

	public Double getListaTotalsaque() {
		return listaTotalsaque;
	}

	public void setListaTotalsaque(Double listaTotalsaque) {
		this.listaTotalsaque = listaTotalsaque;
	}

	public Double getListaTotaldeposito() {
		return listaTotaldeposito;
	}

	public void setListaTotaldeposito(Double listaTotaldeposito) {
		this.listaTotaldeposito = listaTotaldeposito;
	}
}
