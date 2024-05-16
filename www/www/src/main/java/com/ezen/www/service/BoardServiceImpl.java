package com.ezen.www.service;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardMapper;
import com.ezen.www.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Transactional
    @Override
    public int register(BoardDTO bdto) {
        int isOK = boardMapper.insert(bdto.getBvo());
        if (isOK > 0 && bdto.getFlist().size() > 0) {
            long bno = boardMapper.getBno();
            for (FileVO fvo : bdto.getFlist()) {
                fvo.setBno(bno);
                isOK *= fileMapper.insertFile(fvo);
            }
        }
        return  isOK;
    }


    @Override
    public List<BoardVO> list(PagingVO pgvo) {
        return boardMapper.list(pgvo);
    }

    @Override
    public BoardDTO detail(long bno) {
        BoardDTO bdto=new BoardDTO(
                boardMapper.getDetail(bno),
                fileMapper.getFileList(bno));


        return bdto;

    }

    @Override
    public void udpate(BoardVO bvo) {
        boardMapper.update(bvo);
    }

    @Override
    public void remove(long bno) {
        boardMapper.remove(bno);
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return boardMapper.getTotalCount(pgvo);
    }


}
