package com.ghy.service;

import com.alibaba.dubbo.rpc.Result;
import com.ghy.param.MyInput;

public interface HelloService {
    Result sayHello(MyInput input);
    Result execute(MyInput input);
}
