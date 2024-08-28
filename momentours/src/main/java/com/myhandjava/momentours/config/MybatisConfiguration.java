package com.myhandjava.momentours.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "com.myhandjava.momentours", annotationClass = Mapper.class)
public class MybatisConfiguration {
}