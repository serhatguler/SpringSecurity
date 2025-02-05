package com.tpe.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpe.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailsImpl  implements UserDetails {

    //user i userdetailse cevirdik


    private Long id;
    private String userName;


    @JsonIgnore//Json fornatina donusturmek istersek ve response olarak verirsek password gozukmesin
    private String password;


    //user detailsde roller yok granted authority var
    private Collection<? extends GrantedAuthority> authorities; //roller grantedauth ceviriliyor


    public static UserDetailsImpl build(User user){

       List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getType().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getUserName(), user.getPassword(),authorities);

    }





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
