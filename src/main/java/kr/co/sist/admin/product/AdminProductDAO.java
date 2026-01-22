package kr.co.sist.admin.product;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import kr.co.sist.dao.MyBatisHandler; // 기존 프로젝트의 핸들러 사용

@Repository
public class AdminProductDAO {

    /**
     * 검색 및 정렬 조건이 포함된 상품 목록 조회
     */
    public List<AdminProductDomain> selectProductList(AdminProductDTO pDTO) throws SQLException {
        List<AdminProductDomain> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.product.selectProductList", pDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }

    /**
     * 페이지네이션을 위한 전체 상품 수 조회
     */
    public int selectTotalCount(AdminProductDTO pDTO) throws SQLException {
        int totalCount = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            totalCount = ss.selectOne("kr.co.sist.admin.product.selectTotalCount", pDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return totalCount;
    }

    /**
     * 상품 상세 정보 조회 (이미지 제외 정보만)
     */
    public AdminProductDomain selectProductDetail(int productNo) throws SQLException {
        AdminProductDomain pd = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            pd = ss.selectOne("kr.co.sist.admin.product.selectProductDetail", productNo);
        } finally {
            if (ss != null) ss.close();
        }
        return pd;
    }

    /**
     * 해당 상품의 이미지 목록만 별도로 조회
     */
    public List<String> selectProductImages(int productNo) throws SQLException {
        List<String> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.product.selectProductImages", productNo);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }

    /**
     * 상품 상태 변경 (판매중, 예약중, 판매완료 등)
     */
    public int updateState(AdminProductDTO pDTO) throws SQLException {
        int rowCnt = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true); // Auto Commit
        try {
            rowCnt = ss.update("kr.co.sist.admin.product.updateProductState", pDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return rowCnt;
    }

    /**
     * 상품 삭제 (관리자 강제 삭제 및 사유 기록)
     */
    public int deleteProduct(AdminProductDTO pDTO) throws SQLException {
        int rowCnt = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        try {
            rowCnt = ss.update("kr.co.sist.admin.product.deleteProduct", pDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return rowCnt;
    }
}