package net.pd.ethraa.main;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import net.pd.ethraa.common.EthraaConstants;

@SpringBootApplication
@ComponentScan(value = { "net.pd.ethraa" })
@EnableJpaRepositories(basePackages = { "net.pd.ethraa.dao" })
@EntityScan(basePackages = "net.pd.ethraa.common.model")
// @EnableCaching
@EnableAsync
@EnableTransactionManagement
public class EthraaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
	SpringApplication.run(EthraaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(EthraaApplication.class);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
	Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
	b.indentOutput(true).dateFormat(new SimpleDateFormat(EthraaConstants.DATE_FORMAT));
	return b;
    }

}

@Configuration
@EnableWebMvc
class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**");
    }
}