package Challenge_Lv2;

import java.util.*;

public class Kiosk {

    private boolean isWork = true;                                                      //종료 여부 판별

    private void decorateMenuHead(String input){                                        //출력부 카테고리 구역 데코
        System.out.println("=========================");
        System.out.println(input);
        System.out.println("=========================");
    }

    private void decorateTerminate(String input){                                       //출력부 종료 구역 데코
        System.out.println("-------------------------");
        System.out.println(input);
        System.out.println("=========================");
    }

    private void decorateList(String input){                                            //출력부 메뉴 리스트 데코
        System.out.println("=========================");
        System.out.println("[" + input + "]");
        System.out.println("-------------------------");
    }

    private void decorateTotal(String input){                                           //출력부 총액 데코
        System.out.println("\n[" + input + "]");
        System.out.println("-------------------------");
    }

    private void decorateSelectCheck(){                                                 //추가 확인부 메소드화
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("-------------------------");
        System.out.println("1. 확인       2. 취소");
        System.out.println("=========================");
    }

    private void decoratePopup(String input){
        System.out.println("\n*" + input + "*\n");
    } //삭제 등 팝업 메세지 데코

    private void displayInitMenu(List<Menu> menus, Menu cart){                          //초기화면 출력
        decorateMenuHead("    [SHAKESHACK MENU]");
        int menuNumber = 0;                                                             //순회 위한 임시 변수
        for(; menuNumber < menus.size(); menuNumber++){                                 //각 카테고리 순회하며 출력
            System.out.println((menuNumber + 1) + ". " + menus.get(menuNumber).getCategory());  //x. 카테고리명 꼴
        }
        if(cart.getMenuItems().size() < 1) decorateTerminate("0. 종료");          //장바구니에 담긴 상품이 없을 때 종료메뉴 출력
        else {                                                                         //장바구니에 담긴 상품이 있을 때 장바구니 메뉴 추가 출력
            System.out.println("0. 종료");
            displayOrderMenu(menuNumber);
        }
    }

    private void displayOrderMenu(int i){                                               //장바구니에 상품이 존재 시 주문 메뉴 출력
        decorateMenuHead("      [ORDER MENU]");
        System.out.println(++i + ". Orders | 장바구니를 확인 후 주문합니다.");
        System.out.println(++i + ". Cancel | 진행중인 주문을 취소합니다.");
        System.out.println("=========================");
    }

    private void displayMenus(Menu input){                                              //각 메뉴 출력
        int num = 0;
        for(MenuItem items : input.getMenuItems()){                                     //향상된 for문 사용, x. 메뉴명 | 메뉴가격 | 메뉴설명 꼴
            System.out.println(++ num + ". " + items.getName() + " | " + items.getPrice() + " | " + items.getDescription());
        }
    }

    private boolean selectingMenu(Scanner sc, List<Menu> menus, int input, Menu cart) throws Exception{ //초기화면 메뉴 선택
        Menu select;
        if(input == 0) return false;                                                                    //0 입력 시 종료
        else if (input <= menus.size()) {                                                               //카테고리 선택 시 해당 카테고리 내 메뉴 출력
            select = menus.get(input - 1);
            selectingItem(sc, input, select, cart);
        }
        else if(cart.getMenuItems().size() > 0 && input <= menus.size() + 2){                           //주문관련 선택 시 장바구니 출력
            if(input == menus.size() + 1) selectingOrder(sc, cart);
            else if(input == menus.size() + 2) cleaningOrder(sc, cart);
        }
        else throw new Exception("잘못된 메뉴 번호입니다.");                                               //이외의 입력은 예외처리
        return true;
    }

    private void selectingItem(Scanner sc, int input, Menu select, Menu cart) throws Exception{ //메뉴 내 상품 선택
        while(true) {
            decorateMenuHead("     [" + select.getCategory() + " MENU]");                 //카테고리명 출력
            displayMenus(select);                                                               //메뉴 출력
            decorateTerminate("0. 뒤로가기");                                               //종료 출력
            input = Integer.parseInt(sc.nextLine());
            if(input > 0 && input <= select.getMenuItems().size()){                             //메뉴 선택 시 확인 메뉴로
                MenuItem selected = select.getMenuItems().get(input - 1);
                checkSelect(sc, selected, cart);
            }
            else if(input == 0) break;                                                          //0 입력 시 종료
            else throw new Exception("잘못된 메뉴 번호입니다.");                                    //이외의 입력은 예외처리
        }
    }

