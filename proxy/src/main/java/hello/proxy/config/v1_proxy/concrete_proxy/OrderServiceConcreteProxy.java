package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);//proxy역할만 쓸 것이기에 (클래스기반프록시의 단점)
        this.target = target;
        this.logTrace = logTrace;
    }


    @Override
    public void orderItem(String itemId){
        TraceStatus status=null;
        try{
            status= logTrace.begin("OrderServiceV2-proxy.request()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}
