//package com.fabiankevin.spring_caching_redis;
//
//import com.fabiankevin.spring_caching_redis.config.CacheConfig;
//import jakarta.annotation.PostConstruct;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.redis.connection.RedisServer;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.io.IOException;
//
//@Import({ CacheConfig.class })
//@ExtendWith(SpringExtension.class)
//@ImportAutoConfiguration(classes = { CacheAutoConfiguration.class, RedisAutoConfiguration.class })
//@EnableCaching
//class ItemServiceCachingIntegrationTest {
//
//    private static final String AN_ID = "id-1";
//    private static final String A_DESCRIPTION = "an item";
//
////    @MockBean
////    private ItemRepository mockItemRepository;
////
////    @Autowired
////    private ItemService itemService;
//
//    @Autowired
//    private CacheManager cacheManager;
//
//    @Test
//    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
//        Item anItem = new Item(AN_ID, A_DESCRIPTION);
//        given(mockItemRepository.findById(AN_ID))
//                .willReturn(Optional.of(anItem));
//
//        Item itemCacheMiss = itemService.getItemForId(AN_ID);
//        Item itemCacheHit = itemService.getItemForId(AN_ID);
//
//        assertThat(itemCacheMiss).isEqualTo(anItem);
//        assertThat(itemCacheHit).isEqualTo(anItem);
//
//        verify(mockItemRepository, times(1)).findById(AN_ID);
//        assertThat(itemFromCache()).isEqualTo(anItem);
//    }
//
//    private Object itemFromCache() {
//        return cacheManager.getCache("itemCache").get(AN_ID).get();
//    }
//
//    @TestConfiguration
//    static class EmbeddedRedisConfiguration {
//
//        private final RedisServer redisServer;
//
//        public EmbeddedRedisConfiguration() throws IOException {
//            this.redisServer = new RedisServer();
//        }
//
//        @PostConstruct
//        public void startRedis() throws IOException {
//            redisServer.start();
//        }
//
//        @PreDestroy
//        public void stopRedis() throws IOException {
//            this.redisServer.stop();
//        }
//    }
//
//}