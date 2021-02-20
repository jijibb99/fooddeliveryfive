package fooddeliveryfive;

import fooddeliveryfive.config.kafka.KafkaProcessor;
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

    }

    @Autowired
    DeliveryRepository deliveryRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_Ship(@Payload Ordered ordered){
        System.out.println("##### listener wheneverOrdered_Ship KafkaProcessor " + ordered.isMe() );
        System.out.println("##### " + ordered.toJson() );
        if(ordered.isMe()){
            // To-Do : SMS 발송, CJ Logistics 연계, ...
            Delivery delivery = new Delivery();
            delivery.setOrderId(ordered.getId());
            delivery.setStatus("Request Delivery");

            deliveryRepository.save(delivery);
            
            System.out.println("##### listener wheneverOrdered_Ship : " + ordered.toJson());
        }
    }

/*
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_Ship(@Payload OrderCancelled orderCancelled){
        System.out.println("##### listener wheneverOrderCancelled_Ship KafkaProcessor " + orderCancelled.isMe() );
        System.out.println("##### " + orderCancelled.toJson() );
        if(orderCancelled.isMe()){
            // To-Do : SMS 발송, CJ Logistics 연계, ...
            Delivery delivery = new Delivery();
            delivery.setOrderId(orderCancelled.getId());
            delivery.setStatus("OrderCancelled");

            deliveryRepository.save(delivery);
            
            System.out.println("##### listener wheneverOrderCancelled_Ship : " + orderCancelled.toJson());
        }
    }
*/


}
