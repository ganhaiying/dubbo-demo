package com.ghy.rpc;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.google.gson.Gson;
import java.util.Map;

public class DubboServiceFactory {
    private ApplicationConfig application;
    private RegistryConfig registry;
    private String version;

    private static class SingletonHolder {
        private static DubboServiceFactory INSTANCE = new DubboServiceFactory();
    }

    public static DubboServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 构造方法
     */
    private DubboServiceFactory() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo_consumer");
        applicationConfig.setVersion("1.0.0");
        // 这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
        RegistryConfig registryConfig = new RegistryConfig();
        // <host:port> 注册中心服务器地址
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        // 这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件
        this.application = applicationConfig;
        this.registry = registryConfig;
    }

    /**
     * 简单参数的泛化调用
     * @param interfaceClass
     * @param methodName
     * @param parameterType
     * @param param
     * @return
     */
  /*  public Object genericInvoke(String interfaceClass, String methodName, String dubboGroup, String dubboVersion, String parameterType, Object param) {
        try{
            ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
            reference.setApplication(application);
            // 服务注册分组，跨组的服务不会相互影响，也无法相互调用，适用于环境隔离。
            registry.setGroup(dubboGroup);
            //设置dubbo版本
            registry.setVersion(dubboVersion);
            reference.setRegistry(registry);
            reference.setVersion(version);
            reference.setInterface(interfaceClass); // 接口名
            reference.setGeneric(true); // 声明为泛化接口
            // ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
            // 需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
            // API方式编程时，容易忽略此问题。
            // 这里使用dubbo内置的简单缓存工具类进行缓存
            ReferenceConfigCache cache = ReferenceConfigCache.getCache();
            GenericService genericService = cache.get(reference);
            if (genericService == null) {
                cache.destroy(reference);
                throw new IllegalStateException("服务不可用！");
            }

            if (parameterType == null) {
                return genericService.$invoke(methodName, new String[] {}, new Object[] {});
            }
            return genericService.$invoke(methodName, new String[] { parameterType }, new Object[] { param });
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("服务调用发生异常！");
        }
    }*/

    /**
     * 复杂参数的泛化调用
     * @param interfaceName
     * @param dubboGroup
     * @param method
     * @param className
     * @param param
     * @return
     */
    public Object genericInvoke(String interfaceName, String dubboGroup, String method, String className, String param) {
        try{
            ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
            reference.setApplication(application);
            reference.setRegistry(registry);
            reference.setVersion(version);
            reference.setInterface(interfaceName);
//            reference.setGroup(dubboGroup);

            // 声明为泛化接口
            reference.setGeneric(true);

            ReferenceConfigCache cache = ReferenceConfigCache.getCache();
            GenericService genericService = cache.get(reference);
            if (genericService == null) {
                cache.destroy(reference);
                throw new IllegalStateException("服务不可用！");
            }

            //基本类型以及Date,List,Map等不需要转换，直接调用
            String[] parameterTypes = new String[]{className};

            Gson gson = new Gson();
            Map params = gson.fromJson(param, Map.class);
            params.put("class", className);

            Object result = genericService.$invoke(method, parameterTypes,new Object[]{params});
            System.out.println(result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException("服务调用发生异常！");
        }
    }
}
