package com.FTimeshare.UsageManagement.config;

import com.FTimeshare.UsageManagement.entities.AccountEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serial;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class  CustomUserDetails implements UserDetails {

    private int accID;
    private String accName;
    private String accPhone;
    private String accEmail;
    private String accPassword;
    private String accImg;
    private String accStatus;
    private Date accBirthday;
    private int roleID;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    @Serial
    private static final long serialVersionUID = 1113799434508676095L;
    public static CustomUserDetails fromAccountEntityToCustomUserDetails(AccountEntity account) {
        CustomUserDetails c = new CustomUserDetails();
        c.accID = account.getAccID();
        c.accName = account.getAccName();
        c.accPhone = account.getAccPhone();
        c.accImg = account.getAccImg();
        c.accStatus = account.getAccStatus();
        c.accBirthday = account.getAccBirthday();
        c.accEmail = account.getAccEmail();
        c.accPassword = account.getAccPassword();
        c.roleID = account.getRoleID().getRoleID();
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(account.getRoleID().getRoleName()));
        return c;
    }    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return accPassword;
    }

    @Override
    public String getUsername() {
        return accEmail;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails account = (CustomUserDetails) o;
        return Objects.equals(accID, account.accID);
    }
}
