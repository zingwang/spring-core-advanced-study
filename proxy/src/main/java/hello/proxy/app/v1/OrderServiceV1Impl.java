package hello.proxy.app.v1;

public class OrderServiceV1Impl implements OrderServiceV1{

    private final OrderRepositoryV1 orderRepositroy;

    public OrderServiceV1Impl(OrderRepositoryV1 orderRepositroyV1){
        this.orderRepositroy=orderRepositroyV1;
    }

    @Override
    public void orderItem (String itemID){
        orderRepositroy.save(itemID);

    }
}
