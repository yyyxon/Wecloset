package config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.ActionControlCenter;

@WebFilter("/*")
public class CrossScriptingFilter implements Filter {
	
	private final Logger logger = LogManager.getLogger(CrossScriptingFilter.class);
	
	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		//logger.debug(" xss 필터  {}  ", "init");
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//logger.debug(" xss 필터  {}  ", "doFilter");
		
		//1.전체 적용시
		//chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
		
		 //2.부분 적용시
		  HttpServletRequest req = (HttpServletRequest) request ;
		   if(includeUrl(req)){	
			   
			   if(!excludeUrl(req)){
				   chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);   
			   }else{
				   chain.doFilter(request, response);   
			   }
			   		   
		  }else{
			  chain.doFilter(request, response);
		  }		
	}

	private boolean includeUrl(HttpServletRequest request) {
		String uri = request.getRequestURI().toString().trim();	
		if (uri.contains("WeclosetServlet")||uri.contains("chatUnread") ) { 			
			return true;
		} else {						
			return false;
		}
	}
	
	private boolean excludeUrl(HttpServletRequest request) {
		String uri = request.getRequestURI().toString().trim();		
		if (uri.startsWith("/admin/")|| uri.contains("commentWrite")) { 
			return false;
		}else{
			return true;
		}
	}

	
	
}
