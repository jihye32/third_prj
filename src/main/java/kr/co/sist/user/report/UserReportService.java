package kr.co.sist.user.report;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.dao.MyBatisHandler;

@Service
public class UserReportService {
	
	@Autowired
	UserReportDAO urDAO;
	
	public void addCompleteReport(UserReportDTO urDTO, List<String> fileList) {
	    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	    
	    try {
	        // 2. 신고글 저장 (selectKey로 인해 urDTO에 report_num이 들어옴)
	        urDAO.insertReviewReport(ss, urDTO);
	        
	        // 3. 사진 저장 (서비스에서 for문을 돌림)
	        for (String fileName : fileList) {
	            urDTO.setReviewImgFilename(fileName); // 파일명 세팅
	            urDAO.insertReportImg(ss, urDTO);     // 같은 report_num으로 여러 번 insert
	        }
	        
	        ss.commit(); // 모든 과정 성공 시 커밋
	    } catch (Exception e) {
	        ss.rollback(); // 하나라도 실패하면 싹 다 취소
	        e.printStackTrace();
	    } finally {
	        ss.close();
	    }
	}

}
