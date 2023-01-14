package com.celilSecurity.securityjwtexample.model;


import com.celilSecurity.securityjwtexample.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor

    //userdetail de eger name yerine mail olarak verirsek onları başka implementlerde cıkardı
    // username ve password oluşturmamış olsaydık onlarında impl isterdi !!!
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;


    String username;

    String lastName;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;



    //tek rol oldugu için direk verebiliriz ama birden çok rol oldugunda List içinde döndüre biliriz..
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //kullanıcı hesabının sürersi dolup dolmadıgı hakkaında bir metot
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //kullanıcı kitlimi degilmi die kontrol edilir
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //kullanıcı aktifmi pasifmi die kontrol altına alabilirz
    @Override
    public boolean isEnabled() {
        return true;
    }
}
