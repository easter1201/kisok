package Lv4;

import java.util.*;

public class Kiosk {

    private boolean isWork = true;

    private void displayInitMenu(List<Menu> menus, Menu cart){
        System.out.println("[SHAKESHACK MENU]");
        int i = 0;
        for(; i < menus.size(); i++){
            System.out.println((i + 1) + ". " + menus.get(i).getCategory());
        }
        System.out.println("0. 종료");
        if(cart.getMenuItems().size() > 0){
            displayOrderMenu(i);
        }
    }

    private void displayOrderMenu(int i){
        System.out.println("[ORDER MENU]");
        System.out.println(++i + ". Orders       | 장바구니를 확인 후 주문합니다.");
        System.out.println(++i + ". Cancel       | 진행중인 주문을 취소합니다.");
    }

    private boolean selectingMenu(Scanner sc, List<Menu> menus, int input, Menu cart){
        Menu select;
        if(input == 0) return false;
        else if (input <= menus.size()) {
            select = menus.get(input - 1);
            System.out.println("선택한 메뉴 : " + select.getCategory() + "\n");
            selectingItem(sc, input, select, cart);
        }
        else if(cart.getMenuItems().size() > 0 && input <= menus.size() + 2){
            if(input == menus.size() + 1) selectingOrder(sc, cart);
            else if(input == menus.size() + 2) cleaningOrder(cart);
        }
        else System.out.println("잘못된 메뉴 번호입니다.\n");
        return true;
    }

    private void selectingItem(Scanner sc, int input, Menu select, Menu cart){
        while(true) {
            System.out.println("[" + select.getCategory() + " MENU]");
            int number = 0;
            for (MenuItem item : select.getMenuItems()) {
                System.out.println(++number + ". " + item.getName() + " | " + item.getPrice() + " | " + item.getDescription());
            }
            System.out.println("0. 뒤로가기");
            input = Integer.parseInt(sc.nextLine());
            if(input > 0 && input <= select.getMenuItems().size()){
                MenuItem selected = select.getMenuItems().get(input - 1);
                System.out.println("\n선택한 메뉴 : " + selected.getName().strip() + " | " + selected.getPrice() + " | " + selected.getDescription());
                checkSelect(sc, selected, cart);
            }
            else if(input == 0) break;
            else System.out.println("잘못된 메뉴 번호입니다.\n");
        }
    }

    private void selectingOrder(Scanner sc, Menu cart){
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        System.out.println("[Orders]");
        double total = 0;
        for(MenuItem items : cart.getMenuItems()){
            System.out.println(items.getName() + " | " + items.getPrice() + " | " + items.getDescription());
            total += Double.parseDouble(items.getPrice().split(" ")[1]);
        }
        System.out.println("\n[Total]");
        System.out.println("W " + total);
        System.out.println("\n1. 주문     2. 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 1){
            System.out.println("주문이 완료되었습니다. 금액은 W " + total + "입니다.\n");
            cleaningOrder(cart);
        }
        else if(input != 2){
            System.out.println("잘못된 메뉴 번호입니다.\n");
        }
    }

    private void cleaningOrder(Menu cart){
        cart.getMenuItems().clear();
        System.out.println("\n장바구니가 비워졌습니다.\n");
    }

    private void checkSelect(Scanner sc, MenuItem selected, Menu cart){
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?\n1. 확인       2. 취소");
        int input = Integer.parseInt(sc.nextLine());
        if (input == 1) {
            cart.addMenuItem(selected);
            System.out.println("\n" + selected.getName().strip() + "이(가) 장바구니에 추가되었습니다.\n");
        }
        else if (input != 2) {
            System.out.println("1, 2 중 하나만 입력해주세요.\n");
            checkSelect(sc, selected, cart);
        }
    }

    public void start(List<Menu> menus){
        Scanner sc = new Scanner(System.in);

        Menu cart = new Menu();

        while (isWork) {
            try {
                displayInitMenu(menus, cart);
                int input = Integer.parseInt(sc.nextLine());
                isWork = selectingMenu(sc, menus, input, cart);
            }catch(NumberFormatException e){
                System.out.println("문자는 입력할 수 없습니다.\n");
            }
            catch(Exception e){
                System.out.println("잘못된 입력입니다.\n");
            }
        }
        sc.close();
    }
}
