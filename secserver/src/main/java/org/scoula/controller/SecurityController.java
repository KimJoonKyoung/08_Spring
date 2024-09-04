package org.scoula.controller;

import lombok.extern.log4j.Log4j;
import org.scoula.security.account.domain.CustomUser;
import org.scoula.security.account.domain.MemberVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Log4j
@RequestMapping("/security")
public class SecurityController {
    @GetMapping("/all") //모두 접근 가능한 메소드
    public void doAll() {
        log.info("do all can access everybody");
    }

    /*@GetMapping("/member") // MEMBER 또는 ADMIN 권한 필요
    public void doMember() {
        log.info("logined member");
    }*/

    /*@GetMapping("/member") // Principal 주입
    public void doMember(Principal principal) {
        log.info("username = " + principal.getName());
    }*/

    @GetMapping("/member") // Authentication 주입
    public void doMember(Authentication authentication) {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        log.info("username = " + userDetails.getUsername());
    }


    /*@GetMapping("/admin") // ADMIN 권한 필요
    public void doAdmin() {
        log.info("admin only");
    }*/

    @GetMapping("/admin") // AuthenticationPrincipal 에노테이션
    public void doAdmin(@AuthenticationPrincipal CustomUser customUser) {
        MemberVO member = customUser.getMember();
        log.info("username = " + member);
    }

    @GetMapping("/login")
    public void login() {
        log.info("login page");
    }

    @GetMapping("/logout")
    public void logout() {
        log.info("logout page");
    }
}
