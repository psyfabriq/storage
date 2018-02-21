package pfq.storage.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private CustomAuthFailureHandler customAuthenticationFailureHandler;
/*	
    @Bean
    public ExUsernamePasswordAuthenticationFilter exUsernamePasswordAuthenticationFilter()
            throws Exception {
        ExUsernamePasswordAuthenticationFilter exUsernamePasswordAuthenticationFilter = new ExUsernamePasswordAuthenticationFilter();
        exUsernamePasswordAuthenticationFilter
                .setAuthenticationManager(authenticationManagerBean());
        return exUsernamePasswordAuthenticationFilter;
    }
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		        .antMatchers("/css/**", "/js/**", "/vendor/**", "/img/**", "/template/**", "/file/**", "/403").permitAll()
		        .antMatchers("/admin/**").permitAll()
		        .antMatchers("/").hasAuthority("USER")
				.anyRequest().fullyAuthenticated()
				
				.and()
				.formLogin()
				    .loginPage("/login")
				    .failureHandler(customAuthenticationFailureHandler)
				    .usernameParameter("login").permitAll()
				 .and()
				.logout().logoutUrl("/logout").deleteCookies("remember-me")
				.logoutSuccessUrl("/login").permitAll(false)
				.and().rememberMe();
		 http.httpBasic();
		 http.csrf().disable();
		// http.addFilterBefore(exUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}	 
		
	 @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .userDetailsService(userDetailsService)
	            .passwordEncoder(new BCryptPasswordEncoder());
	       
	    }


}
