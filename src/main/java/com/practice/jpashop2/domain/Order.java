package com.practice.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING) //주문 상태 [ORDER,CANCEL]
    private OderStatus status;

    /**
     * 연관관계 편의 메서드 (양방향)
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);

    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 주문 생성 메서드
     * todo : 주문 회원, 배송 정보, 상품명을 입력하여 주문 생성
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem item : orderItems) {
            order.addOrderItem(item);
        }
        order.setStatus(OderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * 비즈니스 로직
     * todo : 주문 취소 -> 재고 수량 증가
     */
    public void cancel(Order order) {
        //이미 배송완료
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소할 수 없습니다.");
        }
        this.setStatus(OderStatus.CANCEL);

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }

    }

    /**
     * 조회 로직
     * todo : 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();

        }
        return totalPrice;
    }

}
