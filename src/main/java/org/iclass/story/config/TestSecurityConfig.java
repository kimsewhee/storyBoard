package org.iclass.story.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @EnableWebSecurity - 스프링부트 버전 업데이트에 따라 사용 방식 바뀌어 필요없음.
@Configuration
public class TestSecurityConfig {       

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      //  WebSecurityConfigurerAdapter 상속에서 Component 방식으로 바뀜
        return http
                .authorizeHttpRequests(auth-> auth.anyRequest().permitAll())
                .formLogin().and()
                .build();
    }
}
