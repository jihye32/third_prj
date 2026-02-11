package kr.co.sist.user.login;

import java.time.LocalDateTime;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private LoginDAO lDAO;

	public MemberDomain loginMember(LoginDTO lDTO) throws PersistenceException {
		MemberDomain md = lDAO.selectMember(lDTO.getId());

		// 1. 아이디 유무 체크
		if (md == null) {
			throw new PersistenceException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}// end if

		// 2. 비밀번호 일치 체크
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);
		
		if (!bpe.matches(lDTO.getPass(), md.getUser_pass())) {
			throw new PersistenceException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}// end if

		// 3. 탈퇴 여부 체크
		if ("Y".equals(md.getDelete_flag())) {
			throw new PersistenceException("탈퇴한 회원입니다.");
		}// end if

		// 4. 정지 여부 체크
		SuspensionDomain sd = lDAO.selectSuspension(lDTO.getId());
		
		if ("Y".equals(sd.getFlag())) {
			LocalDateTime now = LocalDateTime.now();
			
			// 5. 정지 종료 여부 체크
			if (sd.getEnd_date().isBefore(now)) {
				lDAO.updateSuspension(lDTO.getId());
			} else {
				throw new PersistenceException("활동 정지된 회원입니다.");
			}// end else
		}// end if

		return md;
	}// loginMember

}// class
