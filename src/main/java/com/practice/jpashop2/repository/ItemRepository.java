package com.practice.jpashop2.repository;

import com.practice.jpashop2.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //저장
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }
    //하나만 찾기
    public Item findOne(Long id)
    {
        return em.find(Item.class,id);
    }

    //전체 목록 찾기
    public List<Item> findAll()
    {
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
