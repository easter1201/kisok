package Lv4;

import java.util.*;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();
    private String category;

    public Menu(String category){
        this.category = category;
    }

    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }

    public String getCategory(){
        return category;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }
}
