package hello.proxy.config.v1_proxy.Interface_proxy;

import hello.proxy.app.v1.OrderRepositroyV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositroyV1 {

    private final OrderRepositroyV1 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId){
        TraceStatus status=null;
        try{
            status= logTrace.begin("OrderRepositoryV1-proxy.requset()");
            //target 호출
            target.save(itemId);
            logTrace.end(status);
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
