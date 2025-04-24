package Lv3;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("ShakeBurger   ", "W 6.9", "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("SmokeShack    ", "W 8.9", "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("CheeseBurger  ", "W 6.9", "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
        menuItems.add(new MenuItem("Hamburger     ", "W 5.4", "비프패티를 기반으로 야채가 들어간 기본버거"));

        Kiosk ki = new Kiosk(menuItems);

        ki.start();

    }
}
