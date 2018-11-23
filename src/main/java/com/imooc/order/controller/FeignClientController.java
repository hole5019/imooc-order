package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import com.imooc.order.dto.CartDTO;
import com.imooc.order.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by helei on 2018-11-16.
 */
@RestController
@Slf4j
public class FeignClientController {

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsgFeign")
    public String getProductMsg(){
        String response = productClient.productMsg();

        log.info("response={}",response);
        return response;
    }

    @GetMapping(value = "/getProductList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ProductInfo> getProductList(){
        List<ProductInfo> response = productClient.listForOrder(Arrays.asList("157875196366160022","157875227953464068"));
        log.info("response={}",response);
        return response;
    }

    @GetMapping(value = "/productDecreaseStock",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String productDecreaseStock(){
        productClient.decreaseStock(Arrays.asList(new CartDTO("157875196366160022",2)));
        return "ok";
    }
}
