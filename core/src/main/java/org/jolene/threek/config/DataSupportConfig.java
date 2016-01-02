package org.jolene.threek.config;

import org.luffy.lib.libspring.data.ClassicsRepositoryFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Method;

/**
 * 数据支持配置，运行时也应当载入该配置
 * <p>
 * ok 现在加入一套自身实现的实体监听
 *
 * @author CJ
 * @see TransactionAspectSupport#invokeWithinTransaction(Method, Class, TransactionAspectSupport.InvocationCallback)
 */
@Configuration
//@Import(ListenerConfig.class)
/**
 * 这里的复杂度可以写一篇很长的文章
 * http://stackoverflow.com/questions/15616710/spring-transactional-with-aspectj-is-totally-ignored
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html#aop-aj-ltw-spring
 * 1 是事务监管的启用方式，是通过代理还是通过Aspect(AOP)
 *      代理: 优点 无需配置立马启用
 *            缺点 无法应用在非public方法，意味着所有的事务方法都必须是public
 *      Aspect: 优点 几乎无所不能
 *              缺点 需要特定的启动jvm参数 或者采用编译器植入 但又会增加项目的维护成本
 * 2 代理的类型 只有在选择了代理模式以后启用
 *     false 使用Java自带的Proxy 缺点很明显 无法代理类！！以为着几乎所有bean都需要接口引用。
 *     true  使用CGLIB 性能优秀
 * PROXY false 接口声明即可使用事务
 * 可以通过断点在TransactionAspectSupport的invokeWithinTransaction
 */
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@EnableAspectJAutoProxy
//@EnableLoadTimeWeaving
@EnableJpaRepositories(basePackages = {"org.jolene.threek.repository"}, repositoryFactoryBeanClass = ClassicsRepositoryFactoryBean.class)
@EnableScheduling
//TODO http://docs.spring.io/spring-data/jpa/docs/1.8.2.RELEASE/reference/html/
public class DataSupportConfig implements TransactionManagementConfigurer {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private EntityManagerFactory entityManagerFactory;

//    @Autowired
//    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }

//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(){
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Autowired
//    RedisTemplate<?, ?> redisTemplate(@SuppressWarnings("SpringJavaAutowiringInspection") JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        return redisTemplate;
//    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
