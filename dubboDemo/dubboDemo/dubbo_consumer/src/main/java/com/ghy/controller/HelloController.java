package com.ghy.controller;

import com.ghy.param.MyInput;
import com.ghy.rpc.DubboServiceFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("/ghy")
public class HelloController {
    @Resource
    private DubboServiceFactory cubboServiceFactory;

    @RequestMapping("/sayHello")
    public void sayHello(){
        MyInput input = new MyInput();
        input.setName("ghy111");
        cubboServiceFactory.genericInvoke("com.ghy.service.HelloService", "2.5.3","sayHello", "com.ghy.param.MyInput",  input.toString());
    }
}
