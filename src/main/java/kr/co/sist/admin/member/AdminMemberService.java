package kr.co.sist.admin.member;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class AdminMemberService {

    @Autowired
    private AdminMemberDAO mDAO;

    // application.properties에서 직접 값 읽기
    @Value("${user.crypto.key}")
    private String cryptoKey;

    @Value("${user.crypto.salt}")
    private String cryptoSalt;

    private TextEncryptor textEncryptor;

    /**
     * 의존성 주입 후 복호화 객체 직접 초기화
     */
    @PostConstruct
    public void init() {
        this.textEncryptor = Encryptors.text(cryptoKey, cryptoSalt);
    }

    // [1] 메인: 회원 목록 조회 (복호화 적용)
    public List<AdminMemberDomain> getMemberList(AdminMemberDTO mDTO) {
        List<AdminMemberDomain> list = null;
        try {
            list = mDAO.selectMemberList(mDTO);
            
            if (list != null) {
                for (AdminMemberDomain domain : list) {
                    try {
                        if (domain.getName() != null) domain.setName(textEncryptor.decrypt(domain.getName()));
                        if (domain.getEmail() != null) domain.setEmail(textEncryptor.decrypt(domain.getEmail()));
                    } catch (Exception e) {
                        // 복호화 실패 시 원본 데이터 유지
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // [2] 메인: 전체 회원 수 조회
    public int getTotalCount(AdminMemberDTO mDTO) {
        int totalCount = 0;
        try {
            totalCount = mDAO.selectTotalCount(mDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // [3] 메인: 페이지네이션 HTML 생성
    public String getPaginationHtml(AdminMemberDTO mDTO, int totalCount) {
        int totalPage = (int) Math.ceil((double) totalCount / mDTO.getPageScale());
        int currentPage = mDTO.getCurrentPage();
        int pageBlock = 5;
        String query = (mDTO.getKeyword() != null) ? "&keyword=" + mDTO.getKeyword() : "";

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if (endPage > totalPage)
            endPage = totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<nav><ul class='pagination justify-content-center'>");
        if (startPage > 1) {
            sb.append(String.format(
                    "<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&laquo;</a></li>",
                    startPage - 1, query));
        }
        for (int i = startPage; i <= endPage; i++) {
            String active = (i == currentPage) ? "active" : "";
            sb.append(
                    String.format("<li class='page-item %s'><a class='page-link' href='?currentPage=%d%s'>%d</a></li>",
                            active, i, query, i));
        }
        if (endPage < totalPage) {
            sb.append(String.format(
                    "<li class='page-item'><a class='page-link' href='?currentPage=%d%s'>&raquo;</a></li>", endPage + 1,
                    query));
        }
        sb.append("</ul></nav>");
        return sb.toString();
    }

    // [4] 상세: 회원 기본 정보 조회 (복호화 적용)
    public AdminMemberDetailDomain getMemberDetail(String userId) {
        AdminMemberDetailDomain detail = null;
        try {
            detail = mDAO.selectMemberDetail(userId);
            
            if (detail != null) {
                try {
                    if (detail.getName() != null) detail.setName(textEncryptor.decrypt(detail.getName()));
                    if (detail.getEmail() != null) detail.setEmail(textEncryptor.decrypt(detail.getEmail()));
                } catch (Exception e) {
                    // 복호화 실패 시 원본 데이터 유지
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detail;
    }

    // [5] PRDV: 상점 프로필 조회
    public AdminMemberPrdvDomain getStoreDetail(String userId) {
        AdminMemberPrdvDomain store = null;
        try {
            store = mDAO.selectStoreDetail(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }

    // [6] PRDV: 현재 판매중 상품 개수 조회
    public int getStoreProductCount(String userId) {
        int count = 0;
        try {
            count = mDAO.selectStoreProductCount(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // [7] PRDV: 현재 판매중 상품 리스트 (정렬/페이징)
    public List<AdminMemberPrdvDomain> getStoreProducts(String userId, String sort, int currentPage) {
        List<AdminMemberPrdvDomain> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("sort", sort);
        params.put("start", (currentPage - 1) * 10 + 1);
        params.put("end", currentPage * 10);
        try {
            list = mDAO.selectStoreProducts(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // [8] PRDV: 상점 후기 리스트 조회
    public List<AdminMemberPrdvDomain> getStoreReviews(String userId) {
        List<AdminMemberPrdvDomain> list = null;
        try {
            list = mDAO.selectStoreReviews(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // [9] PRDV: 상점 상세 전용 페이지네이션 HTML
    public String getPrdvPagination(String userId, String sort, int currentPage, int totalCount) {
        int pageScale = 10;
        int totalPage = (int) Math.ceil((double) totalCount / pageScale);
        int pageBlock = 5;
        String query = "&userId=" + userId + "&sort=" + sort;

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if (endPage > totalPage)
            endPage = totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<nav><ul class='pagination justify-content-center'>");
        if (startPage > 1) {
            sb.append(String.format(
            		"<li class='page-item'><a class='page-link' href='/manage/member/member_product?userId=%s&type=%s&currentPage=%d'>&laquo;</a></li>",
                    userId, sort, startPage - 1));
        }
        for (int i = startPage; i <= endPage; i++) {
            String active = (i == currentPage) ? "active" : "";
            sb.append(String.format(
                    "<li class='page-item %s'><a class='page-link' href='?userId=%s&sort=%s&currentPage=%d'>%d</a></li>",
                    active, userId, sort, i, i));
        }
        if (endPage < totalPage) {
            sb.append(String.format(
                    "<li class='page-item'><a class='page-link' href='?userId=%s&sort=%s&currentPage=%d'>&raquo;</a></li>",
                    userId, sort, endPage + 1));
        }
        sb.append("</ul></nav>");
        return sb.toString();
    }

    // [10] HISTORY: 거래 내역 헤더용 상점 정보 조회
    public AdminMemberProductDomain getMemberStoreInfo(String userId) {
        AdminMemberProductDomain store = null;
        try {
            store = mDAO.selectMemberStoreInfo(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }

    // [11] HISTORY: 과거 거래 내역 총 개수 조회
    public int getHistoryCount(String userId, String type) {
        int count = 0;
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        params.put("type", type);
        try {
            count = mDAO.selectHistoryCount(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // [12] HISTORY: 과거 거래 내역 리스트 (구매/판매, 페이징)
    public List<AdminMemberProductDomain> getMemberHistoryList(String userId, String type, int currentPage) {
        List<AdminMemberProductDomain> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        params.put("type", type);
        params.put("start", (currentPage - 1) * 10 + 1);
        params.put("end", currentPage * 10);
        try {
            list = mDAO.selectMemberHistoryList(params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // [13] HISTORY: 거래 내역 전용 페이지네이션 HTML
    public String getHistoryPagination(String userId, String type, int currentPage, int totalCount) {
        int pageScale = 10;
        int totalPage = (int) Math.ceil((double) totalCount / pageScale);
        int pageBlock = 5;
        String query = "&userId=" + userId + "&type=" + type;

        int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
        int endPage = startPage + pageBlock - 1;
        if (endPage > totalPage)
            endPage = totalPage;

        StringBuilder sb = new StringBuilder();
        sb.append("<nav><ul class='pagination justify-content-center'>");
        if (startPage > 1) {
            sb.append(String.format(
                    "<li class='page-item'><a class='page-link' href='?userId=%s&type=%s&currentPage=%d'>&laquo;</a></li>",
                    userId, type, startPage - 1));
        }
        for (int i = startPage; i <= endPage; i++) {
            String active = (i == currentPage) ? "active" : "";
            sb.append(String.format(
                    "<li class='page-item %s'><a class='page-link' href='?userId=%s&type=%s&currentPage=%d'>%d</a></li>",
                    active, userId, type, i, i));
        }
        if (endPage < totalPage) {
            sb.append(String.format(
                    "<li class='page-item'><a class='page-link' href='?userId=%s&type=%s&currentPage=%d'>&raquo;</a></li>",
                    userId, type, endPage + 1));
        }
        sb.append("</ul></nav>");
        return sb.toString();
    }

    public boolean removeMember(String userId) {
        int result = 0;
        try {
            result = mDAO.updateMemberDeleteFlag(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    public boolean modifySuspension(String userId, String action, int days) {
        String flag = action.equals("suspend") ? "Y" : "N";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("flag", flag);
        map.put("days", days);

        int result = 0;
        try {
            result = mDAO.updateSuspension(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }
}