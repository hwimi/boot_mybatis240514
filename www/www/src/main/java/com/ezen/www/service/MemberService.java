package com.ezen.www.service;

import com.ezen.www.domain.MemberVO;

import java.util.List;

public interface MemberService {
    int insert(MemberVO mvo);

    List<MemberVO> list();

    int update(MemberVO mvo);
}
