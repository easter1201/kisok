package Challenge_Lv2;

public enum DiscountRate {
    patriot(1, 10), //국가유공자, 10%
    pregnant(2, 8), //임산부, 8%
    soldier(3, 5),  //군인, 5%
    student(4, 3),  //학생, 3%
    normal(5, 0);   //일반인

    private final int discountNumber;
    private final int discount;

    DiscountRate(int discountNumber, int discount){
        this.discountNumber = discountNumber;
        this.discount = discount;
    }

    public String displayDiscount(){
        return this.name() + " : " + this.discount + "%";
    }   //할인내역 리턴

    public static DiscountRate checkDiscount(int discountNumber){       //할인율 조회
        for(DiscountRate fromNum : values()){
            if(fromNum.discountNumber == discountNumber) return fromNum;
        }
        return null;
    }

    public int getDiscountRate(){
        return discount;
    }       //할인율 리턴
}