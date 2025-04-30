package Challenge_Lv2;

import java.util.*;
import java.util.stream.*;

public class Menu {
    private List<MenuItem> menuItems = new ArrayList<>();
    private String category;

    public Menu(String category){
        this.category = category;
    }               //카테고리명 설정

    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }  //상품 추가

    public String getCategory(){
        return category;
    }                         //카테고리 리턴

    public void deleteMenuItems(String searchName){                         //상품 제거 - Lambda & Stream 사용, 장바구니 제거 시 사용
        this.menuItems.stream()
                .filter(entry -> entry.getName().equals(searchName))
                .collect(Collectors.toList()).forEach(entry ->
                {
                    menuItems.remove(entry);
                });
    }

    public void deleteAllMenuItems(){
        this.menuItems.removeAll(menuItems);
    } //상품 전체 제거

    public List<MenuItem> getMenuItems(){                                   //상품 전체 조회
        return this.menuItems.stream()
                .collect(Collectors.toList());
    }
}