
package fooddeliveryfive;

public class MenuDeleted extends AbstractEvent {

    private Long menuId;
    private String menuName;

    public Long getId() {
        return menuId;
    }

    public void setId(Long menuId) {
        this.menuId = menuId;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
