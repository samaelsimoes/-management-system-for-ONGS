package br.com.senai.projetoindividual.objeto;

public class Categoria {
	
	private int id;
	private String tipoCategoria;
	private Membro responsavelCadastro;
	
	public int getId() {		
		return id;
	}
	
	public void setId(int id) {		
		this.id = id;
	}
	
	public String getTipoCategoria(){		
		return tipoCategoria;
	}
	
	public void setTipoCategoria(String tipoCategoria){		
		this.tipoCategoria = tipoCategoria;
	}

	public Membro getResponsavelCadastro() {
		return responsavelCadastro;
	}

	public void setResponsavelCadastro(Membro responsavelCadastro) {
		this.responsavelCadastro = responsavelCadastro;
	}
}
