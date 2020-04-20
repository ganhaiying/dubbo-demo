package com.icbc.itsp.generaltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.icbc.itsp.generaltest.mapper")
public class StartGeneralTest {
    public static void main( String[] args ) {
        SpringApplication.run(StartGeneralTest.class, args);
        System.out.println( "StartGeneralTest success!" );
    }
}
