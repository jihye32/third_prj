package kr.co.sist.user.productdetail.enums;

public enum DealType {
	//SELL_CODE = dealType : 거래방법(1: 직거래/2: 택배거래)
	DIRECT(1),
	DELIVERY(2);

    private final int code;

    DealType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DealType fromCode(int code) {
    	if (code == 0) return DELIVERY;
        for (DealType d : values()) {
            if (d.code == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown SellStatus: " + code);
    }
}
