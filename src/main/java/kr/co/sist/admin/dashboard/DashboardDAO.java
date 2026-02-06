package kr.co.sist.admin.dashboard;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class DashboardDAO {

    public Map<String, Object> selectJoinStats() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectOne("kr.co.sist.admin.dashboard.selectJoinStats");
        } finally {
            ss.close();
        }
    }

    public Map<String, Object> selectVisitStats() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectOne("kr.co.sist.admin.dashboard.selectVisitStats");
        } finally {
            ss.close();
        }
    }

    public Map<String, Object> selectSoldStats() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectOne("kr.co.sist.admin.dashboard.selectSoldStats");
        } finally {
            ss.close();
        }
    }

    public Map<String, Object> selectAgeStats() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectOne("kr.co.sist.admin.dashboard.selectAgeStats");
        } finally {
            ss.close();
        }
    }

    public List<Map<String, Object>> selectUpload() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectList("kr.co.sist.admin.dashboard.selectUpload");
        } finally {
            ss.close();
        }
    }

    public List<Map<String, Object>> selectSoldItem() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectList("kr.co.sist.admin.dashboard.selectSoldItem");
        } finally {
            ss.close();
        }
    }
    
    public List<Map<String, Object>> selectBestseller() {
        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
        try {
            return ss.selectList("kr.co.sist.admin.dashboard.selectBestseller");
        } finally {
            ss.close();
        }
    }
}