    private void selectingOrder(Scanner sc, Menu cart) throws Exception{    //장바구니 주문 요청 시
        decoratePopup("아래와 같이 주문 하시겠습니까?");
        decorateList("Orders");
        double total = 0;
        displayMenus(cart);                                                                     //장바구니 내 상품 출력
        for(MenuItem items : cart.getMenuItems()){                                              //총액 계산
            total += Double.parseDouble(items.getPrice().split(" ")[1]);
        }
        decorateTotal("Total");
        System.out.println("W " + total);
        decorateTerminate("1. 주문     2. 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 1){                                                                         //계산 시 할인 확인 후 주문
            double discount = checkingDiscount(sc);
            double charge = total * ((100 - discount) / 100);
            decoratePopup("주문이 완료되었습니다. 금액은 W " + String.format("%.1f", charge) + " 입니다.");
            cleaningAllOrder(cart);                                                             //주문 확인 출력 후 장바구니 비우기
        }
        else if(input != 2) throw new Exception("잘못된 메뉴 번호입니다.");                         //1, 2 이외의 입력은 예외처리, 2는 초기 화면으로
    }

    private void cleaningAllOrder(Menu cart){                                                   //장바구니 전체 비우기
        cart.deleteAllMenuItems();
    }

    private void cleaningOrder(Scanner sc, Menu cart) throws Exception{         //장바구니 특정 상품 제거
        decorateMenuHead("취소할 메뉴를 선택해주세요.");
        decorateList("Orders");
        displayMenus(cart);
        decorateTerminate("0. 전체삭제 / 메뉴판");
        int input = Integer.parseInt(sc.nextLine());
        if(input == 0) {                                                        //전체삭제 / 메뉴판 이동화면 출력
            decorateMenuHead("전체삭제를 원하시면 0, 메뉴판으로 돌아가시려면 1을 입력해주세요.");
            input = Integer.parseInt(sc.nextLine());
            if(input == 0) {                                                    //전체 삭제 입력 시 장바구니 상품 삭제
                cart.deleteAllMenuItems();
                decoratePopup("장바구니의 모든 상품이 삭제되었습니다.");
            }
            else if(input != 1) throw new Exception("잘못된 메뉴 번호입니다.");    //이외의 입력은 예외처리
        }
        else if(input > 0 && input < cart.getMenuItems().size() + 1){           //입력값이 특정 상품일 때 해당 상품만 삭제
            String searching = cart.getMenuItems().get(input - 1).getName();
            cart.deleteMenuItems(searching);
            decoratePopup("상품이 장바구니에서 삭제되었습니다.");
            if(cart.getMenuItems().size() > 0) cleaningOrder(sc, cart);         //장바구니에 상품이 남아있는 경우 장바구니 재출력
        }
    }

    private void checkSelect(Scanner sc, MenuItem selected, Menu cart){         //상품 선택 시
        decorateMenuHead("선택한 메뉴 : " + selected.getName().strip() + " | " + selected.getPrice() + " | " + selected.getDescription());
        decorateSelectCheck();
        int input = Integer.parseInt(sc.nextLine());
        if (input == 1) {                                                                   //1 입력 시 장바구니에 상품 추가
            cart.addMenuItem(selected);
            decoratePopup(selected.getName().strip() + "이(가) 장바구니에 추가되었습니다.");
        }
        else if (input != 2) {                                                              //2 입력 시 메뉴판으로, 이외의 입력은 확인메시지 출력 후 상품선택화면 재출력
            decoratePopup("1, 2 중 하나만 입력해주세요.");
            checkSelect(sc, selected, cart);
        }
    }

    private double checkingDiscount(Scanner sc){                            //할인 입출력란
        while(true) {                                                       //기본적으로 무한반복
            decorateMenuHead("할인 정보를 입력해주세요.");
            int i = 0;
            for (DiscountRate type : DiscountRate.values()) {               //할인정보 출력
                System.out.println(++i + ". " +type.displayDiscount());
            }
            System.out.println("-------------------------");
            int discountInput = Integer.parseInt(sc.nextLine());
            if (discountInput > 0 && discountInput < DiscountRate.values().length + 1) {    //올바른 할인번호 입력 시 할인율 반영
                DiscountRate selected = DiscountRate.checkDiscount(discountInput);
                return selected.getDiscountRate();
            } else decoratePopup("올바른 할인 정보를 입력해주세요.");                     //이외의 입력은 올바르지 않음 메시지 출력
        }
    }

    public void start(List<Menu> menus){                                //실행
        Scanner sc = new Scanner(System.in);

        Menu cart = new Menu("Selected");                       //시작 시 장바구니 생성

        while (isWork) {                                                //종료 전까지 무한반복
            try {
                displayInitMenu(menus, cart);                           //초기 메뉴 출력
                int input = Integer.parseInt(sc.nextLine());            //입력받아 이를 전달
                isWork = selectingMenu(sc, menus, input, cart);         //종료 여부 판별
            }catch(NumberFormatException n){                            //문자 입력 시 예외처리
                decoratePopup("문자는 입력할 수 없습니다.");
            }
            catch(Exception e){                                         //올바르지 않은 메뉴 번호 입력 시 예외처리
                decoratePopup("잘못된 메뉴 번호입니다.");
            }
        }
        sc.close();
    }
}