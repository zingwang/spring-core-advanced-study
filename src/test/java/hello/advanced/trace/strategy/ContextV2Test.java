package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {
    @Test
    void strategyV1(){
        ContextV2 context = new ContextV2();
        context.excute(new StrategyLogic1());
        context.excute(new StrategyLogic2());
    }
    @Test
    void strategyV2(){
        //lamda
        ContextV2 context= new ContextV2();
        context.excute(new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직1 실행1 ");
            }
        });
        context.excute(new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직2 실행 2 ");
            }
        });
    }
    @Test
    void strategyV3(){
        //lamda
        ContextV2 context= new ContextV2();
        context.excute(() -> log.info("비즈니스 로직1 실행 1 "));
        context.excute(() -> log.info("비즈니스 로직2 실행 2 "));
    }
}
