package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {
    @Test
    void basicConfig(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorTest.BeanPostProccessorConfig.class);

        //beanA 이름으로 B 객체가 빈으로 등록된다.
        // 빈 후처리기 A객체->B객체 (바꿔치기) : 강력 빈 객체를 프록시로 교체 하는것도 가능
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        //A는 빈으로 등록되지 않는다.
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()->applicationContext.getBean(BeanPostProcessorTest.A.class));
    }

    @Slf4j
    @Configuration
    static class BeanPostProccessorConfig {
        @Bean(name="beanA")
        public BasicTest.A a(){
            return new BasicTest.A();
        }

        @Bean
        public AtoBPostProcessor helloPostProcessor(){
            return new AtoBPostProcessor();
        }
    }
    @Slf4j
    static  class A{
        public void helloA(){
            log.info("hello A");
        }
    }
    @Slf4j
    static  class B{
        public void helloB(){
            log.info("hello B");
        }
    }

    @Slf4j
    static class AtoBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}",beanName, bean);
            if(bean instanceof A){
                return new B();
            }
            return bean;
            //return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
    }
}
