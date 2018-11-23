package com.imooc.order.controller;

import com.imooc.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by helei on 2018-11-20.
 */
@RestController
@RequestMapping("/env")
//@RefreshScope
public class EnvController {

    @Autowired
    private GirlConfig girlConfig;

    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String print(){
        return env;
    }

    @GetMapping("/printGirl")
    public String printGirl(){
        return "name:" + girlConfig.getName() +
                " --- age:" + girlConfig.getAge();
    }

}
