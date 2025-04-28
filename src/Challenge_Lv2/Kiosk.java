package Challenge_Lv2;

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
        System.out.println("\n[ORDER MENU]");
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
            else if(input == menus.size() + 2) cleaningOrder(sc, cart);
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
            double discount = checkingDiscount(sc);
            double charge = total * ((100 - discount) / 100);
            System.out.println("주문이 완료되었습니다. 금액은 W " + String.format("%.1f", charge) + " 입니다.\n");
            cleaningOrder(sc, cart);
        }
        else if(input != 2){
            System.out.println("잘못된 메뉴 번호입니다.\n");
        }
    }

    private void cleaningOrder(Scanner sc, Menu cart){
        System.out.println("\n빼고싶은 메뉴를 선택해주세요.");
        System.out.println("[Orders]");
        int i = 0;
        for(MenuItem items : cart.getMenuItems()){
            System.out.println(++i + ". " + items.getName() + " | " + items.getPrice() + " | " + items.getDescription());
        }
        System.out.println("0. 전체삭제 / 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 0) {
            System.out.println("전체삭제를 원하시면 0, 메뉴판으로 돌아가시려면 1을 입력해주세요.");
            input = Integer.parseInt(sc.nextLine());
            if(input == 0) {
                cart.deleteAllMenuItems();
                System.out.println("장바구니의 모든 상품이 삭제되었습니다.");
            }
            else if(input != 1) System.out.println("올바른 번호를 입력해주세요.");
        }
        else if(input > 0 && input < cart.getMenuItems().size() + 1){
            String searching = cart.getMenuItems().get(input - 1).getName();
            cart.deleteMenuItems(searching);
            System.out.println("상품이 장바구니에서 삭제되었습니다.");
        }
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

    private double checkingDiscount(Scanner sc){
        while(true) {
            System.out.println("할인 정보를 입력해주세요.");
            int i = 0;
            for (DiscountRate type : DiscountRate.values()) {
                System.out.println(++i + ". " +type.displayDiscount());
            }
            int discountInput = Integer.parseInt(sc.nextLine());
            if (discountInput > 0 && discountInput < DiscountRate.values().length + 1) {
                DiscountRate selected = DiscountRate.checkDiscount(discountInput);
                return selected.getDiscountRate();
            } else System.out.println("올바른 할인 정보를 입력해주세요.");
        }
    }

    public void start(List<Menu> menus){
        Scanner sc = new Scanner(System.in);

        Menu cart = new Menu("Selected");

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