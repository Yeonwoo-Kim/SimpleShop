package com.practice.jpashop2.domain;

import com.practice.jpashop2.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * todo : 비즈니스 로직
     * 1. 재고 수량 추가
     * 2. 재고 수량 감소 (0보다 작을 시, 오류 발생)
     */

    public void addStock(int quantity)
    {
        this.stockQuantity+=quantity;
    }

    public void removeStock(int quantity)
    {
        int restStock=this.stockQuantity-quantity;
        if(restStock<0)
        {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=quantity;
    }

}
