package kr.co.sist.user.findAccount;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class FindAccountDAO {

	public String selectId(FindAccountDTO faDTO) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.findAccount.selectId", faDTO);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectId

	public int selectMember(FindAccountDTO faDTO) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.findAccount.selectMember", faDTO);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectMember

	public int updateMember(FindAccountDTO faDTO) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.update("kr.co.sist.user.findAccount.updateMember", faDTO);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// updateMember

}// class
