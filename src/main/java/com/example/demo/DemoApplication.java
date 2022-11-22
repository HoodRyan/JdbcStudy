package com.example.demo;

import com.example.demo.config.JdbcConfig;
import com.example.demo.config.JdbcConfigV2;
import com.example.demo.config.SpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(SpringConfig.class)
//@Import(JdbcConfig.class)
@Import(JdbcConfigV2.class)
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
