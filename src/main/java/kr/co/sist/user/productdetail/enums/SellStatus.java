package kr.co.sist.user.productdetail.enums;

public enum SellStatus {
	//STATUS_CODE = sellStatus : 판매 상태(1: 판매중, 2: 예약중, 3: 판매완료)
	ON_SALE(1),
	RESERVED(2),
	SOLD_OUT(3);

    private final int code;

    SellStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SellStatus fromCode(int code) {
    	if (code == 0) return ON_SALE;
        for (SellStatus s : values()) {
            if (s.code == code) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown SellStatus: " + code);
    }
}
