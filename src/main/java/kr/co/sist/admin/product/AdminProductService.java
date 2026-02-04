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

    public AdminProductDomain getProductDetail(int productNo) {
        AdminProductDomain pd = null;
        try {
            pd = pDAO.selectProductDetail(productNo);
            
            if (pd != null) {
                pd.setTime_string(calculateTimeString(pd)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pd;
    }

    public List<String> getProductImages(int productNo) {
        try {
            return pDAO.selectProductImages(productNo);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPaginationHtml(AdminProductDTO pDTO, int totalCount) {
        int totalPage = (int)Math.ceil((double)totalCount / pDTO.getPageScale());
        int currentPage = pDTO.getCurrentPage();
        int pageBlock = 5; 

        String queryParams = "";
        if (pDTO.getKeyword() != null && !pDTO.getKeyword().isEmpty()) queryParams += "&keyword=" + pDTO.getKeyword();
        if (pDTO.getSortBy() != null) queryParams += "&sort=" + pDTO.getSortBy();
        if (pDTO.getCategory_code() != 0) queryParams += "&category=" + pDTO.getCategory_code();

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
    
    // 6. 경과 시간 계산 로직
    public String calculateTimeString(AdminProductDomain domain) {
        java.util.Date baseDate = (domain.getModify_date() != null) 
                                  ? domain.getModify_date() 
                                  : domain.getInput_date();
        if (baseDate == null) return "-";
        long diffTime = (System.currentTimeMillis() - baseDate.getTime()) / 1000;
        if (diffTime < 60) return "방금 전";
        if (diffTime < 3600) return (diffTime / 60) + "분 전";
        if (diffTime < 86400) return (diffTime / 3600) + "시간 전";
        return (diffTime / 86400) + "일 전";
    }
}