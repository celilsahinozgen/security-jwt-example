package com.celilSecurity.securityjwtexample.config;


import com.celilSecurity.securityjwtexample.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// OncePerRequestFilter sayesinde hhtp lerdeki request ve responselere cevap vermek için yardımcı olur
// kısaca tokenleri kontrol eder ve gitmesi gereken yada gitmemesi gerekn kontrol saglarız
public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         // gelen request e göre bir aut cekmemiz gerekecek
        final String header = request.getHeader("Authorization");
        // bir token degerimiz olacak onun için bir degisken tanımlıyalım
        final String jwt;
        // gelen tokenede bir user degeri olcak bunlarıda bir filtre yapıp mevcut mu die kotnrol altına alcaz
        final String username;

        // bearer yazmamızın sebebi ise token içerisinde başında out bearer ile başlar String deger oldugu için kontrol ederiz
        if (header == null || header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        // 7. satırdan başlıyorsa da bearer den kaynaklı
        jwt = header.substring(7);
        //anlamsızkelimerin içinden username bulmamız lazım
        username =jwtService.findUsername(jwt);

        // user degiskeni null degilse atadıgımız kullanıcı bilgilerini spring e atıcaz
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()== null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //nesne oluşturup
            if (jwtService.tokenControl(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
