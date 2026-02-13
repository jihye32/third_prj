package kr.co.sist.user.findAccount;

import java.security.MessageDigest;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FindAccountService {

	@Autowired
	private FindAccountDAO faDAO;

	public String generateTempPass() {
		char prefix = (char) ((Math.random() * 26) + 'A');
		String digits = String.format("%07d", (int) (Math.random() * 10000000));

		return prefix + digits;
	}// generateTempPass

	private String getSha256(String source) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] bytes = md.digest(source.getBytes());

		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		} // end for

		return sb.toString();
	}// getSha256

	public String searchId(FindAccountDTO faDTO) throws Exception {
		String rawName = faDTO.getName();
		String rawEmail = faDTO.getEmail();

		faDTO.setNameHash(getSha256(rawName));
		faDTO.setEmailHash(getSha256(rawEmail));

		return faDAO.selectId(faDTO);
	}// searchId

	public boolean searchMember(FindAccountDTO faDTO) throws Exception {
		String rawName = faDTO.getName();
		String rawEmail = faDTO.getEmail();

		faDTO.setNameHash(getSha256(rawName));
		faDTO.setEmailHash(getSha256(rawEmail));

		return faDAO.selectMember(faDTO) == 1;
	}// searchMember

	public boolean modifyMember(FindAccountDTO faDTO) throws PersistenceException {
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder(10);
		faDTO.setTempPass(bpe.encode(faDTO.getTempPass()));

		return faDAO.updateMember(faDTO) == 1;
	}// modifyMember

}// class
