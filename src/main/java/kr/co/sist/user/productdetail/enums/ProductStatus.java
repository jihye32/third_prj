package kr.co.sist.user.productdetail.enums;

public enum ProductStatus {
	//PRODUCT_STATUS = productStatus : 상품 상태(U: 중고, N: 새거)
	USED("U"),
	NEW("N");
	
	private final String code;
	
	ProductStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ProductStatus fromCode(String code) {
    	if (code == null) return USED;
        for (ProductStatus ps : values()) {
            if (ps.code.equals(code)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Unknown ProductStatus: " + code);
    }
}
