package com.wiiee.core.web.api;

import com.wiiee.core.platform.config.FeatureConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feature")
public class FeatureApiController {
    @Autowired
    private FeatureConfig featureConfig;

    @RequestMapping(method = RequestMethod.GET)
    public List<FeatureConfig.Item> getItems() {
        return featureConfig.getSortedItems();
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public boolean isActive(@PathVariable String name) {
        return featureConfig.isActive(name);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.POST)
    public void change(@PathVariable String name) {
        featureConfig.changeResult(name);
    }
}