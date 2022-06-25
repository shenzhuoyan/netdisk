package com.netdisk.baiguo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.netdisk.baiguo.dao")
public class BaiguoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaiguoApplication.class, args);
	}

}
