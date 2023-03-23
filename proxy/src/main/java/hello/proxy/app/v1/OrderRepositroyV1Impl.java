package hello.proxy.app.v1;

import static java.lang.Thread.sleep;

public class OrderRepositroyV1Impl implements OrderRepositroyV1{

    @Override
    public void save(String itemId){
        if(itemId.equals("ex")){
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int millis){
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
