package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.MemberService2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true  CGLIB
 * spring.aop.proxy-target-class=false JDK 동적 프록시
 */
@Slf4j
@Import(ThisTargetTest2.ThisTargetAspect.class)
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") //JDK 동적 프록시
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") //CGLIB
public class ThisTargetTest2 {

    @Autowired
    MemberService2 memberService2;

    @Test
    void success() {
        log.info("memberService2 Proxy={}", memberService2.getClass());
        memberService2.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        //부모 타입 허용
        @Around("this(hello.aop.member.MemberService2)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //부모 타입 허용
        @Around("target(hello.aop.member.MemberService2)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl2)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl2ㅅ쿄ㅔ)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
