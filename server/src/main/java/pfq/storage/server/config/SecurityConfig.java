package pfq.storage.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
    private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		        .antMatchers("/css/**", "/js/**", "/vendor/**", "/img/**").permitAll()
		        .antMatchers("/admin/**").hasAuthority("ADMIN")
		        .antMatchers("/").hasAuthority("USER")
				.anyRequest().fullyAuthenticated()
				.and().formLogin().loginPage("/login").failureUrl("/login?error")
				.usernameParameter("login").permitAll()
				.and().logout().logoutUrl("/logout").deleteCookies("remember-me")
				.logoutSuccessUrl("/login").permitAll()
				.and().rememberMe();
	}
	
	 @Override
	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .userDetailsService(userDetailsService)
	            .passwordEncoder(new BCryptPasswordEncoder());
	    }


}
