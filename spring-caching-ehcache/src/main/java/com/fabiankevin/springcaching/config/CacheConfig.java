package com.fabiankevin.springcaching.config;

import com.fabiankevin.springcaching.models.User;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.util.UUID;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfiguration<UUID, User> cacheConfiguration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(UUID.class, User.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10, MemoryUnit.KB)
                                .offheap(1, MemoryUnit.MB))
                .build();

        javax.cache.configuration.Configuration<UUID, User> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration);

        cacheManager.createCache("users", configuration);

        return cacheManager;
    }
}