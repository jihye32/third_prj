package kr.co.sist.admin.dashboard;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    @Autowired
    DashboardDAO dDAO;
    
    // 1. 회원가입 통계 (오늘/이번달/이번해 한꺼번에)
    public Map<String, Object> getJoinStats() {
        return dDAO.selectJoinStats();
    }
    
    // 2. 방문 통계 (오늘/이번달/이번해 한꺼번에)
    public Map<String, Object> getVisitStats() {
        return dDAO.selectVisitStats();
    }
    
    // 3. 판매 통계 (오늘/이번달/이번해 한꺼번에)
    public Map<String, Object> getSoldStats() {
        return dDAO.selectSoldStats();
    }
    
    // 4. 연령대 통계 (10대~60대 한꺼번에)
    public Map<String, Object> getAgeStats() {
        return dDAO.selectAgeStats();
    }
    
    // 5. 그래프 및 리스트 데이터 (기존 유지)
    public List<Map<String, Object>> getUploadStats() {
        return dDAO.selectUpload();
    }
    
    public List<Map<String, Object>> getSoldItemStats() {
        return dDAO.selectSoldItem();
    }

    public List<Map<String, Object>> getBestsellers() {
        return dDAO.selectBestseller();
    }
}