package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by helei on 2018-11-28.
 */
@RestController
public class HystrixController {

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping(value = "/getProductInfoList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8762/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
//        throw new RuntimeException("发生异常");
    }

    private String fallback(){
        return "太拥挤了，请稍后再试~";
    }

}
