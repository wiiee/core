package com.platform.util;

import com.platform.config.FeatureConfig;

/**
 * Created by wang.na on 2017/6/29.
 */
public abstract class FeatureConfigUtil {
    private static FeatureConfig _featureConfig;

    public static void setFeatureConfig(FeatureConfig featureConfig){
        _featureConfig = featureConfig;
    }

    public static boolean isActive(String feature){
        return _featureConfig.isActive(feature);
    }
}
