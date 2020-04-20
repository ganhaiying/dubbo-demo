package com.ghy.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ghy.tools.ReflectTools;
import com.ghy.tools.RequestClient;
import org.springframework.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用服务拦截器
 */
//@Activate(group = Constants.CONSUMER)
public class CustomConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //首先定义一个返回对象;
        Result result = null;
        //获取接口名称
        String interfaceName = invoker.getUrl().getPath();
        //获取方法名称
        String methodName = invocation.getMethodName();
        //获取参数，我举的例子是一个参数，实际可能有多个参数，多种类型
        Object[] arguments =invocation.getArguments();
        //这里面可能有泛型,所以入参在序列化时要上json
        String paramJson = JSON.toJSONString(arguments, SerializerFeature.WriteClassName);
        //获取所有的入参类型,这块要自己写反射处理
        ReflectTools reflectTools = new ReflectTools();
        List<String> paramTypes = reflectTools.getMethodParameterType(interfaceName, methodName);
        //获取被调用方法返回类型，这块要自己写反射处理
        String methodReturnType = reflectTools.getMethodTypeReturnString(interfaceName, methodName);
        //上面两行可以拿到入参类型和返回值的类型，是因为我们在客户端只用里面有参数类

        //下面定义一个httpclient，用于发送Http请求，首先我们发送一个请求，确认这个接口的这个方法是否在我们测试平台存在了
        Map paramMap = new HashMap();
        paramMap.put("interfaceName", interfaceName);
        paramMap.put("methodName", methodName);
        //发送请求给mock系统，如果有维护该接口的数据，则返回该接口的返回数据
        RequestClient requestClient = new RequestClient();
        String r = requestClient.doGet( "https://www.baidu.com/");

        //如果不存在，调用真实的接口获取返回值
        if(!StringUtils.isEmpty(r)){
            result = new RpcResult(r);
        }else {
            //调用真实接口
            result = invoker.invoke(invocation);
        }
        //然后把接口信息存在我们的测试平台
        return result;
    }
}
