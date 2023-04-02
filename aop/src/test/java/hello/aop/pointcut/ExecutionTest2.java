package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import hello.aop.member.MemberServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest2 {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl2.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl2.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void allMatch() {
        //반환타입 *, 선업타입 생략, 메서드이름 *, 파라미터 (..)
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void nameMatchStar1() {
        //패턴매치
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void nameMatchStar2() {
        //패턴매치2
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isFalse();
    }

    @Test
    void packageExactMatch1() {
        //정확하게 매칭
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl2.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void packageExactFalse() {
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1() {
        //member 하위에 있는 모든패키지 대상
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2() {
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void typeExactMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl2.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void typeMatchSuperType() {
        //typeMatch는 부모타입 가능
        pointcut.setExpression("execution(* hello.aop.member.MemberService2.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        //pointcut.setExpression("execution(* hello.aop.member.MemberService2.*(..))"); > isFalse, 부모타입에 선언한 것 만 매칭됨.
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl2.*(..))");
        Method internalMethod = MemberServiceImpl2.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl2.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        pointcut.setExpression("execution(* hello.aop.member.MemberService2.*(..))");

        Method internalMethod = MemberServiceImpl2.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl2.class)).isFalse();//> isFalse, 부모타입에 선언한 것 만 매칭됨.
    }

    //String 타입의 파라미터 허용
    //(String)
    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");//String타입
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    //파라미터가 없어야 함
    //()
    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");//파라미터X 인 것 > False String이 있으므로
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isFalse();
    }

    //정확히 하나의 파라미터 허용, 모든 타입 허용
    //(Xxx)
    @Test
    void argsMatchStar() {
        pointcut.setExpression("execution(* *(*))"); // 모든 타입 허용이나 1개의 파라미터
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    //숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(), (Xxx), (Xxx, Xxx)
    @Test
    void argsMatchAll() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    //String 타입으로 시작, 숫자와 무관하게 모든 파라미터, 모든 타입 허용
    //(String), (String, Xxx), (String, Xxx, Xxx)
    @Test
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }


}
