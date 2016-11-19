package monitoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import monitoring.config.database.DatabaseConfig;
import monitoring.config.jms.JmsConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
@Import({JmsConfig.class,
        DatabaseConfig.class})
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class AppInitializer extends WebMvcConfigurerAdapter implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(AppInitializer.class);
        springContext.scan("monitoring.app", "monitoring.config");

        servletContext.addListener(new ContextLoaderListener(springContext));
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);
        dispatcherServlet.setDetectAllViewResolvers(true);

        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
