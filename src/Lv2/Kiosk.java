package Lv2;

import java.util.*;

public class Kiosk {
    private List<MenuItem> menuItems;

    public Kiosk(List<MenuItem> menuItems){
        this.menuItems = menuItems;
    }

    public void start(){
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("[SHAKESHACK MENU]");
                int number = 0;
                for (MenuItem menu : menuItems) {
                    System.out.print(++number + ". " + menu.getName() + "| ");
                    System.out.print(menu.getPrice() + " | ");
                    System.out.println(menu.getDescription());
                }
                System.out.println("0. 종료           | 종료");
                int input = Integer.parseInt(sc.nextLine());

                if (input > 0 && input <= menuItems.size()) {
                    MenuItem select = menuItems.get(input - 1);
                    System.out.println("선택한 메뉴 : " + select.getName() + " | " + select.getPrice() + " | " + select.getDescription() + "\n");
                }
                else if (input == 0) {
                    break;
                } else {
                    System.out.println("잘못된 메뉴 번호입니다.");
                }
            }catch(NumberFormatException e){
                System.out.println("문자는 입력할 수 없습니다.");
            }
            catch(Exception e){
                System.out.println("잘못된 입력입니다.");
            }
        }
        sc.close();
    }

}
