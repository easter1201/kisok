package Challenge_Lv2;

public class MenuItem {
    private String name;
    private String price;
    private String description;

    public MenuItem(String name){
        this.name = name;
    }

    public MenuItem(String name, String price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName(){
        return name;
    }       //메뉴명 리턴

    public String getPrice(){
        return price;
    }     //메뉴 가격 리턴

    public String getDescription(){
        return description;
    }   //메뉴 설명 리턴
}