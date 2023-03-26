package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();// 이부분만 변경
        log.info("result={}",result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB();// 이부분만 변경
        log.info("result={}",result2);
        //공통 로직2 종료
    }
    @Test
    void reflection1() throws Exception{
        // 클래스정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드정보
        Method methodCallA= classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);

        log.info("result1={}",result1);

        // callB 메서드정보
        Method methodCallB= classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}",result2);

    }

    @Test
    void reflection2() throws Exception{

        // 리플렉션은 컴파일 시점에 오류
        // 클래스정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA
        Method methodCallA= classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB
        Method methodCallB= classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    private void dynamicCall(Method method, Object target) throws Exception{
        //공통 로직 시작
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}",result);
        //공통 로직 종료
    }
    @Slf4j
    static class Hello {
        public String callA(){
            log.info("callA");
            return "A";
        }
        public String callB(){
            log.info("callB");
            return "B";
        }
    }
}
