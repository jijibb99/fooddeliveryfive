package fooddeliveryfive;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String status;

    @PostPersist
    public void onPostPersist(){
        System.out.println("##### Delivery PostPersist ####################: " + this.id + " ," + this.orderId + " ," + this.status);

        DeliveryRequested deliveryRequested = new DeliveryRequested();
        BeanUtils.copyProperties(this, deliveryRequested);
        deliveryRequested.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("##### Delivery PostUpdate ####################: " + this.id + "  ," + this.status);

             
        if ("Delivered".equals(this.status)){
            System.out.println("##### Delivery status1 : " + this.status);

            Delivered delivered = new Delivered();
            BeanUtils.copyProperties(this, delivered);
            delivered.publishAfterCommit();

        }
        else if ("DeliveryCanceled".equals(this.status)){
            System.out.println("##### Delivery status2 : " + this.status);

            DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
            BeanUtils.copyProperties(this, deliveryCanceled);
            deliveryCanceled.publishAfterCommit();    
        } 
        else {
            System.out.println("##### Delivery status3 : " + this.status);
        }

    }
    @PreUpdate
    public void onPreUpdate(){

        System.out.println("##### Delivery onPreUpdate ####################: " + this.id + "  ," + this.status);

    }    

  

    @PostRemove
    public void onPostRemove(){

        System.out.println("##### Delivery onPostRemove ####################: " + this.id + " ," + this.orderId + " ," + this.status);

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled();
        this.status = "OrderCancelled";
        BeanUtils.copyProperties(this, deliveryCanceled);
        deliveryCanceled.publishAfterCommit();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
