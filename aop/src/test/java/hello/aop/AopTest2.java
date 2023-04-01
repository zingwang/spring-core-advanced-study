package hello.aop;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV11.class) //클라이언트 <-> (로그) <-> Service <-> (로그) <-> Repository
//@Import(AspectV21.class) // (포인트컷분리)클라이언트 <-> (로그) <-> Service <-> (로그) <-> Repository
//@Import(AspectV31.class) // 클라이언트 <-> (로그, 트랜잭션) <-> Service <-> (로그) <-> Repository
//@Import(AspectV41.class) // (포인트컷 참조)클라이언트 <-> (로그, 트랜잭션) <-> Service <-> (로그) <-> Repository

//@Import({AspectV51.LogAspect.class, AspectV51.TxAspect.class }) // (어드바이스 순서:Aspect 단위로 순서가 보장) 클라이언트 <-> 1.트랜잭션 2.로그 <-> Service <-> (로그) <-> Repository
@Import(AspectV61.class) // @Around <-> @Before <-> @After <-> @After Returning <-> @After Throwing
public class AopTest2 {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(()->orderService.orderItem("ex")).isInstanceOf(IllegalStateException.class);
    }

}
