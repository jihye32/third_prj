package kr.co.sist.admin.login;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService {
	
	@Autowired
	AdminLoginDAO adminDAO;
	
	public boolean login(AdminLoginDTO ldto) throws SQLException {
	    AdminLoginDomain adminDomain =
	        adminDAO.selectAdminLogin(ldto.getAdminId());

	    if (adminDomain == null) {
	        return false;
	    }
	    BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);

	    boolean ok = bpe.matches(ldto.getAdminPass(), adminDomain.getAdminPass().trim());
	    if (ok) {//로그인 성공하면 로그인 최신일자 업데이트
	        adminDAO.updateLastLogin(ldto.getAdminId());
	    }

	    return ok;
	}
	
	


}