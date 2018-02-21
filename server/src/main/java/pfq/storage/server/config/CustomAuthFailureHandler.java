package pfq.storage.server.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
    	System.out.println("onAuthenticationFailure");
    	//response.set
      //  response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	PrintWriter writer = response.getWriter();
    	writer.println("HTTP Status 401 - " + exception.getMessage());
    	/*
    	Enumeration<String> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object value = request.getAttribute(name);
         //   attributes.put(name, value);
            //System.out.println(name);
           // System.out.println(value);
        }
        */
     //   getRedirectStrategy().sendRedirect(request, response, "/login?error");
        

    }
}
