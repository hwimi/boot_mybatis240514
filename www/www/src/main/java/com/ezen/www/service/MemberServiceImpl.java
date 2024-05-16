package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;

    @Override
    public int insert(MemberVO mvo) {
        int isOK= memberMapper.insert(mvo);

        return (isOK>0 ? memberMapper.insertAuth(mvo.getEmail()) :0);
    }
}
