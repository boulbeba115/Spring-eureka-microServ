package tn.iit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import tn.iit.utils.FileUploadProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({ FileUploadProperties.class })
public class ImpressionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImpressionServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
