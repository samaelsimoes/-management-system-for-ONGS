package br.com.senai.projetoindividual.criptografia;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	
	public static String criptografar(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
				
		String crip = "";
		
	    MessageDigest m = MessageDigest.getInstance("SHA-256");
	    m.update(pwd.getBytes("UTF-8"),0,pwd.length());
	    crip = new BigInteger(1,m.digest()).toString(64);
	    
	    return crip;
	}	
}
