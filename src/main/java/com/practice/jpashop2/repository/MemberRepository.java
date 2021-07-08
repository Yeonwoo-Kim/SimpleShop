package com.practice.jpashop2.repository;

import com.practice.jpashop2.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;


    //회원 등록
    public Long save(Member member)
    {
        em.persist(member);
        return member.getId();
    }

    //단건 조회
    public Member findOne(Long id)
    {
        return em.find(Member.class,id);
    }

    //전체 조회
    public List<Member> findAll()
    {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    //이름으로 회원 조회
    public List<Member> findByName(String name)
    {
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
