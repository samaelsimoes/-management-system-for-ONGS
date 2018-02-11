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
 * Servlet Filter implementation class FiltroLogin
 */
@WebFilter(urlPatterns = {"/login.html"})
public class FiltroLogin implements Filter {
	 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	    	
	    	String context = request.getServletContext().getContextPath();
	    	
	    	try {
	    		
				HttpSession session = ((HttpServletRequest) request).getSession();
				Membro membro = null;

				if(session != null){
					membro = (Membro) session.getAttribute("login");
				}
				if(membro != null){	
					((HttpServletResponse) response).sendRedirect(context+"/privado/paginausuario.html");
				}
				
			} catch (Exception e){
				e.printStackTrace();
			}
	    	
			chain.doFilter(request, response);
		}
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
