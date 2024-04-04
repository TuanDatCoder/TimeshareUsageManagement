package com.FTimeshare.UsageManagement.security;


import com.FTimeshare.UsageManagement.security.jwt.AuthTokenFilter;
import com.FTimeshare.UsageManagement.security.jwt.JwtAuthEntryPoint;
import com.FTimeshare.UsageManagement.security.user.TimeshareUserDetailsService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig{
    private final TimeshareUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    @Bean
    public AuthTokenFilter authenticationTokenFilter(){
        return new AuthTokenFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers("/api/users").permitAll()
//                        .requestMatchers("/api/users/sendOTP/*").permitAll()
//                        .requestMatchers("/api/users/verify/active/*").permitAll()
//                        .requestMatchers("/api/users/deleteByEmail/*").permitAll()
//                        .requestMatchers("/api/products/staff/active").permitAll()
//                        .requestMatchers("/api/feedback/average-feedback-rating/*").permitAll()
//                        .requestMatchers("/api/news/view").permitAll()
////                        .requestMatchers("/api/pictures/customerview").permitAll()
//                        .requestMatchers("/api/products/viewById/*").permitAll()
//                        .requestMatchers("/api/pictures/viewPicture/*").permitAll()
//                        .requestMatchers("/api/news/viewDetail/*").permitAll()
//                        .requestMatchers("/api/news/imgView/*").permitAll()
//                        .requestMatchers("/api/project/customer/viewproject").permitAll()
//                        .requestMatchers("/api/products/filter").permitAll()
//                        .requestMatchers("/api/productType/customer/viewproductType").permitAll()
//                        .requestMatchers("/api/feedback/viewByProductId/*").permitAll()
//                        .requestMatchers("/api/products/add").hasRole("CUSTOMER")
//                        .requestMatchers("/api/pictures/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/users/viewDetail/*").permitAll()
//                        .requestMatchers("/api/users/edit/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/customer/waitToRespond-Active-Done-In_progress/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/staff/change_status_to_in_progress/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/staff/change_status_to_done/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/customer/waitToByAccId/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/by-account/done/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/by-account/cancel/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/cancel/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/products/view/bookedDate/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/payment/payment/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/payment/*").hasRole("CUSTOMER")
//                        .requestMatchers("/api/bookings/customer/createbooking").hasRole("CUSTOMER")
//                        .requestMatchers("/api/reports/customer/submitreport").hasRole("CUSTOMER")
//                        .requestMatchers("/api/feedback/customer/submitfeedback").hasRole("CUSTOMER")
//                        .requestMatchers("/api/products/staff/totalActive").hasRole("STAFF")
//                        .requestMatchers("/api/users/staff/count/ROLE_CUSTOMER").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/totalPending").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/totalRejected").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/totalClosed").hasRole("STAFF")
//                        .requestMatchers("/api/bookings/staff/totalActive").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/reject/*").hasRole("STAFF")
//                        .requestMatchers("/api/news/staff/totalNews").hasRole("STAFF")
//                        .requestMatchers("/api/users/staffview").hasRole("STAFF")
//                        .requestMatchers("/api/products/viewById/${productID}").hasRole("STAFF")
//                        .requestMatchers("/api/reports/viewByProductId/*").hasRole("STAFF")
//                        .requestMatchers("/api/users/ROLE_CUSTOMER").permitAll()
//                        .requestMatchers("/api/users/staff/active/*").permitAll()
//                        .requestMatchers("/api/users/staff/block/*").permitAll()
//                        .requestMatchers("/api/products/staff/pending").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/rejected").hasRole("STAFF")
//                        .requestMatchers("/api/products/staff/closed").hasRole("STAFF")
//                        .requestMatchers("/api/users/ROLE_STAFF").permitAll()
//                        .requestMatchers("/api/bookings/staff/active/*").hasRole("STAFF")
//                        .requestMatchers("/api/bookings/staff/waitToConfirm").hasRole("STAFF")
//                        .requestMatchers("/api/bookings/staff/waitToConfirmRC").hasRole("STAFF")
//                        .requestMatchers("/api/bookings/view-booking-by-Id/*").hasRole("STAFF")
//                        .requestMatchers("/api/news/**").hasRole("STAFF")
//                        .requestMatchers("/api/bookings/admin/total_Price_For_Done_Bookings").hasRole("ADMIN")
//                        .requestMatchers("/api/bookings/admin/monthlyTotalPrice/*").hasRole("ADMIN")
//                        .requestMatchers("/api/bookings/admin/yearlyTotalPrice/*").hasRole("ADMIN")
//                        .requestMatchers(("/api/users")).hasRole("ADMIN")
                        .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }







}