package com.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.wallet.repository")
public class WalletDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletDemoApplication.class, args);
    }

}
