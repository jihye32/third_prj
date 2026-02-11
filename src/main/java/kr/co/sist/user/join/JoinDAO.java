package kr.co.sist.user.join;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class JoinDAO {

	public int selectId(String id) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.join.selectId", id);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectId

	public int selectStoreName(String storeName) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.join.selectStoreName", storeName);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectStoreName

	public int insertMember(SqlSession ss, JoinDTO jDTO) throws PersistenceException {
		return ss.insert("kr.co.sist.user.join.insertMember", jDTO);
	}// insertMember

	public int insertStore(SqlSession ss, JoinDTO jDTO) throws PersistenceException {
		return ss.insert("kr.co.sist.user.join.insertStore", jDTO);
	}// insertStore

	public int insertSuspension(SqlSession ss, JoinDTO jDTO) throws PersistenceException {
		return ss.insert("kr.co.sist.user.join.insertSuspension", jDTO);
	}// insertSuspension

}// class
