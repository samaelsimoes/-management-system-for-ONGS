package br.com.senai.projetoindividual.rest.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
	
	public static final String UPLOAD_DIR = "C:/Users/samael/Desktop/OngGenios/OngGenios/imagens";
											
	public static File save(String fileName, InputStream inputStream){
		
		String extension = fileName.substring(fileName.lastIndexOf("."));
		String uniqueName = UUID.randomUUID().toString()+extension;
		
		File temp = new File(UPLOAD_DIR,uniqueName);
		Path dest = Paths.get(temp.getAbsolutePath());
		
		try {
			
			Files.copy(inputStream, dest);
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
		
		return temp;
	}
	
	public static File getFile(String fileName){
		
		return new File(UPLOAD_DIR,fileName);
	}
}