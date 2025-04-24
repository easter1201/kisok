package Lv1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] menuArray = {"1. ShackBurger   | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거",
                              "2. SmokeShack    | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거",
                              "3. CheeseBurger  | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거",
                              "4. Hamburger     | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거"};

        while (true) {
            try {
                System.out.println("[SHAKESHACK MENU]");
                for (String menu : menuArray) {
                    System.out.println(menu);
                }
                System.out.println("0. 종료           | 종료");
                int input = Integer.parseInt(sc.nextLine());

                if (input > 0 && input < 5) System.out.println(menuArray[input - 1] + "\n");
                else if (input == 0) {
                    break;
                } else {
                    System.out.println("잘못된 메뉴 번호입니다.");
                }
            }catch(Exception e){
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}