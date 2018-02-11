package br.com.senai.projetoindividual.rest;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.projetoindividual.objeto.Membro;

public class UtilRest {
	
	@Context
	private HttpServletRequest request;
	
	public Membro getLoguedUser(){
		
		Membro membro = null;
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null){
			
			membro = (Membro) sessao.getAttribute("login");
		}

		return membro;
	}
	
	/*
	 *M�todo respons�vel por enviar a resposta ao cliente sobre
	 *a transa��o realizada, inclus�o, consulta, edi��o ou exclus�o,
	 *realizadas com sucesso.
	 *
	 * Repare que o m�todo em quest�o aguarda que seja repassado um
	 *conte�do que ser� referenciado por um objeto chamado result.
	 */
	
	public Response buildResponse(Object result){
		
		/*
		*Cria a inst�ncia da Classe StringWriter para o objeto fw.
		*Isto que este objeto � quem estar� referenciando o conte�do
		*repassado como resposta para o lado cliente.
		*/
		
		StringWriter fw = new StringWriter();
		
		try{
			
			/*
			* Cria a inst�ncia da classe ObjectMapper para o objeto
			mapper.
			*/
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
			
			/*
			* Acessa o m�todo writeValue, por meio do objeto mapper,
			* passando como par�metro o objeto fw e o conte�do do
			* objeto result, na realidade est� criando um mapeamento
			* de dados onde o objeto fw � a chave do valor de um
			* conte�do referenciado pelo objeto result.
			* result pode conter a mensagem, "Cadastro efetuado com
			* sucesso", ou "Exclus�o efetuada com sucesso" ou outra
			* qualquer dependendo da transa��o realizada.
			*/
			
			mapper.writeValue(fw, result);
			
			/*
			* Monta o objeto de resposta com status 200 (ok), junto
			* com o objeto result convertido para JSON pelo objeto fw
			* para o cliente no formato String.
			*/
			
			return Response.ok(fw.toString()).build();
		}catch(Exception ex){
			
			return this.buildErrorResponse(ex.getMessage());
		}
	}
	
	public Response buildResponse2(Object result){
		
		/*
		*Cria a inst�ncia da Classe StringWriter para o objeto fw.
		*Isto que este objeto � quem estar� referenciando o conte�do
		*repassado como resposta para o lado cliente.
		*/
		
		StringWriter fw = new StringWriter();
		
		try{
			
			/*
			* Cria a inst�ncia da classe ObjectMapper para o objeto
			mapper.
			*/
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
			
			/*
			* Acessa o m�todo writeValue, por meio do objeto mapper,
			* passando como par�metro o objeto fw e o conte�do do
			* objeto result, na realidade est� criando um mapeamento
			* de dados onde o objeto fw � a chave do valor de um
			* conte�do referenciado pelo objeto result.
			* result pode conter a mensagem, "Cadastro efetuado com
			* sucesso", ou "Exclus�o efetuada com sucesso" ou outra
			* qualquer dependendo da transa��o realizada.
			*/
			
			mapper.writeValue(fw, result);
			
			/*
			* Monta o objeto de resposta com status 200 (ok), junto
			* com o objeto result convertido para JSON pelo objeto fw
			* para o cliente no formato String.
			*/
			
			return Response.ok(fw.toString()).build();
		}catch(Exception ex){
			
			return this.buildErrorResponse(ex.getMessage());
		}
	}

	public Response buildErrorResponse(String str){
		
		/*
		* Abaixo o objeto rb recebe o status do erro.
		*/
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		
		/*
		* Define a entidade (objeto), que nesse caso � uma
		* mensagem que ser� retornado para o cliente.
		*/
		
		rb = rb.entity(str);
		
		/*
		* Define o tipo de retorno desta entidade(objeto), no
		* caso � definido como texto simples.
		*/
		
		rb = rb.type("text/plain");
		
		/*
		* Retorna o objeto de resposta com status 500 (erro),
		* junto com a String contendo a mensagem de erro.
		*/
		
		return rb.build();
	}
}


