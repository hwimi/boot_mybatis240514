package com.ezen.www.controller;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
    private final CommentService csv;


    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody CommentVO cvo){
        log.info(">>cvo>>{}",cvo);
        int isOk=csv.post(cvo);
        return isOk>0? "1":"0";

    }
    /*select *from comment order by cno desc limit 0(pageStart) 5(qty)*/

    @GetMapping("/list/{bno}/{page}")
    @ResponseBody
    public PagingHandler list(@PathVariable("bno") long bno,@PathVariable("page") int page){
        log.info(">>bno>>page>>{}"+bno+"/"+page);
        PagingVO pgvo=new PagingVO(page, 5);
        //CommentList
        //totalcount;
        PagingHandler ph=csv.getList(bno,pgvo);
        return ph;
    }
 /*   @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody CommentVO cvo){
        int isOK=csv.modfiy(cvo);
        return isOK>0? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);

    }*/
    @ResponseBody
    @PutMapping("/modify")
    public String modify(@RequestBody CommentVO cvo){
        log.info(">>cvo>>{}",cvo);
        int isOk=csv.modfiy(cvo);
        return isOk>0? "1":"0";
    }

    /*@DeleteMapping("/{cno}")
    public ResponseEntity<String>remove(@PathVariable("cno")int cno){
        log.info(">>>cvo>>{}",cno);
        int isOk=csv.delete(cno);
        return isOk>0? new ResponseEntity<String>("1",HttpStatus.OK):
                new ResponseEntity<String>("0",HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
    @ResponseBody
    @DeleteMapping("/remove/{cno}")
    public String remove(@PathVariable("cno")long cno){
        log.info(">>cvo>>{}",cno);
        int isOk=csv.delete(cno);
        return isOk>0? "1":"0";
    }


}
