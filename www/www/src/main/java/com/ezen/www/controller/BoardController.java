package com.ezen.www.controller;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bsv;
    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String register(BoardVO bvo) {
        log.info(">>bvo>>{}",bvo);
        int isOk=bsv.register(bvo);
        return "index";
    }

    @GetMapping("/list")
    public String list(BoardVO bvo,Model m){
        List<BoardVO> list=bsv.list();
        m.addAttribute("list",list);
        return "/board/list";
    }
    @GetMapping("/detail")
    public void detail(Model m, @RequestParam("bno")long bno){
        BoardVO bvo=bsv.detail(bno);
        m.addAttribute("bvo",bvo);
    }
    @PostMapping("/modify")
    public String modify(BoardVO bvo){
        bsv.udpate(bvo);
        return "redirect:/board/list";
    }
    @PostMapping("/remove")
    public String remove(@RequestParam("bno")long bno){
        bsv.remove(bno);
        return "redirect:/board/list";
    }


}
