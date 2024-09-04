package org.scoula.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity // 모든 페이지에서 자동으로 인증을 하도록 설정
@Log4j
@MapperScan(basePackages = {"org.scoula.security.account.mapper"})
@ComponentScan(basePackages = {"org.scoula.security"})
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 경로별 접근 권한 설정
        http.authorizeRequests()
                // '/security/all' 경로는 모든 사용자에게 접근 허용
                .antMatchers("/security/all").permitAll()
                // '/security/admin' 경로는 ADMIN 권한이 있는 사용자만 접근 가능
                .antMatchers("/security/admin").access("hasRole('ROLE_ADMIN')")
                // '/security/member' 경로는 MEMBER 권한이 있는 사용자만 접근 가능
                .antMatchers("/security/member").access("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')");
        
        // form 기반 로그인 활성화, 나머지 설정은 디폴트값 사용
        http.formLogin()
                .loginPage("/security/login")
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/");

        http.logout()
                .logoutUrl("/security/logout")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSION-ID")
                .logoutSuccessUrl("/security/logout");

        // addFilterBefore 메서드를 사용하여 CharacterEncodingFilter를 CsrfFilter 이전에 추가
        // 이 설정은 모든 요청에 대해 UTF-8 인코딩 적용 후에 CSRF 보호가 이루어지도록 함
        http.addFilterBefore(encodingFilter(), CsrfFilter.class);

//        super.configure(http);

    }


    public CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();

        // UTF-8 인코딩을 설정합니다.
        encodingFilter.setEncoding("UTF-8");
        // 강제로 UTF-8 인코딩을 적용하도록 설정
        encodingFilter.setForceEncoding(true);
        // 생성된 필터를 반환
        return encodingFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*log.info("configure ...........................................");

        auth.inMemoryAuthentication()
                .withUser("admin")
//                .password("{noop}1234")
                .password("$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq")
                .roles("ADMIN", "MEMBER")
        ;

        auth.inMemoryAuthentication()
                .withUser("member")
//                .password("{noop}1234")
                .password("$2a$10$Tnp52pT3CxpcPTiWB.0UW.A66BFZZR6ftbOw/kcbpj9yZj8wQuBZq")
                .roles("MEMBER");*/
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
