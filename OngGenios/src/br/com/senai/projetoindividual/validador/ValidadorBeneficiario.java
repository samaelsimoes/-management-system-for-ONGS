package br.com.senai.projetoindividual.validador;

import java.util.Date;

import br.com.senai.projetoindividual.exception.CampoVaziuException;
import br.com.senai.projetoindividual.objeto.Beneficiario;
import br.com.senai.projetoindividual.objeto.Membro;

public class ValidadorBeneficiario {
	public static void validaAdicionar(Beneficiario beneficiario) throws CampoVaziuException{

		String msg =  validaVaziu("Nome ", beneficiario.getNome());
		msg += validaVaziu("SobreNome ", beneficiario.getSobreNome());
		msg += validaVaziu("Email ", beneficiario.getEmail());
		msg += validaVaziu("Endereco", beneficiario.getEndereco());
		msg += validaVaziuDate("Data Nascimento ", beneficiario.getDataNascimento());				
		msg += validaVaziuTel("Telefone Residencial ", beneficiario.getTelCelular());
		msg += validaVaziuTel("Telefone Pessoal ", beneficiario.getTelResidencial());	
		
		if(!msg.equals("")){
						
			throw new CampoVaziuException(" <br> <br> Um dos argumentos para o cadastramento Inválido"+ "<br>" + msg + "<br>" );
		}
	}
	
	public static String validaVaziu(String campo, String valor){
		
		String msg = "";
				
		if(valor == null || valor.trim().isEmpty()){

			msg += "</br> O campo: " + campo + " Está Vazio ?. </br>";
		}
		
		return msg;
	}
	
	private static String validaVaziuDate(String campo, Date date){

		String msg = "";
		
		if(date == null || date.equals("")){
			
			msg += "</br> O campo: " + campo + " Está Vazio ?. </br>";

		}
		return msg;
	}
	
	private static String validaVaziuTel(String campo, long valor) {
		
		String msg = "";
		
		String tel = Long.toString(valor); // convertendo para String para validar.
		
		if(tel == null || tel.trim().isEmpty()){

			msg += "</br> O campo: " + campo + " Está Vazio ?. </br>";
		}
		
		return msg;
	}
	
	/*Date date = new Date();
	
	String s = financeiro.getDataDeposito();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    try
    {
        date = simpleDateFormat.parse(s);

        System.out.println("date : "+simpleDateFormat.format(date));
    }
    catch (ParseException ex)
    {
        System.out.println("Exception "+ex);
    }*/
}
