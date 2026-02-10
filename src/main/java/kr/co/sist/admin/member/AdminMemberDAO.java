package kr.co.sist.admin.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class AdminMemberDAO {

    // [1] 회원 목록 페이징용: 전체 회원 수 조회
    public int selectTotalCount(AdminMemberDTO mDTO) throws SQLException {
        int totalCount = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            totalCount = ss.selectOne("kr.co.sist.admin.member.selectTotalCount", mDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return totalCount;
    }

    // [2] 회원 목록 조회
    public List<AdminMemberDomain> selectMemberList(AdminMemberDTO mDTO) throws SQLException {
        List<AdminMemberDomain> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.member.selectMemberList", mDTO);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }
    
    // [3] 회원 기본 인적사항 상세 조회
    public AdminMemberDetailDomain selectMemberDetail(String userId) throws SQLException {
        AdminMemberDetailDomain detail = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            detail = ss.selectOne("kr.co.sist.admin.member.selectMemberDetail", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return detail;
    }
    
    // [4] 상점 프로필 정보 조회
    public AdminMemberPrdvDomain selectStoreDetail(String userId) throws SQLException {
        AdminMemberPrdvDomain store = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            store = ss.selectOne("kr.co.sist.admin.member.selectStoreDetail", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return store;
    }

    // [5] 상점 내 현재 판매 중인 물품 총 개수
    public int selectStoreProductCount(String userId) throws SQLException {
        int count = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            count = ss.selectOne("kr.co.sist.admin.member.selectStoreProductCount", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return count;
    }

    // [6] 상점 내 현재 판매 중인 물품 리스트
    public List<AdminMemberPrdvDomain> selectStoreProducts(Map<String, Object> params) throws SQLException {
        List<AdminMemberPrdvDomain> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.member.selectStoreProducts", params);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }

    // [7] 상점 후기 리스트 조회
    public List<AdminMemberPrdvDomain> selectStoreReviews(String userId) throws SQLException {
        List<AdminMemberPrdvDomain> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.member.selectStoreReviews", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }
    
    // [8] 거래 내역 페이지 헤더용 상점 정보
    public AdminMemberProductDomain selectMemberStoreInfo(String userId) throws SQLException {
        AdminMemberProductDomain domain = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            domain = ss.selectOne("kr.co.sist.admin.member.selectMemberStoreInfo", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return domain;
    }

    // [9] 거래 내역(구매/판매) 총 개수 조회
    public int selectHistoryCount(Map<String, Object> params) throws SQLException {
        int count = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            count = ss.selectOne("kr.co.sist.admin.member.selectHistoryCount", params);
        } finally {
            if (ss != null) ss.close();
        }
        return count;
    }

    // [10] 과거 거래 내역 리스트 조회
    public List<AdminMemberProductDomain> selectMemberHistoryList(Map<String, Object> params) throws SQLException {
        List<AdminMemberProductDomain> list = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            list = ss.selectList("kr.co.sist.admin.member.selectMemberHistoryList", params);
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }

    // [11] 추가: 회원 삭제 플래그 업데이트
    public int updateMemberDeleteFlag(String userId) throws SQLException {
        int result = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        try {
            result = ss.update("kr.co.sist.admin.member.updateMemberDeleteFlag", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return result;
    }
    
    public int updateSuspension(Map<String, Object> map) throws SQLException {
        int result = 0;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
        try {
            result = ss.update("kr.co.sist.admin.member.updateSuspension", map);
        } finally {
            if (ss != null) ss.close();
        }
        return result;
    }
}