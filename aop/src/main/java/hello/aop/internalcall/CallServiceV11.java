package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV11 {

    private CallServiceV11 callServiceV11;

    //setter, Autowired로 주입
    @Autowired
    public void setCallServiceV11(CallServiceV11 callServiceV11) {
        this.callServiceV11 = callServiceV11;
    }

    public void external() {
        log.info("call external");
        callServiceV11.internal(); //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
