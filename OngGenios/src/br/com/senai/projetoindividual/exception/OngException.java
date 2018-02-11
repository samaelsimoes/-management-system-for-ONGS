package br.com.senai.projetoindividual.exception;

public class OngException extends Exception{
	
	private static final String MSG = " DEU ALGUM ERRO";
	
	public OngException(String msg) {
		super(msg);
	}
	
	public OngException(String msg, Throwable t) {
		super(msg, t);
	}

	public OngException(Throwable e) {
		super(MSG , e);
	}
}
