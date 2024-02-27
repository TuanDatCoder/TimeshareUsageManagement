package com.FTimeshare.UsageManagement.security.user;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeshareUserDetails implements UserDetails {
    private int id;
    private  String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static TimeshareUserDetails buildUserDetails(AccountEntity account){
        TimeshareUserDetails c = new TimeshareUserDetails();
        c.id = account.getAccID();
        c.email = account.getAccEmail();
        c.password = account.getAccPassword();
        c.authorities = Collections.singletonList(new SimpleGrantedAuthority(account.getRoleID().getRoleName()));
        return c;
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
        return email;
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