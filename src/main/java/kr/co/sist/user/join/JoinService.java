package kr.co.sist.user.join;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.sist.dao.MyBatisHandler;

@Service
public class JoinService {

	@Autowired
	private JoinDAO jDAO;

	@Value("${user.crypto.key}")
	private String key;
	@Value("${user.crypto.salt}")
	private String salt;

	public boolean searchId(JoinDTO jDTO) throws PersistenceException {
		return jDAO.selectId(jDTO) == 0;
	}// searchId

	public boolean searchStoreName(JoinDTO jDTO) throws PersistenceException {
		return jDAO.selectStoreName(jDTO) == 0;
	}// searchStoreName

	public boolean joinMember(JoinDTO jDTO) throws PersistenceException {
		boolean result = false;
		SqlSession ss = null;

		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(false);

			int mResult = jDAO.insertMember(ss, jDTO);
			int sResult = jDAO.insertStore(ss, jDTO);
			int spResult = jDAO.insertSuspension(ss, jDTO);

			if (mResult == 1 && sResult == 1 && spResult == 1) {
				ss.commit();
				result = true;
			} else {
				ss.rollback();
			} // end else

		} finally {
			if (ss != null) {
				ss.close();
			} // end if
		} // end finally

		return result;
	}// join

}// class
