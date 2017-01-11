package net.pd.ethraa.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(value = { "net.pd.ethraa" })
@EnableJpaRepositories(basePackages = { "net.pd.ethraa.dao" })
@EntityScan(basePackages = "net.pd.ethraa.common.model")
// @EnableCaching
@EnableTransactionManagement
public class EthraaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
	SpringApplication.run(EthraaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(EthraaApplication.class);
    }

    // @Bean
    // public Jackson2ObjectMapperBuilder jacksonBuilder() {
    // Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
    // b.indentOutput(true).dateFormat(new
    // SimpleDateFormat("yyyy-MM-dd")).defaultViewInclusion(true).;
    // return b;
    // }

}

@Configuration
@EnableWebMvc
class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/**");
    }
}