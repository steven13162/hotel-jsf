package es.uv.etse.bdweb.hotel.filter;

import java.io.IOException;
import java.util.logging.Logger;

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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "/clihab/*",
														"/reception/*"})
public class AuthorizationFilter implements Filter {

	private static final Logger logger = Logger.getLogger(AuthorizationFilter.class.getName());
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain)
			throws IOException, ServletException {
		try {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession session = req.getSession(false);
			
			String webOrigen = req.getServletPath();	

			if (session != null && session.getAttribute("clienteHabitualSession") != null
					&& webOrigen.startsWith("/clihab/"))
			{
				chain.doFilter(request, response);
				logger.info("\npasa por AuthFilter chain.doFilter\n");
			}
			else if (session != null && session.getAttribute("recepcionistaSession") != null
					&& webOrigen.startsWith("/reception/"))
			{
				chain.doFilter(request, response);
				logger.info("\npasa por AuthFilter chain.doFilter\n");
			}
			else
			{
				resp.sendRedirect(req.getContextPath() + "/");
				logger.info("\npasa por AuthFilter, pero NO PASA por chain.doFilter\n");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}

}
