package miljana.andric.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN")
//	                .and()
//	                .withUser("user").password("user").roles("USER");
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(corsConfigurationSource());
		http.csrf().disable().authorizeRequests().antMatchers("/user/login/**").permitAll()
//				.antMatchers("/user/**").hasRole("ADMIN")
//				.antMatchers("/user/register/**").permitAll().antMatchers("/students/**").hasRole("ADMIN")
//				.antMatchers("/professors/**").hasRole("ADMIN").antMatchers("/examination_period/**").hasRole("ADMIN")
//				.antMatchers("/exams/**").hasRole("ADMIN").antMatchers("/subjects/**").hasRole("ADMIN")
//				.antMatchers("/exam_application/**").hasRole("STUDENT").antMatchers("/mark/**").hasRole("PROFESSOR")

				.and().httpBasic();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
