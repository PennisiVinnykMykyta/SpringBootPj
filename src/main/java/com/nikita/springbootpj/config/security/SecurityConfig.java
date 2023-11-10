package com.nikita.springbootpj.config.security;


public class SecurityConfig  {
    /*
    extends WebSecurityConfigurerAdapter

    private final UserRepository userRepository;

    public static final String[] ADMIN_URL_MATCHER = {
            "/api/**"
    };

    public static final String[] USER_URL_MATCHER = {
            "/api/user/add-or-update", //si devono criptare gli ID

            "/api/booking/add-or-update",
            "/api/booking/delete/{bookId}",
            "/api/booking/list/by-user/{userId}",

            "/api/car/available-cars/{start},{finish}"
    };
/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
            .authorizeRequests().anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
*/

}
