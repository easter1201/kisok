package Challenge_Lv2;

public enum DiscountRate {
    patriot(1, 10),
    pregnant(2, 8),
    soldier(3, 5),
    student(4, 3),
    normal(5, 0);

    private final int discountNumber;
    private final int discount;

    DiscountRate(int discountNumber, int discount){
        this.discountNumber = discountNumber;
        this.discount = discount;
    }

    public String displayDiscount(){
        return this.name() + " : " + this.discount + "%";
    }

    public static DiscountRate checkDiscount(int discountNumber){
        for(DiscountRate fromNum : values()){
            if(fromNum.discountNumber == discountNumber) return fromNum;
        }
        return null;
    }

    public int getDiscountRate(){
        return discount;
    }
}