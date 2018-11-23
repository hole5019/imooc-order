package com.imooc.order.client;

import com.imooc.order.dto.CartDTO;
import com.imooc.order.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by helei on 2018-11-16.
 */
@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/msg")
    String productMsg();

    @PostMapping("/product/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    /**
     * 扣库存
     * @param cartDTOList
     */
    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList);

}
