package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by helei on 2018-11-28.
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

    //@HystrixCommand(fallbackMethod = "fallback")
//    @HystrixCommand
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })
    @HystrixCommand(commandKey = "getProductInfoList2",
            commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
//    @HystrixCommand(commandKey = "getProductInfoList2")
    @GetMapping(value = "/getProductInfoList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getProductInfoList(@RequestParam("num") Integer num){
        if(num % 2 == 0){
            return "success";
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8762/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
//        throw new RuntimeException("发生异常");
    }

    private String fallback(){
        return "太拥挤了，请稍后再试~";
    }

    private String defaultFallback(){
        return "默认提示：太拥挤了，请稍后再试";
    }

}
