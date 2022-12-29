package com.example.server.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance(){
        return Hazelcast.newHazelcastInstance(new Config().setInstanceName("cache"));
    }

    @Bean
    public IMap<Long, Long> balanceCache(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance){
        return hazelcastInstance.getMap("balanceCache");
    }
}
