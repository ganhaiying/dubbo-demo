package com.ghy;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ProviderStartUp
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:springmvc.xml");
        context.start();
        System.out.println("Dubbo provider");
        try {
            System.in.read();   // 按任意键退出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
