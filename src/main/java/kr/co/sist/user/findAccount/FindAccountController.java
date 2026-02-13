package kr.co.sist.user.findAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/findAccount")
@Controller
public class FindAccountController {

	@Autowired
	private FindAccountService fas;

	@GetMapping("/findAccountFrm")
	public String findAccountFrm() {
		return "/findAccount/findAccountFrm";
	}// findAccountFrm

	@GetMapping("/findIdProcess")
	public String findIdProcess(FindAccountDTO faDTO, Model model) {
		try {
			int flag = 1;
			String id = fas.searchId(faDTO);

			if (id == null || id.isEmpty()) {
				model.addAttribute("errorMsg", "일치하는 회원정보가 없습니다.");

				return "/findAccount/findAccountFrm";
			} // end if

			model.addAttribute("name", faDTO.getName());
			model.addAttribute("flag", flag);
			model.addAttribute("result", id);

			return "/findAccount/findAccountResult";
		} catch (Exception e) {
			e.printStackTrace();

			model.addAttribute("errorMsg", "아이디 찾기 중 오류가 발생하였습니다.");

			return "/findAccount/findAccountFrm";
		} // end catch
	}// findIdProcess

	@PostMapping("/findPassProcess")
	public String findPassProcess(FindAccountDTO faDTO, RedirectAttributes ra) {
		try {
			if (!fas.searchMember(faDTO)) {
				ra.addFlashAttribute("errorMsg", "일치하는 회원정보가 없습니다.");

				return "redirect:/findAccount/findAccountFrm";
			} // end if

			String tempPass = fas.generateTempPass();
			faDTO.setTempPass(tempPass);

			if (fas.modifyMember(faDTO)) {
				ra.addFlashAttribute("name", faDTO.getName());
				ra.addFlashAttribute("flag", 2);
				ra.addFlashAttribute("result", tempPass);

				return "redirect:/findAccount/findAccountResult";
			} else {
				throw new Exception();
			} // end else

		} catch (Exception e) {
			e.printStackTrace();

			ra.addFlashAttribute("errorMsg", "임시 비밀번호 발급 중 오류가 발생했습니다.");

			return "redirect:/findAccount/findAccountFrm";
		} // end catch
	}// findPassProcess
	
	@GetMapping("/findAccountResult")
	public String findAccountResult() {
		return "/findAccount/findAccountResult";
	}// findAccountResult

}// class
