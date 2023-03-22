package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    @Test
    void strategyV0(){
        logic1();
        logic2();

    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime= endTime - startTime;
        log.info("resultTime={}",resultTime);
    }
    private void logic2(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime= endTime - startTime;
        log.info("resultTime={}",resultTime);
    }

    @Test
    void strategyV1(){
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1= new ContextV1(strategyLogic1);
        context1.excute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.excute();
    }
    @Test
    void strategyV2(){
       Strategy strategyLogic1 = new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직1 실행 1");
            }
        };

        ContextV1 context1= new ContextV1(strategyLogic1);
        context1.excute();

        Strategy strategyLogic2 = new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직2 실행 2 ");
            }
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.excute();
    }

    @Test
    void strategyV3(){
        // 변수선언 생략
        ContextV1 context1= new ContextV1( new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직1 실행 1");
            }
        });
        context1.excute();

        ContextV1 context2 = new ContextV1(new Strategy(){
            @Override
            public void call(){
                log.info("비즈니스 로직2 실행 2 ");
            }
        });
        context2.excute();
    }

    @Test
    void strategyV4(){
        //lamda
        ContextV1 context1= new ContextV1(() -> log.info("비즈니스 로직 lamda-실행 1"));
        context1.excute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직 lamda-실행 2"));
        context2.excute();
    }


}
