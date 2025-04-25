package Lv4;

import java.util.*;
import java.util.stream.*;

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

    public void deleteMenuItems(String searchName){
        this.menuItems.stream()
            .filter(entry -> entry.getName().equals(searchName))
            .collect(Collectors.toList()).forEach(entry ->
            {
                menuItems.remove(entry);
            });
    }

    public void deleteAllMenuItems(){
        this.menuItems.removeAll(menuItems);
    }

    public List<MenuItem> getMenuItems(){
        return this.menuItems.stream()
                .collect(Collectors.toList());
    }
}
