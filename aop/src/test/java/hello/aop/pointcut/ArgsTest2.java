package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import hello.aop.member.MemberServiceImpl2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest2 {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl2.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        //hello(String)과 매칭
        assertThat(pointcut("args(String)") .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args(Object)") //Object 상위타입이므로 String도 isTrue
                .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args()") //없으므로 False
                .matches(helloMethod, MemberServiceImpl2.class)).isFalse();
        assertThat(pointcut("args(..)") .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args(*)") .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args(String,..)") .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
    }

    /**
     * execution(* *(java.io.Serializable)): 메서드의 시그니처로 판단 (정적)
     * args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)
     */
    @Test
    void argsVsExecution() {
        //Args : 런타임에 전달된 인수로 판단 (동적), 상위타입을 허용
        assertThat(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl2.class)).isTrue();

        //Execution : 메서드의 시그니처로 판단 (정적), 정확하게매칭
        assertThat(pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl2.class)).isTrue();
        assertThat(pointcut("execution(* *(java.io.Serializable))") //매칭 실패
                .matches(helloMethod, MemberServiceImpl2.class)).isFalse();
        assertThat(pointcut("execution(* *(Object))") //매칭 실패
                .matches(helloMethod, MemberServiceImpl2.class)).isFalse();
    }

}
