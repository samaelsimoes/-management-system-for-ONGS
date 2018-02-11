package br.com.senai.projetoindividual.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.senai.projetoindividual.objeto.Membro;

/**
 * Servlet Filter implementation class FiltroSessao
 */

@WebFilter(urlPatterns = {"/paginausuario.html","/privado/*"})
public class FiltroSessao implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String context = request.getServletContext().getContextPath();
		
		try {
			
			HttpSession session = ((HttpServletRequest) request).getSession();
			Membro membro= null;
			
			if(session != null){
				membro = (Membro) session.getAttribute("login");
			}
			
			if(membro != null && context.equals("login.html")  ){
				((HttpServletResponse) response).sendRedirect(context+"/privado/paginausuario.html");
			}			
			
			if(membro == null){ 

				session.setAttribute("msg", "Voce não está logado no sistema");
				((HttpServletResponse) response).sendRedirect(context+"/resources/login.html");
			}else{
				chain.doFilter(request, response);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
    /**
     * Default constructor. 
     */

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
