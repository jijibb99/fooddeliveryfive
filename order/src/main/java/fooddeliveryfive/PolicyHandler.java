package fooddeliveryfive;

import fooddeliveryfive.config.kafka.KafkaProcessor;

import java.util.List;

import javax.swing.plaf.basic.BasicBorders.MenuBarBorder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){
        System.out.println("##### listener onStringEventListener KafkaProcessor ");
    }


    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MenuRepository menuRegistered;



    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryRequested_UpdateStatus(@Payload DeliveryRequested deliveryRequested){

        if(deliveryRequested.isMe()){
            List<Order> orderList = orderRepository.findByid(deliveryRequested.getOrderId());
            for(Order order : orderList){
                // order 객체에 이벤트의 eventDirectValue 를 set 함
                order.setDeliveryId(deliveryRequested.getId());
                order.setStatus(deliveryRequested.getStatus());
                // order 레파지토리에 save
                orderRepository.save(order);
            }
            
            System.out.println("##### listener UpdateStatus : " + deliveryRequested.toJson());
        }
    } 

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_UpdateStatus(@Payload Delivered delivered){

        if(delivered.isMe()){
            List<Order> orderList = orderRepository.findByid(delivered.getOrderId());
            for(Order order : orderList){
                // order 객체에 이벤트의 eventDirectValue 를 set 함
                order.setStatus(delivered.getStatus());
                // order 레파지토리에 save
                orderRepository.save(order);
            }

            System.out.println("##### listener UpdateStatus : " + delivered.toJson());
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCanceled_UpdateStatus(@Payload DeliveryCanceled deliveryCanceled){

        if(deliveryCanceled.isMe()){
            if("OrderCancelled".equals(deliveryCanceled.getStatus()) ){
                System.out.println("##### listener UpdateStatus : order Cancelled,  " + deliveryCanceled.toJson());

            } else {
                List<Order> orderList = orderRepository.findByid(deliveryCanceled.getOrderId());
                for(Order order : orderList){
                    // order 객체에 이벤트의 eventDirectValue 를 set 함
                    order.setStatus(deliveryCanceled.getStatus());
                    // order 레파지토리에 save
                    orderRepository.save(order);
                }
    
            }

            System.out.println("##### listener UpdateStatus : " + deliveryCanceled.toJson());
        }
    }    
    

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMenuRegistered_(@Payload MenuRegistered menuRegistered){

        if(menuRegistered.isMe()){
            System.out.println("##### listener  : " + menuRegistered.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMenuDeleted_(@Payload MenuDeleted menuDeleted){

        if(menuDeleted.isMe()){
            System.out.println("##### listener  : " + menuDeleted.toJson());
        }
    }

}
