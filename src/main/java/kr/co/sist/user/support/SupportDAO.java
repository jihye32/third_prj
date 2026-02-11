package kr.co.sist.user.support;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class SupportDAO {

  // 목록 (공지/FAQ) 
    public List<SupportDomain> selectSupportList(SupportDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectList("kr.co.sist.user.support.SupportMapper.selectSupportList", dto);
        } finally {
            if (ss != null) ss.close();
        }
    }

 // 상세 (공지/FAQ)
    public SupportDomain selectSupportDetail(SupportDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectOne("kr.co.sist.user.support.SupportMapper.selectSupportDetail", dto);
        } finally {
            if (ss != null) ss.close();
        }
    }
     // ===== Ask(1:1문의) =====

    public List<UserAskDomain> selectMyAskList(String userId, String keyword) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            
            // 검색 조건 Map
            java.util.Map<String, Object> paramMap = new java.util.HashMap<>();
            paramMap.put("userId", userId);
            paramMap.put("keyword", keyword);
            
            return ss.selectList("kr.co.sist.user.support.SupportMapper.selectMyAskList", paramMap);
        } finally {
            if (ss != null) ss.close();
        }
    }

    public UserAskDomain selectMyAskDetail(String userId, int askNum) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectOne("kr.co.sist.user.support.SupportMapper.selectMyAskDetail",
                    Map.of("userId", userId, "askNum", askNum));
        } finally {
            if (ss != null) ss.close();
        }
    }

    public List<String> selectAskImgList(int askNum) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
            return ss.selectList("kr.co.sist.user.support.SupportMapper.selectAskImgList", askNum);
        } finally {
            if (ss != null) ss.close();
        }
    }

    public void insertAsk(UserAskDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            ss.insert("kr.co.sist.user.support.SupportMapper.insertAsk", dto); // dto.askNum 채워짐(selectKey)
        } finally {
            if (ss != null) ss.close();
        }
    }

    public void insertAskImg(int askNum, String askImg) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            ss.insert("kr.co.sist.user.support.SupportMapper.insertAskImg",
                    Map.of("askNum", askNum, "askImg", askImg));
        } finally {
            if (ss != null) ss.close();
        }
    }
    
    public void updateAsk(UserAskDTO dto) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            ss.update("kr.co.sist.user.support.SupportMapper.updateAsk", dto);
        } finally {
            if (ss != null) ss.close();
        }
    }
    public void deleteAskImg(int askNum) throws SQLException {
        SqlSession ss = null;
        try {
            ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
            ss.delete("kr.co.sist.user.support.SupportMapper.deleteAskImg", askNum);
        } finally {
            if (ss != null) ss.close();
        }
    }
}