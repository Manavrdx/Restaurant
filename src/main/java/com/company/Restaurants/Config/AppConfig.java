package com.company.Restaurants.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AppConfig {
	
	@Bean
    public CommonsMultipartResolver commonsMultipartResolver(){

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();

        resolver.setMaxUploadSizePerFile(-1);

        return resolver;
    
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
