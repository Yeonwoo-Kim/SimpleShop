package com.practice.jpashop2.service;

import com.practice.jpashop2.domain.Item;
import com.practice.jpashop2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    //저장
    @Transactional
    public void saveItem(Item item)
    {
        itemRepository.save(item);
    }

    //전체조회
    public List<Item> findItems()
    {
        return itemRepository.findAll();
    }

    //단건조회
    public Item findOne(Long ItemId)
    {
        return itemRepository.findOne(ItemId);
    }

}
