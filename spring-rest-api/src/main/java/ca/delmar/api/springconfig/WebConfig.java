package ca.delmar.api.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="ca.delmar.api")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

    @Bean
    public LobHandler lobHandler() {
        LobHandler lobHandler = new DefaultLobHandler();
        return lobHandler;
    }

    @Bean
    public DataSource oracleLookup() {
        try {
            InitialContext initialContext = new InitialContext();
            return (DataSource) initialContext.lookup("OracleDS");
        } catch (NamingException e) {
            throw new RuntimeException("JNDI lookup failed.",e);
        }
    }

}
