package com.bkap.vnpConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.bkap.service.CustomAccountDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfigH {
	@Autowired
	CustomAccountDetailService accountDetailService;
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(accountDetailService)  
                .passwordEncoder(bCryptPasswordEncoder())
                .and()
                .build();
    }
    private final CustomAccountDetailService customAccountDetailService ;

    public SecurityConfigH(CustomAccountDetailService customAccountDetailService) {
        this.customAccountDetailService = customAccountDetailService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customAccountDetailService;
    }

   @Bean
   SecurityFilterChain configure(HttpSecurity http) throws Exception {
       http.csrf(csrf-> csrf.disable()).
                authorizeHttpRequests((auth) -> auth.
       		    requestMatchers("/*").permitAll().
        		requestMatchers("/admin/**").hasRole("ADMIN").
        		requestMatchers("/home/**").hasRole("USER").
        		anyRequest().authenticated()).
                formLogin(login-> login
                		.loginPage("/login").
                loginProcessingUrl("/login").
        		usernameParameter("username").
        		passwordParameter("password").
        		successForwardUrl("/login/submit").permitAll()) 
               .logout(logout -> logout
            		   .logoutUrl("/logout")  // Cấu hình đường dẫn logout chính xác
            		   .logoutSuccessUrl("/login")
            		    .invalidateHttpSession(true)  // Đảm bảo session được xóa khi logout
            		    .clearAuthentication(true)
               .permitAll());	
        return http.build();
        		
    }
   
   @Bean
   WebSecurityCustomizer webSecurityCustomizer() {
	   return (web)-> web.ignoring().requestMatchers("/static/**","/fe/**","/log/**","/imgupp/**", "/acsset/**", "/css/**", "/js/**", "/images/**");
   }
   

  
}
