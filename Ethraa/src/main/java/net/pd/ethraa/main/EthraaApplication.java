package net.pd.ethraa.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(value = { "net.pd.ethraa" })
@EnableJpaRepositories(basePackages = { "net.pd.ethraa.dao" })
@EntityScan(basePackages = "net.pd.ethraa.common.model")
@EnableCaching
public class EthraaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
	SpringApplication.run(EthraaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(EthraaApplication.class);
    }

}
