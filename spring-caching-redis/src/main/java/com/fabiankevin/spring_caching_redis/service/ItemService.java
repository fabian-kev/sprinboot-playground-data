package com.fabiankevin.spring_caching_redis.service;

import com.fabiankevin.spring_caching_redis.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ItemService {

    @Cacheable(value = "items1")
    public List<Item> getItems() {
        log.info("Retrieving list of items...");
        return List.of(
                new Item(UUID.randomUUID(), "item 1"),
                new Item(UUID.randomUUID(), "item 2"),
                new Item(UUID.randomUUID(), "item 3")
        );
    }
}
