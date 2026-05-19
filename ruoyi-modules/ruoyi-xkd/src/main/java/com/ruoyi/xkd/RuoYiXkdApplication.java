package com.ruoyi.xkd;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.ruoyi.xkd.mapper")
public class RuoYiXkdApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiXkdApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  XKD024业务模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}