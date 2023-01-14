package com.celilSecurity.securityjwtexample.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
  //tüm http ler için bir yetkilendirme
                .authorizeHttpRequests()
                .requestMatchers("/login/**")
                .permitAll()
  // diger tüm isteklerin yetkilendirmeye tabi olacak şekilde belirtiyoruz
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
    // burdaki polisiye sesion managment ifadesi ile bir id ile eşleştirmeye gerek kalmıyor . token içerisindeki excp deki zamanına bakcaz demektir
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
    // kullanıcı kimlik dogrulama işlemini bu provider ile yap anlamına geliyor
                .authenticationProvider(authenticationProvider)
     // nasıl bir filtreleme kullanıcamızı belirtiyoruz
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
