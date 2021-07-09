package com.practice.jpashop2.service;

import com.practice.jpashop2.domain.*;
import com.practice.jpashop2.exception.NotEnoughStockException;
import com.practice.jpashop2.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    /**
     * todo: 상품 주문하기 성공여부
     * member,Item,Count
     */
    @Test
    public void 상품_주문하기() throws Exception{
        //given
        //멤버 생성
        Member member=createMember();
        //상품 생성
        Item item=createItem();
        // 주문한 상품 갯수
        int count=2;

        //when
        // 주문생성
        Long OrderId = orderService.order(member.getId(), item.getId(), count);

        //then
        Order order=orderRepository.findOne(OrderId);
        /**
         * 주문 상태 :ORDER
         * 주문한 상품 종류의 수 :1
         * 주문 가격 = 주문수량*주문 가격
         * 주문 수량만큼 재고 감소
         */
        assertEquals("주문 상태는 Status", OrderStatus.ORDER,order.getStatus());
        assertEquals("주문 상품 종류의 수는 1개",1,order.getOrderItems().size());
        assertEquals("주문 가격은 주문수량*주문가격",2*10000,order.getTotalPrice());
        assertEquals("주문 수량만큼 재고 감소",98,item.getStockQuantity());

    }
    @Test
    public void 주문취소() throws Exception{
        //given
        //멤버 생성
        Member member=createMember();
        //상품 생성
        Item item=createItem();
        // 주문한 상품 갯수
        int count=2;
        // 주문생성
        Long OrderId = orderService.order(member.getId(), item.getId(), count);

        //when
        orderService.CancelOrder(OrderId);

        //then
        /**
         * 주문 취소시 상태는 CANCEL
         * 주문 취소시 재고 수량 증가
         */
        Order order =orderRepository.findOne(OrderId);
        assertEquals("주문 취소시 상태는 CANCEL",OrderStatus.CANCEL,order.getStatus());
        assertEquals("주문 취소시 취소된 상품의 재고는 증가",100,item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given

        //멤버 생성
        Member member=createMember();

        //상품 생성
        Item item=createItem();

        // 초과로 상품 주문
        int count=101;

        //when
        // 주문생성
        Long OrderId = orderService.order(member.getId(), item.getId(), count);

        //then
        fail("재고 수량 예외가 발생해야한다!!!");


    }

    private Item createItem() {
        Item item= new Item();
        item.setName("책이름");
        item.setStockQuantity(100);
        item.setPrice(10000);
        em.persist(item);
        return item;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("city","street","zipcode"));
        em.persist(member);
        return member;
    }

    /**
     * todo : 상품 주문시 재고 수량 초과
     */

    /**
     * todo : 주문 취소 성공여부
     */

}