package kr.co.sist.admin.login;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class AdminLoginDAO {
	
	
	
	
	 public AdminLoginDomain selectAdminLogin(String adminId) throws SQLException {
	        SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
	        try {
	            return ss.selectOne("kr.co.sist.admin.login.selectAdminLogin", adminId);
	        } finally {
	            if (ss != null) ss.close();
	        }
	    }//selectAdminLogin
	 
	 public void updateLastLogin(String adminId) throws SQLException {
		    SqlSession ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		    try {
		        ss.update("kr.co.sist.admin.login.updateLastLogin", adminId);
		        ss.commit(); 
		    } finally {
		        if (ss != null) ss.close();
		    }
		}

	

}
