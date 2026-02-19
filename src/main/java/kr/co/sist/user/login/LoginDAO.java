package kr.co.sist.user.login;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class LoginDAO {

	public MemberDomain selectMember(String id) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.login.selectMember", id);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectMember

	public SuspensionDomain selectSuspension(String id) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.login.selectSuspension", id);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectSuspension
	
	public int updateSuspension(String id) throws PersistenceException {
		SqlSession ss = null;
		
		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.update("kr.co.sist.user.login.updateSuspension", id);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// updateSuspension

}// class
