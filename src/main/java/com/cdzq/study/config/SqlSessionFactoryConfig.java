package com.cdzq.study.config;

import com.github.abel533.sql.SqlMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

@Configuration
public class SqlSessionFactoryConfig {
    @Resource
    SqlSessionTemplate sqlSession;

    @Bean
    @Primary
    public SqlMapper sqlMapper() throws Exception {
        SqlMapper sqlMapper = new SqlMapper(sqlSession);
        return sqlMapper;
    }
}
