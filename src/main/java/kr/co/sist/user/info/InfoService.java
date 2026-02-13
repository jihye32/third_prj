package kr.co.sist.user.info;

import java.security.MessageDigest;

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
public class InfoService {

	@Autowired
	private InfoDAO iDAO;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Value("${user.crypto.key}")
	private String key;
	@Value("${user.crypto.salt}")
	private String salt;

	public MemberDomain passChk(InfoDTO iDTO) throws PersistenceException {
		MemberDomain md = iDAO.selectMember(iDTO.getId());
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);

		if (!bpe.matches(iDTO.getPass(), md.getUser_pass())) {
			throw new PersistenceException("비밀번호가 일치하지 않습니다.");
		} // end if
		
		TextEncryptor te = Encryptors.text(key, salt);
		md.setName(te.decrypt(md.getName()));
		md.setEmail(te.decrypt(md.getEmail()));

		return md;
	}// passChk
	
	public MemberDomain searchMember(String id) {
		return iDAO.selectMember(id);
	}// searchMember

	public boolean searchStoreName(String storeName) throws PersistenceException {
		return iDAO.selectStoreName(storeName) == 0;
	}// searchStoreName

	public boolean sendAuthEmail(String email, String verifyCode) throws Exception {
		boolean result = false;
		MimeMessage message = mailSender.createMimeMessage();

		// UTF-8 설정
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom(senderEmail, "중고나라 이메일 인증");
		helper.setTo(email);
		helper.setSubject("회원가입 인증 번호");

		StringBuilder body = new StringBuilder();
		body.append(
				"<div style='margin:20px; padding:40px; border:1px solid #E7FAEF; border-radius:40px; text-align:center; font-family:sans-serif;'>");
		body.append("<h2 style='color:#222222; font-size:24px; font-weight:bold;'>인증번호</h2>");
		body.append("<p style='color:#888888; font-size:16px; margin-top:20px;'>아래 번호를 입력하시고 <br>인증하기 버튼을 눌러주세요.</p>");
		body.append(
				"<div style='margin:40px auto; padding:20px; background-color:#F5F5F5; border-radius:20px; width:180px;'>");
		body.append("<span style='font-size:32px; font-weight:bold; color:#0DCC5A; letter-spacing:5px;'>")
				.append(verifyCode).append("</span>");
		body.append("</div>");
		body.append("<br>");
		body.append("</div>");

		helper.setText(body.toString(), true);

		mailSender.send(message);
		result = true;

		return result;
	}// sendAuthEmail

	private String getSha256(String source) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] bytes = md.digest(source.getBytes());

		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		} // end for

		return sb.toString();
	}// getSha256

	public boolean changeInfo(InfoDTO iDTO) throws Exception {
		boolean result = false;
		SqlSession ss = null;

		// 비밀번호 : 일방향 암호화
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);
		iDTO.setPass(bpe.encode(iDTO.getPass()));
		
		// 이메일 :
		String rawEmail = iDTO.getEmailLocal() + iDTO.getEmailDomain();
		
		// 양방향 암호화
		TextEncryptor te = Encryptors.text(key, salt);
		iDTO.setEmail(te.encrypt(rawEmail));
		
		// 아이디/비밀번호 찾기용 해시
		iDTO.setEmailHash(getSha256(rawEmail));
		
		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(false);

			int mResult = iDAO.updateMember(ss, iDTO);
			int sResult = iDAO.updateStore(ss, iDTO);

			if (mResult == 1 && sResult == 1) {
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
	}// changeInfo

}// class
