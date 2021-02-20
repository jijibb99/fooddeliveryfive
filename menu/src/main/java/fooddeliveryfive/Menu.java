package fooddeliveryfive;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Menu_table")
public class Menu {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long menuId;
    private String menuName;

    @PostPersist
    public void onPostPersist(){
        MenuRegistered menuRegistered = new MenuRegistered();
        BeanUtils.copyProperties(this, menuRegistered);
        menuRegistered.publishAfterCommit();


    }

    @PostRemove
    public void onPostRemove(){
        MenuDeleted menuDeleted = new MenuDeleted();
        BeanUtils.copyProperties(this, menuDeleted);
        menuDeleted.publishAfterCommit();


    }


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }




}
