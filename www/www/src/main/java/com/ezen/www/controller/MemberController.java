package com.ezen.www.controller;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/member/*")
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService msv;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public void join(){

    }
    @PostMapping("/register")
    public String register(MemberVO mvo){
        log.info(">>mvo>>{}",mvo);
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        int isOk=msv.insert(mvo);
        return "/index";
    }



    @GetMapping("/login")
    public  void login(){

    }


}
