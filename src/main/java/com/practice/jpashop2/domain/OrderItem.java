package com.practice.jpashop2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; //주문 당시 가격
    private int count; //주문 수량

    /**
     * 생성 메서드 - 주문 할 아이템, 주문 가격, 주문 수량
     */
    public static OrderItem createOrderItem(Item item,int orderPrice,int count)
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    /**
     * 비스니스 로직
     * todo: 주문 취소시 주문 수량만큼 재고 늘리기
     */
    public void cancel() {
        this.getItem().addStock(count);

    }

    /**
     * todo : 전체 가격
     */
    public int getTotalPrice() {
        return orderPrice*count;
    }
}
