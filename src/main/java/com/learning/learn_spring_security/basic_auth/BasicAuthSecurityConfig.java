package com.learning.learn_spring_security.basic_auth;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.learning.learn_spring_security.enums.UserRoles;

@Configuration
public class BasicAuthSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());

		http.csrf().disable();

		http.headers().frameOptions().sameOrigin();
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:3000");
			}
		};
	}

//	@Bean
//	UserDetailsService userDetailsService() {
//		UserDetails user = User.withUsername("chandra").password("{noop}gvn").roles(String.valueOf(UserRoles.USER))
//				.build();
//		UserDetails admin = User.withUsername("admin").password("{noop}gvn").roles(String.valueOf(UserRoles.ADMIN))
//				.build();
//		return new InMemoryUserDetailsManager(user, admin);
//	}

	@Bean
	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION).build();
	}

	@Bean
	UserDetailsService userDetailsService(DataSource dataSource) {
		UserDetails user = User.withUsername("chandra").password("{noop}gvn").roles(String.valueOf(UserRoles.USER))
				.build();
		UserDetails admin = User.withUsername("admin").password("{noop}gvn")
				.roles(String.valueOf(UserRoles.ADMIN), String.valueOf(UserRoles.USER)).build();

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		return jdbcUserDetailsManager;
	}

}
