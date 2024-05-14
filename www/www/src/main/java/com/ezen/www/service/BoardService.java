package com.ezen.www.service;

import com.ezen.www.domain.BoardVO;

import java.util.List;

public interface BoardService {

    int register(BoardVO bvo);

    List<BoardVO> list();

    BoardVO detail(long bno);

    void udpate(BoardVO bvo);


    void remove(long bno);
}
