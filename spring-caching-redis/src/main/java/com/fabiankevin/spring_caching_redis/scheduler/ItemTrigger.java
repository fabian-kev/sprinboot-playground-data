package com.fabiankevin.spring_caching_redis.scheduler;

import com.fabiankevin.spring_caching_redis.Item;
import com.fabiankevin.spring_caching_redis.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemTrigger {

    private final ItemService itemService;

    @Scheduled(fixedDelay = 5000L)
    public void itemTrigger() {
        List<Item> items = itemService.getItems();
        log.info("Items are retrieved={}", items);
    }
}
