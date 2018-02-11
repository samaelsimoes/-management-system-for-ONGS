package br.com.senai.projetoindividual.validador;

import java.util.Date;

import br.com.senai.projetoindividual.exception.CampoVaziuException;
import br.com.senai.projetoindividual.objeto.Categoria;
import br.com.senai.projetoindividual.objeto.Doacao;
import br.com.senai.projetoindividual.objeto.Evento;
import br.com.senai.projetoindividual.objeto.Financeiro;
import br.com.senai.projetoindividual.objeto.Galeria;
import br.com.senai.projetoindividual.objeto.Imagem;
import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.objeto.Sonho;

public class OngValidador extends Exception{

	public static void validaAdicionar(Membro membro) throws CampoVaziuException{

		String msg =  validaVaziu("Nome ", membro.getNome());
		msg += validaVaziu("SobreNome ", membro.getSobreNome());
		msg += validaVaziu("Email ", membro.getEmail());
		msg += validaVaziu("Endereco", membro.getEndereco());
		msg += validaVaziuDate("Data Nascimento ", membro.getDataNascimento());
		msg += validaVaziu("Login ", membro.getLogin());
		msg += validaVaziu("Senha ", membro.getSenha());
				
		msg += validaVaziuNum("Telefone Residencial ", membro.getTelCelular());
		msg += validaVaziuNum("Telefone Pessoal ", membro.getTelResidencial());	
		
		if(!msg.equals("")){
						
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}
	
	public static void validaAdicionarDoacao(Doacao doacao) throws CampoVaziuException {

		String msg = validaVaziu("Doacao ", doacao.getDoacao());
	          msg += validaVaziuNum("Categoria ", doacao.getCategoria().getId());
	          msg += validaVaziuNum("Quantidade " , doacao.getQuantidade());
	          msg += validaVaziuNum("Responsavel ", doacao.getResponsavel().getId());
		
		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}

	public static void validaVaziuLogin(Membro membro) throws CampoVaziuException {
			
		String msg = "";
		
		msg += validaVaziu("Login ", membro.getLogin());
		msg += validaVaziu("Senha ", membro.getSenha());
				
		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}			
	}

	public static void validaSonho(Sonho sonho) throws CampoVaziuException {
		String msg = validaVaziu(" Sonho ", sonho.getSonho());
			   msg += validaVaziuNum(" Id Beneficiario ", sonho.getBeneficiario().getId());
			   msg += validaVaziuNum(" Id Responsavel ", sonho.getResponsavel().getId());
		
		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}
	
	public static void validaDeposito(Financeiro financeiro) throws CampoVaziuException {
		
		String msg = validaVaziuDate(" Data e Hora ", financeiro.getDataDeposito());
			   msg += validaVaziuDouble(" Deposito ", financeiro.getDeposito());
	
		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}

	public static void validaFinanceiro(Financeiro financeiro) throws CampoVaziuException {

		String msg = validaVaziuDate(" Data e Hora ", financeiro.getDataSaque());
			   msg += validaVaziuDouble(" Saque ", financeiro.getSaque());
			   msg += validaVaziu(" Motivo", financeiro.getMotivo());

		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}
	
	public static void validaEvento(Evento evento) throws CampoVaziuException {
		
		String msg = validaVaziuNum(" Responsavel ", evento.getResponsavel().getId());
			   msg += validaVaziu(" Endereço ", evento.getEndereco());
			   msg += validaVaziuDoubleFinanceiro(" latitude ", evento.getLatitude());
			   msg += validaVaziuDoubleFinanceiro(" Longetude " , evento.getLongetude());
			   msg += validaVaziuDate(" Data Evento " , evento.getDataEvento());
			   msg += validaVaziu(" Horario ", evento.getHorario());
			   msg += validaVaziuNum(" Contato ", evento.getContato());
			   
				if(!msg.equals("")){
					
					throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
				}	
		}
	

	public static void validaGaleria(Galeria galeria) throws CampoVaziuException {

		String msg = validaVaziuNum("id ", galeria.getId());
			   msg += validaVaziu("Nome ", galeria.getNome());
			   msg += validaVaziu("Informacao Galeria " ,  galeria.getInformacaoGaleria());			   		
		
		if(!msg.equals("")){
			
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}	
	}
	
	public static void validaGaleriaImagens(Imagem imagem) throws CampoVaziuException {
		
		String msg = validaVaziuNum(" Id ", imagem.getGaleria_id());
			   msg += validaVaziu(" Informacao Imagem ", imagem.getInformacaoImagem());
			   msg += validaVaziu(" Nome Imagem ", imagem.getNome());
		
		   if(!msg.equals("")){
				
				throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
			}	
	}
	
	public static void validaCategoria(Categoria categoria) throws CampoVaziuException {

		String msg = validaVaziu("Tipo Categoria" , categoria.getTipoCategoria());
		
		if(!msg.equals("")){
			
			throw new CampoVaziuException("<br> Um dos argumentos do cadastro está Invalido" + msg + "<br>");
		}
	}
	
	// ============================================================================================================================================
   // ======================================================================================================================================================
	
	public static String validaVaziu(String campo, String valor){
		
		String msg = "";
				
		if(valor == null || valor.trim().isEmpty()){

			msg += "</br> O campo: " + campo + " Está Vazio ! . </br>";
		}
		return msg;
	}
	private static String validaVaziuDoubleFinanceiro (String campo,Double valor){
		
		String msg = "";
		
		if(valor == null){

			msg += "</br> O campo: " + campo + " Está Vazio ! . </br>";
		}
		return msg;
	}
	private static String validaVaziuDouble (String campo, 	Float valor){
		
		String msg = "";
		
		if(valor == null){

			msg += "</br> O campo: " + campo + " Está Vazio ! . </br>";
		}
		return msg;
	}
	
	private static String validaVaziuNum(String campo, long valor) {
		
		String msg = "";
		
		String tel = Long.toString(valor); // convertendo para String para validar.
		
		if(tel == null || tel.trim().isEmpty()){

			msg += "</br> O campo: " + campo + " Está Vazio !. </br>";
		}
		return msg;
	}
	
	private static String validaVaziuDate(String campo, Date date){

		String msg = "";
		
		if(date == null || date.equals("")){
			
			msg += "</br> O campo: " + campo + " Está Vazio !. </br>";

		}
		return msg;
	}
}
