package br.com.senai.projetoindividual.criptografia;

import java.util.Arrays;
import org.apache.tomcat.util.codec.binary.Base64;

public class Decode64 {
	public String decode64(String senha){
				
	  byte[] byteArray = Base64.decodeBase64(senha.getBytes());
		
	  Arrays.toString(senha.getBytes());
	  String decodedString = new String(byteArray);
	  
	  return decodedString;
	}
}
