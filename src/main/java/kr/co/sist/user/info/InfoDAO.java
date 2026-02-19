package kr.co.sist.user.info;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.co.sist.dao.MyBatisHandler;

@Repository
public class InfoDAO {

	public MemberDomain selectMember(String id) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.info.selectMember", id);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectMember

	public int selectStoreName(String storeName) throws PersistenceException {
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(true);
			return ss.selectOne("kr.co.sist.user.info.selectStoreName", storeName);
		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally
	}// selectStoreName

	public int updateMember(SqlSession ss, InfoDTO iDTO) throws PersistenceException {
		return ss.update("kr.co.sist.user.info.updateMember", iDTO);
	}// updateMember

	public int updateStore(SqlSession ss, InfoDTO iDTO) throws PersistenceException {
		return ss.update("kr.co.sist.user.info.updateStore", iDTO);
	}// updateStore

}// class
