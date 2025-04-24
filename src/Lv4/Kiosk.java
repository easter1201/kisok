package Lv4;

import java.util.*;

public class Kiosk {

    private boolean isWork = true;

    private void displayInitMenu(List<Menu> menus){
        System.out.println("[SHAKESHACK MENU]");
        for(int i = 0; i < menus.size(); i++){
            System.out.println((i + 1) + ". " + menus.get(i).getCategory());
        }
        System.out.println("0. 종료");
    }

    private void selectingMenu(Scanner sc, List<Menu> menus, int input, boolean isWork){
        Menu select;
        if (input > 0 && input <= menus.size()) {
            select = menus.get(input - 1);
            System.out.println("선택한 메뉴 : " + select.getCategory() + "\n");
            selectingItem(sc, input, select);
        }
        else if(input == 0) isWork = false;
        else System.out.println("잘못된 메뉴 번호입니다.");
    }

    private void selectingItem(Scanner sc, int input, Menu select){
        while(true) {
            System.out.println("[" + select.getCategory() + " MENU]");
            int number = 0;
            for (MenuItem item : select.getMenuItems()) {
                System.out.println(++number + ". " + item.getName() + " | " + item.getPrice() + " | " + item.getDescription());
            }
            System.out.println("0. 뒤로가기");
            input = Integer.parseInt(sc.nextLine());
            if(input > 0 && input <= select.getMenuItems().size()){
                System.out.println("선택한 메뉴 : " + select.getMenuItems().get(input - 1).getName() + "\n");
            }
            else if(input == 0) break;
            else System.out.println("잘못된 메뉴 번호입니다.");
        }
    }

    public void start(List<Menu> menus){
        Scanner sc = new Scanner(System.in);

        while (isWork) {
            try {
                displayInitMenu(menus);
                int input = Integer.parseInt(sc.nextLine());
                selectingMenu(sc, menus, input, isWork);
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
