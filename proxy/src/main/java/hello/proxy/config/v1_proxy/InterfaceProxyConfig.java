package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.Interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.Interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.Interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    //스프링 컨테이너에 프록시 객체 등록(실제 객체 대신)
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){
        //컨트롤러 구현체는 서비스 proxy를 호출
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl,logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        //서비스 구현체는 서비스 proxy를 호출
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl,logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace){
        OrderRepositoryV1Impl repositroyImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositroyImpl, logTrace);
    }

}
