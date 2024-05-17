package com.ezen.www.controller;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
@GetMapping("/list")
    public String list(MemberVO mvo, Model m){
        List<MemberVO> list=msv.list();
        m.addAttribute("list",list);
        return"/member/list";
}
    @GetMapping("/modify")
    public void modify(){

    }
    @PostMapping("/modify")
    public String modify(MemberVO mvo,Model m){
        if(mvo.getPwd()!=null){
            mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        }
       if(mvo.getNickName()!=null){
            mvo.setNickName(mvo.getNickName());
       }
        int isOK=msv.update(mvo);

        return "redirect:/member/logout";

    }


}
