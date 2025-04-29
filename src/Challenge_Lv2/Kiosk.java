package Challenge_Lv2;

import java.util.*;

public class Kiosk {

    private boolean isWork = true;

    private void decorateMenuHead(String input){
        System.out.println("=========================");
        System.out.println(input);
        System.out.println("=========================");
    }

    private void decorateTerminate(String input){
        System.out.println("-------------------------");
        System.out.println(input);
        System.out.println("=========================");
    }

    private void displayInitMenu(List<Menu> menus, Menu cart){
        decorateMenuHead("    [SHAKESHACK MENU]");
        int menuNumber = 0;
        for(; menuNumber < menus.size(); menuNumber++){
            System.out.println((menuNumber + 1) + ". " + menus.get(menuNumber).getCategory());
        }
        if(cart.getMenuItems().size() < 1) decorateTerminate("0. 종료");
        else {
            System.out.println("0. 종료");
            displayOrderMenu(menuNumber);
        }
    }

    private void displayOrderMenu(int i){
        decorateMenuHead("      [ORDER MENU]");
        System.out.println(++i + ". Orders | 장바구니를 확인 후 주문합니다.");
        System.out.println(++i + ". Cancel | 진행중인 주문을 취소합니다.");
        System.out.println("=========================");
    }

    private boolean selectingMenu(Scanner sc, List<Menu> menus, int input, Menu cart) throws Exception{
        Menu select;
        if(input == 0) return false;
        else if (input <= menus.size()) {
            select = menus.get(input - 1);
            selectingItem(sc, input, select, cart);
        }
        else if(cart.getMenuItems().size() > 0 && input <= menus.size() + 2){
            if(input == menus.size() + 1) selectingOrder(sc, cart);
            else if(input == menus.size() + 2) cleaningOrder(sc, cart);
        }
        else throw new Exception("잘못된 메뉴 번호입니다.");
        return true;
    }

    private void selectingItem(Scanner sc, int input, Menu select, Menu cart) throws Exception{
        while(true) {
            decorateMenuHead("     [" + select.getCategory() + " MENU]");
            int number = 0;
            displayMenus(select);
            decorateTerminate("0. 뒤로가기");
            input = Integer.parseInt(sc.nextLine());
            if(input > 0 && input <= select.getMenuItems().size()){
                MenuItem selected = select.getMenuItems().get(input - 1);
                checkSelect(sc, selected, cart);
            }
            else if(input == 0) break;
            else throw new Exception("잘못된 메뉴 번호입니다.");
        }
    }

    private void displayMenus(Menu input){
        int num = 0;
        for(MenuItem items : input.getMenuItems()){
            System.out.println(++ num + ". " + items.getName() + " | " + items.getPrice() + " | " + items.getDescription());
        }
    }

    private void decorateList(String input){
        System.out.println("=========================");
        System.out.println("[" + input + "]");
        System.out.println("-------------------------");
    }

    private void decorateTotal(String input){
        System.out.println("\n[" + input + "]");
        System.out.println("-------------------------");
    }

    private void selectingOrder(Scanner sc, Menu cart) throws Exception{
        decoratePopup("아래와 같이 주문 하시겠습니까?");
        decorateList("Orders");
        double total = 0;
        displayMenus(cart);
        for(MenuItem items : cart.getMenuItems()){
            total += Double.parseDouble(items.getPrice().split(" ")[1]);
        }
        decorateTotal("Total");
        System.out.println("W " + total);
        decorateTerminate("1. 주문     2. 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 1){
            double discount = checkingDiscount(sc);
            double charge = total * ((100 - discount) / 100);
            decoratePopup("주문이 완료되었습니다. 금액은 W " + String.format("%.1f", charge) + " 입니다.");
            cleaningAllOrder(cart);
        }
        else if(input != 2){
            decoratePopup("잘못된 메뉴 번호입니다.");
        }
    }

    private void cleaningAllOrder(Menu cart){
        cart.deleteAllMenuItems();
    }

    private void cleaningOrder(Scanner sc, Menu cart) throws Exception{
        decorateMenuHead("빼고싶은 메뉴를 선택해주세요.");
        decorateList("Orders");
        int i = 0;
        displayMenus(cart);
        System.out.println("0. 전체삭제 / 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 0) {
            System.out.println("전체삭제를 원하시면 0, 메뉴판으로 돌아가시려면 1을 입력해주세요.");
            input = Integer.parseInt(sc.nextLine());
            if(input == 0) {
                cart.deleteAllMenuItems();
                decoratePopup("장바구니의 모든 상품이 삭제되었습니다.");
            }
            else if(input != 1) throw new Exception("잘못된 메뉴 번호입니다.");
        }
        else if(input > 0 && input < cart.getMenuItems().size() + 1){
            String searching = cart.getMenuItems().get(input - 1).getName();
            cart.deleteMenuItems(searching);
            decoratePopup("상품이 장바구니에서 삭제되었습니다.");
        }
    }

    private void decorateSelect(){
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("-------------------------");
        System.out.println("1. 확인       2. 취소");
        System.out.println("=========================");
    }

    private void decoratePopup(String input){
        System.out.println("\n*" + input + "*\n");
    }

    private void checkSelect(Scanner sc, MenuItem selected, Menu cart){
        decorateMenuHead("선택한 메뉴 : " + selected.getName().strip() + " | " + selected.getPrice() + " | " + selected.getDescription());
        decorateSelect();
        int input = Integer.parseInt(sc.nextLine());
        if (input == 1) {
            cart.addMenuItem(selected);
            decoratePopup(selected.getName().strip() + "이(가) 장바구니에 추가되었습니다.");
        }
        else if (input != 2) {
            decoratePopup("1, 2 중 하나만 입력해주세요.");
            checkSelect(sc, selected, cart);
        }
    }

    private double checkingDiscount(Scanner sc){
        while(true) {
            decorateMenuHead("할인 정보를 입력해주세요.");
            int i = 0;
            for (DiscountRate type : DiscountRate.values()) {
                System.out.println(++i + ". " +type.displayDiscount());
            }
            System.out.println("-------------------------");
            int discountInput = Integer.parseInt(sc.nextLine());
            if (discountInput > 0 && discountInput < DiscountRate.values().length + 1) {
                DiscountRate selected = DiscountRate.checkDiscount(discountInput);
                return selected.getDiscountRate();
            } else decoratePopup("올바른 할인 정보를 입력해주세요.");
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
            }catch(NumberFormatException n){
                decoratePopup("문자는 입력할 수 없습니다.");
            }
            catch(Exception e){
                decoratePopup("잘못된 메뉴 번호입니다.");
            }
        }
        sc.close();
    }
}