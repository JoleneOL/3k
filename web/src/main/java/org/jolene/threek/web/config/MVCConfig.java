package org.jolene.threek.web.config;

import org.jolene.threek.service.AppService;
import org.jolene.threek.web.dialect.ThreekDialect;
import org.luffy.libs.libseext.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Jolene
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.jolene.threek.web.controller"
})
public class MVCConfig extends WebMvcConfigurerAdapter {

    public static String[] STATIC_RESOURCE_PATHS = new String[]{
            "css", "fonts", "holder.js", "images", "js", "_resources"
    };

    @Autowired
    private Environment environment;
    @Autowired
    private AppService appService;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("Duplicates")
    public String[] staticResourcePathPatterns() {
        String[] ignoring;
        int startIndex = 0;
        if (environment.acceptsProfiles("development")) {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATHS.length + 2];
            ignoring[startIndex++] = "/**/*.html";
            ignoring[startIndex++] = "/mock/**";
        } else {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATHS.length];
        }
        for (String path : MVCConfig.STATIC_RESOURCE_PATHS) {
            ignoring[startIndex++] = "/" + path + "/**";
        }
        return ignoring;
    }

    @SuppressWarnings("Duplicates")
    public String[] staticResourceAntPatterns() {
        String[] ignoring;
        int startIndex = 0;
        if (environment.acceptsProfiles("development")) {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATHS.length + 2];
            ignoring[startIndex++] = "/**/*.html";
            ignoring[startIndex++] = "/mock/**/*";
        } else {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATHS.length];
        }
        for (String path : MVCConfig.STATIC_RESOURCE_PATHS) {
            ignoring[startIndex++] = "/" + path + "/**/*";
        }
        return ignoring;
    }

    /**
     * for upload
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        for (String path : STATIC_RESOURCE_PATHS) {
            registry.addResourceHandler("/" + path + "/**").addResourceLocations("/" + path + "/");
        }
    }

    // 拦截所有操作 如果当前配置未完成


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        // 开启JPA 当任何一个访问开始
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor).excludePathPatterns(staticResourcePathPatterns());
        // TODO 允许配置的URI

        registry.addWebRequestInterceptor(appService).excludePathPatterns(ArrayUtils.mixedArray(staticResourceAntPatterns(),new String[]{
                "/config"
        }));
    }

    // thymeleaf
    @Bean
    public ThreekDialect threekDialect() {
        return new ThreekDialect();
    }

    @Autowired
    private ThreekDialect threekDialect;

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addDialect(new SpringSecurityDialect());
        engine.addDialect(new Java8TimeDialect());
        engine.addDialect(threekDialect);
//        engine.addDialect(webDialect);
        ServletContextTemplateResolver rootTemplateResolver = new ServletContextTemplateResolver();
        rootTemplateResolver.setOrder(1);
        rootTemplateResolver.setPrefix("/");
        rootTemplateResolver.setSuffix(".html");
        rootTemplateResolver.setCharacterEncoding("UTF-8");

        // start cache
        if (environment.acceptsProfiles("development") || environment.acceptsProfiles("test")) {
            rootTemplateResolver.setCacheable(false);
        }

        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setOrder(10);
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        if (environment.acceptsProfiles("development") || environment.acceptsProfiles("test")) {
            System.out.println("Development Mode");
            classLoaderTemplateResolver.setCacheable(false);
        }

        engine.setTemplateResolvers(new HashSet<>(Arrays.asList(rootTemplateResolver, classLoaderTemplateResolver)));

        resolver.setTemplateEngine(engine);
        resolver.setOrder(1);
//        resolver.setViewNames(new String[]{"*.html"});
        resolver.setCharacterEncoding("UTF-8");

        //在初始化Thymeleaf的时候 应该增加它的方言,spring添加方言
//        engine.addDialect(new SpringSecurityDialect());
//        engine.addDialect(new org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect());
//      resolver.setPrefix("/WEB-INF/views/");
//      resolver.setSuffix(".jsp");
        return resolver;
    }

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
//        viewResolver = viewResolver();
        registry.viewResolver(thymeleafViewResolver);
    }
}
