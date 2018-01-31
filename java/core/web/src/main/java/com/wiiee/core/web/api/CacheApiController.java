package com.wiiee.core.web.api;

import com.wiiee.core.domain.cache.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by wang.na on 2017/7/19.
 */
@RestController
@RequestMapping("/api/cache")
public class CacheApiController {
    @Autowired
    private CacheHelper cacheHelper;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<String> getCacheNames() {
        return cacheHelper.getCacheNames();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.POST)
    public String getCacheValue(@PathVariable String name, @RequestBody String key) {
        return cacheHelper.getCacheValueWithString(name, key);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public Object getCacheKeys(@PathVariable String name) {
        return cacheHelper.getCacheKeys(name);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public void clearCache(@PathVariable String name) {
        cacheHelper.clearCache(name);
    }

    @RequestMapping(path = "/evict/{name}", method = RequestMethod.POST)
    public void evictCache(@PathVariable String name, @RequestBody String key) {
        cacheHelper.evictCache(name, key);
    }

    @RequestMapping(path = "/update/{name}", method = RequestMethod.POST)
    public void updateCache(@PathVariable String name, @RequestBody Pair<String, String> data) {
        cacheHelper.updateCacheWithString(name, data.getFirst(), data.getSecond());
    }
}
