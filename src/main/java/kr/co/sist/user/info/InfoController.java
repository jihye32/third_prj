package kr.co.sist.user.info;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/info")
@Controller
public class InfoController {

	@Autowired
	private InfoService is;

	@GetMapping("/passChkFrm")
	public String passChkFrm(HttpSession session) {
		if(session.getAttribute("uid") != null) {
			return "info/passChkFrm";
		} else {
			return "redirect:/user/login/loginFrm";
		}// end else
	}// passChkFrm

	@PostMapping("/passChkProcess")
	public String passChkProcess(InfoDTO iDTO, HttpSession session, Model model, RedirectAttributes ra) {
		try {
			iDTO.setId((String) session.getAttribute("uid"));
			MemberDomain md = is.passChk(iDTO);

			session.setAttribute("isVerified", true);
			model.addAttribute("md", md);

			return "info/infoFrm";
		} catch (PersistenceException pe) {
			ra.addFlashAttribute("passChkFlag", "fail");
			ra.addFlashAttribute("errorMsg", pe.getMessage());

			return "redirect:/info/passChkFrm";
		} // end catch
	}// passChkProcess

	@GetMapping("/infoFrm")
	public String infoFrm(HttpSession session, Model model, RedirectAttributes ra) {
		Boolean isVerified = (Boolean) session.getAttribute("isVerified");
		
		if (isVerified == null || !isVerified) {
			return "redirect:/info/passChkFrm";
		} // end if

		try {
			MemberDomain md = is.searchMember(session.getAttribute("uid").toString());
			model.addAttribute("md", md);
		} catch (PersistenceException pe) {
			ra.addFlashAttribute("passChkFlag", "fail");
			ra.addFlashAttribute("errorMsg", pe.getMessage());

			return "redirect:/info/passChkFrm";
		} // end catch

		return "info/infoFrm";
	}// infoFrm

	@ResponseBody
	@GetMapping("/storeNameDup")
	public boolean storeNameDup(@RequestParam String storeName) {
		try {
			return is.searchStoreName(storeName);
		} catch (PersistenceException pe) {
			pe.printStackTrace();

			return false;
		} // end catch
	}// storeNameDup

	@ResponseBody
	@GetMapping("/sendVerifyCode")
	public boolean sendVerifyCode(@RequestParam String emailLocal, @RequestParam String emailDomain,
			HttpSession session) {
		String email = emailLocal + emailDomain;
		String verifyCode = String.valueOf((int) (Math.random() * 900000) + 100000);

		session.setAttribute("verifyCode", verifyCode);
		session.setAttribute("verifyEmail", email);

		try {
			return is.sendAuthEmail(email, verifyCode);
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		} // end catch
	}// sendVerifyCode

	@ResponseBody
	@PostMapping("/verifyCodeChk")
	public boolean verifyCodeChk(@RequestParam String inputCode, HttpSession session) {
		String sessionCode = (String) session.getAttribute("verifyCode");

		if (sessionCode == null) {
			return false; // 세션 만료
		} // end if

		return sessionCode.equals(inputCode);
	}// verifyCodeChk

	@PostMapping("/changeInfoProcess")
	public String changeInfoProcess(InfoDTO iDTO, HttpSession session, RedirectAttributes ra) {
		iDTO.setId((String) session.getAttribute("uid"));
		boolean result = false;

		try {
			result = is.changeInfo(iDTO);
		} catch (Exception e) {
			e.printStackTrace();
		} // end catch

		if (result) {
			return "redirect:/info/infoFrm";
		} else {
			ra.addFlashAttribute("changeInfoFlag", "fail");

			return "redirect:/info/infoFrm";
		} // end else
	}// changeInfoProcess

}// class
