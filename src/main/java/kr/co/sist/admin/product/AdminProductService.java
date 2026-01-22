package kr.co.sist.admin.product;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProductService {
    @Autowired
    private AdminProductDAO pDAO;

    // 1. 페이지네이션 연산 로직 (12개씩 출력 권장)
    public int pageScale() { return 12; }

    public int totalPage(int totalCount, int pageScale) {
        return (int)Math.ceil((double)totalCount / pageScale);
    }

    public int startNum(int currentPage, int pageScale) {
        return currentPage * pageScale - pageScale + 1;
    }

    // 2. 상품 목록 조회 (ProductDTO 사용 및 예외 처리)
    public List<AdminProductDomain> getProductList(AdminProductDTO pDTO) {
        List<AdminProductDomain> list = null;
        try {
            list = pDAO.selectProductList(pDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getProductTotalCount(AdminProductDTO pDTO) {
        int totalCount = 0;
        try {
            totalCount = pDAO.selectTotalCount(pDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // 3. 상세 정보 조회 (이미지와 정보 분리 - 요구사항 반영)
    public AdminProductDomain getProductDetail(int productNo) {
        AdminProductDomain pd = null;
        try {
            pd = pDAO.selectProductDetail(productNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pd;
    }

    public List<String> getProductImages(int productNo) {
        List<String> list = null;
        try {
            list = pDAO.selectProductImages(productNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. 페이지네이션 HTML 생성
    public String getPaginationHtml(AdminProductDTO pDTO) {
        // 기존 Board 프로젝트의 pagination2 로직을 이식하여 리턴
        return "페이지네이션 태그 문자열";
    }
}