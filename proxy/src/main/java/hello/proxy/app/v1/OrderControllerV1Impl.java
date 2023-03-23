package hello.proxy.app.v1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class OrderControllerV1Impl implements OrderControllerV1 {
    private final OrderServiceV1 orderService ;

    public OrderControllerV1Impl(OrderServiceV1 orderServiceV1){
        this.orderService=orderServiceV1;
    }

    @Override
    public String request(@RequestParam("itemId") String itemId){
        orderService.orderItem(itemId);
        return "ok";
    };

    @Override
    public String noLog(){
      return null;
    };
}
