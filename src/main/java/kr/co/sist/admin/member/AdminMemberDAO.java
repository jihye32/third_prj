package kr.co.sist.admin.member;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import kr.co.sist.dao.MyBatisHandler; // 본인 프로젝트의 핸들러 경로 확인

@Repository
public class AdminMemberDAO {

    // 1. 전체 회원 수 조회
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

    // 2. 회원 목록 조회
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
    
    public AdminMemberDetailDomain selectMemberDetail(String userId) throws SQLException {
        AdminMemberDetailDomain detail = null;
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            // 매퍼 ID는 kr.co.sist.admin.member.selectMemberDetail로 설정할 예정입니다.
            detail = ss.selectOne("kr.co.sist.admin.member.selectMemberDetail", userId);
        } finally {
            if (ss != null) ss.close();
        }
        return detail;
    }
}