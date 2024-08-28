package com.thinkhumble.StockApi.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for enabling and setting up caching in the application.
 */
@Configuration
@EnableCaching
public class CacheConfig {
    /**
     * Configures a cache manager with in-memory caching.
     *
     * @return CacheManager using a ConcurrentMapCacheManager
     */
    @Bean
    public CacheManager cacheManager() {
        // This uses Spring's default in-memory cache implementation
        return new ConcurrentMapCacheManager("stockQuotes");
    }
}
