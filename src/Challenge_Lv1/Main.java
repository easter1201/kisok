package Challenge_Lv1;

import java.util.*;
public class Main {

    public static void main(String[] args) {
        List<Menu> menus = new ArrayList<>();

        Menu burgers = new Menu("Burgers");
        burgers.addMenuItem(new MenuItem("ShakeBurger   ", "W 6.9", "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        burgers.addMenuItem(new MenuItem("SmokeShack    ", "W 8.9", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        burgers.addMenuItem(new MenuItem("CheeseBurger  ", "W 6.9", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        burgers.addMenuItem(new MenuItem("Hamburger     ", "W 5.4", "비프패티를 기반으로 야채가 들어간 기본버거"));

        Menu drinks = new Menu("Drinks");
        drinks.addMenuItem(new MenuItem("ChocoShake     ", "W 4.9", "초코쉐이크"));
        drinks.addMenuItem(new MenuItem("Coke           ", "W 3.0", "콜라"));

        Menu desserts = new Menu("Desserts");
        desserts.addMenuItem(new MenuItem("FriedPotato  ", "W 4.5", "감자튀김"));

        menus.add(burgers);
        menus.add(drinks);
        menus.add(desserts);

        Kiosk ki = new Kiosk();
        ki.start(menus);
    }
}