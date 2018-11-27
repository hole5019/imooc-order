package com.imooc.order.controller;

import com.imooc.order.converter.OrderForm2OrderDtoConverter;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.form.OrderForm;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.ResultVoUtil;
import com.imooc.order.vo.ResultVo;
import com.imooc.order.dto.OrderDto;
import com.imooc.order.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by helei on 2018-11-15.
 */
@RestController
@RequestMapping("/order")
@Slf4j
@RefreshScope
public class OrderController {

    @Value("${env}")
    private String env;

    @Autowired
    private OrderService orderService;

    @GetMapping("/print")
    public String print(){
        return env;
    }

    /**
     * 1.参数检验
     * 2.查询商品信息（调用商品服务）
     * 3.计算总价
     * 4.扣库存（调用商品服务）
     * 5.订单入库
     */
    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，oerderForm={}",orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //order -> orderDTO
        OrderDto orderDto = OrderForm2OrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDto result = orderService.create(orderDto);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVoUtil.success(map);

    }

    /**
     * 完结订单
     * @param orderid
     * @return
     */
    @PostMapping(value = "/finish",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<OrderDto> finish(@RequestParam("orderid") String orderid){
        return ResultVoUtil.success(orderService.finish(orderid));
    }
}
