package fooddeliveryfive;

import fooddeliveryfive.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MypageViewHandler {


    @Autowired
    private MypageRepository mypageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            System.out.println("##### listener whenOrdered_then_CREATE_1 KafkaProcessor " + ordered.isMe() );
            if (ordered.isMe()) {
                
                // view 객체 생성
                Mypage mypage  = new Mypage();
                // view 객체에 이벤트의 Value 를 set 함                
                mypage.setOrderId(ordered.getId());   
                mypage.setMenuId(ordered.getMenuId());  
                mypage.setStatus(ordered.getStatus());
                mypage.setDeliveryId(ordered.getDeliveryId());
                mypage.setQty(ordered.getQty());  
                // view 레파지 토리에 save
                mypageRepository.save(mypage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCancelled_then_UPDATE_1(@Payload OrderCancelled orderCancelled) {
        try {
            System.out.println("##### listener whenOrderCancelled_then_UPDATE_1 KafkaProcessor " + orderCancelled.isMe() );
            if (orderCancelled.isMe()) {
                // view 객체 조회                           
                List<Mypage> mypageList = mypageRepository.findByOrderId(orderCancelled.getId());
                
                for(Mypage mypage  : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    // view 레파지 토리에 save
                    mypage.setOrderId(orderCancelled.getId());
                    mypage.setStatus(orderCancelled.getStatus());

                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryCanceled_then_UPDATE_2(@Payload DeliveryCanceled deliveryCanceled) {
        try {
            System.out.println("##### listener whenDeliveryCanceled_then_UPDATE_2 KafkaProcessor " + deliveryCanceled.isMe() );
            if (deliveryCanceled.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByDeliveryId(deliveryCanceled.getId());
                for( Mypage mypage  : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    // view 레파지 토리에 save
                    mypage.setStatus(deliveryCanceled.getStatus());
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenDelivered_then_UPDATE_3(@Payload Delivered delivered) {
        try {
            System.out.println("##### listener whenDelivered_then_UPDATE_3  KafkaProcessor " + delivered.isMe() );
            if (delivered.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByDeliveryId(delivered.getId());
                for( Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    // view 레파지 토리에 save
                    mypage.setStatus(delivered.getStatus());
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenDeliveryRequested_then_UPDATE(@Payload DeliveryRequested deliveryRequested) {
        try {
            System.out.println("##### listener whenDeliveryRequested_then_UPDATE KafkaProcessor " + deliveryRequested.isMe() );
            if (deliveryRequested.isMe()) {
                // view 객체 조회
                List<Mypage> mypageList = mypageRepository.findByOrderId(deliveryRequested.getOrderId());
                for(Mypage mypage : mypageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    mypage.setDeliveryId(deliveryRequested.getId());
                    mypage.setStatus(deliveryRequested.getStatus());                    
                    // view 레파지 토리에 save
                    mypageRepository.save(mypage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}