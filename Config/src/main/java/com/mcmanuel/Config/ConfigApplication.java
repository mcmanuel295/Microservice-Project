package com.mcmanuel.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.client.ConfigClientAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableAutoConfiguration(exclude = {
        ConfigClientAutoConfiguration.class,
        // Also exclude these if needed:
       // org.springframework.cloud.config.client.ConfigServerConfigDataLocationResolver.class
})
public class ConfigApplication {

	public static void main(String[] args) {

        System.setProperty("spring.cloud.bootstrap.enabled", "false");
        System.setProperty("spring.config.use-legacy-processing", "true");

        SpringApplication.run(ConfigApplication.class, args);
	}

}
