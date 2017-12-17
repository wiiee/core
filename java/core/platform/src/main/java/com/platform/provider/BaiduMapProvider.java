package com.platform.provider;

import com.platform.model.baidu.GeocoderSearchResponse;
import com.platform.model.baidu.GeoconvResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wiiee on 9/23/2017.
 */
@Component
public class BaiduMapProvider {
    private final RestTemplate restTemplate;

    @Autowired
    public BaiduMapProvider(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GeocoderSearchResponse getGeo(double latitude, double longitude)
    {
        String url = String.format("http://api.map.baidu.com/geocoder/v2/?ak=%s&location=%s,%s&output=json&coordtype=wgs84ll", "wavxymeawXnIqXO2HZcc8REWU3Py1A1e", latitude, longitude);
        return restTemplate.getForObject(url, GeocoderSearchResponse.class);
    }

    //from
    //取值为如下：
    //1：GPS设备获取的角度坐标，wgs84坐标;
    //2：GPS获取的米制坐标、sogou地图所用坐标;
    //3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标，国测局坐标;
    //4：3中列表地图坐标对应的米制坐标;
    //5：百度地图采用的经纬度坐标;
    //6：百度地图采用的米制坐标;
    //7：mapbar地图坐标;
    //8：51地图坐标
    //to
    //有两种可供选择：5、6。
    //5：bd09ll(百度经纬度坐标),
    //6：bd09mc(百度米制经纬度坐标);
    public GeoconvResponse geoConvert(double latitude, double longitude)
    {
        int from = 1, to = 5;
        String url = String.format("http://api.map.baidu.com/geoconv/v1/?coords=%s,%s&from=%s&to=%s&ak=%s", latitude, longitude, from, to, "wavxymeawXnIqXO2HZcc8REWU3Py1A1e");
        return restTemplate.getForObject(url, GeoconvResponse.class);
    }
}
