package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner0.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner0.class.getName() + ".car0S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car0.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner1.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car1.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner2.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner2.class.getName() + ".ownedCar2S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner2.class.getName() + ".drivedCar2S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car2.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner3.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner3.class.getName() + ".car3S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car3.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car3.class.getName() + ".owner3S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner4.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car4.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner5.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Owner5.class.getName() + ".car5S", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car5.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Car5.class.getName() + ".owner5S", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
