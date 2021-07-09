package com.practice.jpashop2.service;

import com.practice.jpashop2.domain.*;
import com.practice.jpashop2.repository.ItemRepository;
import com.practice.jpashop2.repository.MemberRepository;
import com.practice.jpashop2.repository.OrderRepository;
import com.practice.jpashop2.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * todo : 주문하기
     * input : 주문한 memberId, 주문한 상품, 상품 갯수
     * 1. 주문한 member가 누군지 조회
     * 2. 배송 정보 생성
     * 3. 주문한 상품생성 (여러개)
     * 4. 주문 생성
     * 5. 주문 저장
     */
    @Transactional
    public Long order(Long memberId, Long ItemId, int count)
    {
        //주문한 사람 찾기
        Member member= memberRepository.findOne(memberId);

        //주문한 상품 찾기
        Item item = itemRepository.findOne(ItemId);

        //배송정보 생성
        Delivery delivery =new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문한 상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성 -멤버,배송정보,주문할 상품
        Order order= Order.createOrder(member, delivery,orderItem);


        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * todo : 주문 취소
     * Input : 취소할 주문 id
     */
    @Transactional
    public void CancelOrder(Long OrderId)
    {
        Order order = orderRepository.findOne(OrderId);
        order.cancel();
    }

    /**
     * todo : 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch)
    {
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
