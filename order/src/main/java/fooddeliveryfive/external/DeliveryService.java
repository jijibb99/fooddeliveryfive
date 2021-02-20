
package fooddeliveryfive.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@FeignClient(name="delivery", url="http://localhost:8088")
public interface DeliveryService {

    @RequestMapping(method= RequestMethod.DELETE, path="/deliveries/{deliveryId}")
    public void cancelDelivery(@PathVariable("deliveryId") Long id);

}