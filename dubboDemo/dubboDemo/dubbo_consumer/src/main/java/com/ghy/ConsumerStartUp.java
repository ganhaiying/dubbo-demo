package com.ghy;

import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ConsumerStartUp {
    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:springmvc.xml" });
        context.start();
        GenericService demoService = (GenericService ) context.getBean("helloService");

        try {

            for (int i = 0; i < 10; i++) {
                //GenericService的$invoke的方法,该方法有三个参数，第一个参数是你调用远程接口的具体方法名，第二个参数是helloService这个方法的入参的类型，最后一个参数是值。
                Object result = demoService.$invoke("sayHello", new String[] { "java.lang.String" }, new Object[] { "ghy" + i });
                System.out.println(result);
            }
            // 为保证服务一直开着，利用输入流的阻塞来模拟
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
