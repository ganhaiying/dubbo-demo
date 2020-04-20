package com.ghy.tools;

import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具
 */
@Component
public class ReflectTools {
    /**
     * 根据接口名和方法名获取方法参数类型
     * @param interfaceName
     * @param methodName
     * @return
     */
    public List<String> getMethodParameterType(String interfaceName, String methodName) {
        List<String> list = new ArrayList<>();
        try{
            Class clazz = Class.forName(interfaceName);
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
                if(method.getName().equals(methodName)){
                    Class[] parameterTypes = method.getParameterTypes();
                    for (Class c : parameterTypes){
                        list.add(c.toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("找不到方法！");
        }
        return list;
    }

    /**
     * 根据接口名和方法名获取方法返回值类型
     * @param interfaceName
     * @param methodName
     * @return
     */
    public String getMethodTypeReturnString(String interfaceName, String methodName) {
        try{
            Class clazz = Class.forName(interfaceName);
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
                if(method.getName().equals(methodName)){
                    Class returnType = method.getReturnType();
                    return returnType.toString();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("找不到方法！");
        }
        return "";
    }
}
