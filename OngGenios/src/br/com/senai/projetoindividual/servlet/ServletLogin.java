package br.com.senai.projetoindividual.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.senai.projetoindividual.objeto.Membro;
import br.com.senai.projetoindividual.service.MembroService;

public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID=1L;
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = request.getContextPath();
		Membro retorno = null;
		
		try {
			if(request.getParameter("acao").equals("login")){

				String loginMembro = request.getParameter("txtlogin");
				String senhaMembro = request.getParameter("txtsenha");

				MembroService membro = new MembroService();				
				retorno = membro.login(loginMembro,senhaMembro);
							
				Map<String, String>msg = new HashMap<String, String>();
				HttpSession sessao = request.getSession();	
								
				if(retorno != null){
					
					sessao.setAttribute("login", retorno);
					msg.put("msg", " Login valida, entrando . . .");
				}else{
					
					msg.put("msg", " Login INVALIDO ");
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
				
				String json = new ObjectMapper().writeValueAsString(msg);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if(request.getParameter("acao").equals("logout")) {
				
				HttpSession sessao = request.getSession();
				sessao.invalidate();
				response.sendRedirect(url+"/resources/login.html");
			}

		} catch (Exception e) {			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		process(request, response);
	}
}
