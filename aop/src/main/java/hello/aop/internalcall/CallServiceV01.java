package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV01 {


    //Asepct
    public void external() {
        log.info("call external");
        internal(); // this.internal() 호출, 내부 호출의 경우 프록시를 거치지 않음(Aspect 적용X, Advice 적용X)
    }

    //Asepct
    public void internal() {
        log.info("call internal");
    }
}
