package com.ghy;

import com.ghy.param.MyInput;
import com.ghy.rpc.DubboServiceFactory;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericServicTest {

    @Test
    public void testSayHello(){
        DubboServiceFactory cubboServiceFactory = DubboServiceFactory.getInstance();
        MyInput input = new MyInput();
        input.setName("ghy");
        Object result =  cubboServiceFactory.genericInvoke("com.ghy.service.HelloService","2.5.3","sayHello","com.ghy.param.MyInput",  input.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void testExcute(){
        DubboServiceFactory cubboServiceFactory = DubboServiceFactory.getInstance();
        MyInput input = new MyInput();
        Map<String , Object> map = new HashMap<>();
        map.put("广州产品部","java开发");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String , Object> mapSkill = new HashMap<>();
        mapSkill.put("开发语言","java");
        mapSkill.put("数据库语言","Mysql");
        list.add(mapSkill);
        input.setId(1);
        input.setName("ghy");
        input.setJob(map);
        input.setSkill(list);
        Object result =  cubboServiceFactory.genericInvoke("com.ghy.service.HelloService","2.5.3", "execute", "com.ghy.param.MyInput",  input.toString());
        Assert.assertNotNull(result);
    }
}
