package kr.co.sist.admin.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
    @Autowired
    private AdminMemberDAO mDAO;

    public List<AdminMemberDomain> getMemberList(AdminMemberDTO mDTO) {
        List<AdminMemberDomain> list = null;
        try {
            list = mDAO.selectMemberList(mDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalCount(AdminMemberDTO mDTO) {
        int totalCount = 0;
        try {
            totalCount = mDAO.selectTotalCount(mDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    public String getPaginationHtml(AdminMemberDTO mDTO, int totalCount) {
        int totalPage = (int)Math.ceil((double)totalCount / mDTO.getPageScale());
        int currentPage = mDTO.getCurrentPage();
        int pageBlock = 5;

        String query = "";
        if(mDTO.getKeyword() != null) query += "&keyword=" + mDTO.getKeyword();

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if(endPage > totalPage) endPage = totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<nav><ul class='pagination justify-content-center'>");
        
        if(startPage > 1) {
            sb.append(String.format("<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&laquo;</a></li>", startPage-1, query));
        }

        for(int i=startPage; i<=endPage; i++) {
            String active = (i == currentPage) ? "active" : "";
            sb.append(String.format("<li class='page-item %s'><a class='page-link' href='?currentPage=%d%s'>%d</a></li>", active, i, query, i));
        }

        if(endPage < totalPage) {
            sb.append(String.format("<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&raquo;</a></li>", endPage+1, query));
        }

        sb.append("</ul></nav>");
        return sb.toString();
        
        
    }
    
 // 기존 코드 아래에 추가
    public AdminMemberDetailDomain getMemberDetail(String userId) {
        AdminMemberDetailDomain detail = null;
        try {
            detail = mDAO.selectMemberDetail(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
    
    
  
}