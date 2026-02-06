package kr.co.sist.admin.dashboard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class DashboardDAO {
	
	
	public List<Map<String, Object>> selectUpload(){
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		return ss.selectList("kr.co.sist.admin.dashboard.selectUpload");
	}//selectUpload
	
	public List<Map<String, Object>> selectSoldItem(){
		SqlSession ss=MyBatisHandler.getInstance().getMyBatisHandler(false);
		return ss.selectList("kr.co.sist.admin.dashboard.selectSoldItem");
	}//selectSoldItem

}
