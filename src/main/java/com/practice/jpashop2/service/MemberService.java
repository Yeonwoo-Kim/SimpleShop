package com.practice.jpashop2.service;

import com.practice.jpashop2.domain.Member;
import com.practice.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member)
    {
        validateDuplicateMember(member); //회원 중복검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     */
    public void validateDuplicateMember(Member member)
    {
        String name = member.getName();

        List<Member> memberList = memberRepository.findByName(name);

        if(!memberList.isEmpty())
        {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }

    }

    /**
     * 전체 회원 조회, 단건 조회
     */
    public List<Member> findMembers()
    {
        return memberRepository.findAll();
    }

    public Member findOne(Long id)
    {
        return memberRepository.findOne(id);
    }

}
