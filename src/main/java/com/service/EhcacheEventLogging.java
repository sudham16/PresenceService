package com.service;


import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhcacheEventLogging implements CacheEventListener<Object, Object> {
    private static final Logger LOGGER= LoggerFactory.getLogger(EhcacheEventLogging.class);

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        if(cacheEvent.getKey().toString().contains("SubscriptionCacheType")){
            LOGGER.info( "Subscription Id "+cacheEvent.getOldValue()+" is expired and initiating new request");
        }
        if(cacheEvent.getKey().toString().contains("OauthTokenCacheType")){
            LOGGER.info( "OauthToken"+cacheEvent.getOldValue()+" is expired and initiating new request");
        }

    }

}



