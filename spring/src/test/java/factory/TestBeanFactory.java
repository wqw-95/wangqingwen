package factory;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //bean的定义（class,scope,初始化，销毁）
        AbstractBeanDefinition singleton = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",singleton);
        //给beanFactory添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        //Beanfactory后处理器主要功能
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(item -> {
            item.postProcessBeanFactory(beanFactory);
        });
        // Bean后处理器，争对bean的生命周期各个阶段提供扩展，例如@Autowired
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){return new Bean1();}

        @Bean
        public Bean2 bean2(){return new Bean2();}
    }

    static class Bean1{
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1(){
           log.debug("构造Bean1()");
        }
        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){
            return bean2;
        }
    }

    static class Bean2{
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2(){
            log.debug("构造Bean2()");
        }
    }


}
