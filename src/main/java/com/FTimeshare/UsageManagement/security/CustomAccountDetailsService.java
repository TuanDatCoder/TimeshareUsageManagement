//package com.FTimeshare.UsageManagement.security;
//
//import com.FTimeshare.UsageManagement.entities.AccountEntity;
//import com.FTimeshare.UsageManagement.repositories.AccountRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomAccountDetailsService implements UserDetailsService{
//    private AccountRepository accountRepository;
//
//    public void CustomUserDetailsService(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String accnameOrEmail) throws UsernameNotFoundException {
//        AccountEntity account = accountRepository.findByAccNameOrAccEmail(accnameOrEmail, accnameOrEmail)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("User not found with username or email: "+ accnameOrEmail));
//
//         GrantedAuthority authoritie = new SimpleGrantedAuthority(account.getRoleID().getRoleName());
//         Set<GrantedAuthority> authorities = new HashSet<>();
//         authorities.add(authoritie);
//
//        return new org.springframework.security.core.userdetails.User(account.getAccEmail(),
//                account.getAccPassword(),
//                authorities);
//    }
//}
