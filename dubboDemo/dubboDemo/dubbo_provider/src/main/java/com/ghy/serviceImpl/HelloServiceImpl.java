package com.ghy.serviceImpl;

import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;
import com.ghy.param.MyInput;
import com.ghy.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public Result sayHello(MyInput input) {
        Result result = new RpcResult("Hello World!" + input.getName());
        return result;
    }

    @Override
    public Result execute(MyInput input){
        Result result = new RpcResult("test excute method!");
        return result;
    }
}
