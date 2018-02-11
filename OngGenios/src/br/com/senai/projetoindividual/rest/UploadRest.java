package br.com.senai.projetoindividual.rest;


import java.io.File;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.com.senai.projetoindividual.rest.util.FileUtil;

@Path("upload")
public class UploadRest {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(	@FormDataParam("file") InputStream inputStream,	@FormDataParam("file") FormDataContentDisposition contentDisposition, @FormDataParam("nome") String nome){			
			
		File file = FileUtil.save(contentDisposition.getFileName(), inputStream);
		return Response.ok(file.getName()).build();
	}
}
