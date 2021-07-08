package com.practice.jpashop2.repository;

import com.practice.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order)
    {
        em.persist(order);
    }

    //단건 조회
    public Order findOne(Long id)
    {
        return em.find(Order.class,id);
    }

    /**
     * todo: 주문 검색 기능 (동적 쿼리)
     */


}
