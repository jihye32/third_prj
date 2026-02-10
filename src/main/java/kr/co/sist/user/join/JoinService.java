package kr.co.sist.user.join;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import kr.co.sist.dao.MyBatisHandler;

@Service
public class JoinService {

	@Autowired
	private JoinDAO jDAO;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String senderEmail;

	@Value("${user.crypto.key}")
	private String key;
	@Value("${user.crypto.salt}")
	private String salt;

	public boolean searchId(String id) throws PersistenceException {
		return jDAO.selectId(id) == 0;
	}// searchId

	public boolean searchStoreName(String storeName) throws PersistenceException {
		return jDAO.selectStoreName(storeName) == 0;
	}// searchStoreName
	
	public boolean sendAuthEmail(String email, String verifyCode) {
		boolean result = false;
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // UTF-8 설정
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(senderEmail, "중고나라 이메일 인증");
            helper.setTo(email);
            helper.setSubject("회원가입 인증 번호");

            StringBuilder body = new StringBuilder();
            body.append("<div style='margin:20px; padding:40px; border:1px solid #E7FAEF; border-radius:40px; text-align:center; font-family:sans-serif;'>");
            body.append("<h2 style='color:#222222; font-size:24px; font-weight:bold;'>인증번호</h2>");
            body.append("<p style='color:#888888; font-size:16px; margin-top:20px;'>아래 인증번호를 입력하시고<br>인증하기 버튼을 눌러주세요.</p>");
            body.append("<div style='margin:40px auto; padding:20px; background-color:#F5F5F5; border-radius:20px; width:180px;'>");
            body.append("<span style='font-size:32px; font-weight:bold; color:#0DCC5A; letter-spacing:5px;'>").append(verifyCode).append("</span>");
            body.append("</div>");
            body.append("<br>");
            body.append("</div>");

            helper.setText(body.toString(), true);

            mailSender.send(message);
            result = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }// end catch

        return result;
	}// sendAuthEmail

	public boolean joinMember(JoinDTO jDTO) throws PersistenceException {
		boolean result = false;
		SqlSession ss = null;

		// 비밀번호 : 일방향 해시
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);
		jDTO.setPass(bpe.encode(jDTO.getPass()));

		// 이름, 이메일 : 양방향
		TextEncryptor te = Encryptors.text(key, salt);
		jDTO.setUserName(te.encrypt(jDTO.getUserName()));

		String email = jDTO.getEmailLocal() + jDTO.getEmailDomain();
		jDTO.setEmail(te.encrypt(email));

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
