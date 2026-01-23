package kr.co.sist.admin.product;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProductService {
    @Autowired
    private AdminProductDAO pDAO;

    public int pageScale() { return 12; }

    public List<AdminProductDomain> getProductList(AdminProductDTO pDTO) {
        try {
            return pDAO.selectProductList(pDTO);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getProductTotalCount(AdminProductDTO pDTO) {
        try {
            return pDAO.selectTotalCount(pDTO);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getPaginationHtml(AdminProductDTO pDTO, int totalCount) {
        int totalPage = (int)Math.ceil((double)totalCount / pDTO.getPageScale());
        int currentPage = pDTO.getCurrentPage();
        int pageBlock = 5; 

        // 파라미터 유지 (검색어, 카테고리, 정렬)
        String queryParams = "";
        if (pDTO.getKeyword() != null && !pDTO.getKeyword().isEmpty()) queryParams += "&keyword=" + pDTO.getKeyword();
        if (pDTO.getSortBy() != null) queryParams += "&sort=" + pDTO.getSortBy();
        if (pDTO.getCategory() != 0) queryParams += "&category=" + pDTO.getCategory();

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if (endPage > totalPage) endPage = totalPage;

        StringBuilder html = new StringBuilder();
        html.append("<nav><ul class='pagination justify-content-center'>");

        if (startPage > 1) {
            html.append(String.format("<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&laquo;</a></li>", startPage - 1, queryParams));
        }

        for (int i = startPage; i <= endPage; i++) {
            String active = (i == currentPage) ? "active" : "";
            html.append(String.format("<li class='page-item %s'><a class='page-link' href='?currentPage=%d%s'>%d</a></li>", active, i, queryParams, i));
        }

        if (endPage < totalPage) {
            html.append(String.format("<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&raquo;</a></li>", endPage + 1, queryParams));
        }

        html.append("</ul></nav>");
        return html.toString();
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