package kr.co.sist.user.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class UserReportController {
	
	@Autowired
	UserReportService urs;
	
	
	@ResponseBody
	@PostMapping("/report/review_process")
	public String reportProcess(
	        UserReportDTO urDTO, 
	        @RequestParam("reviewerId")String reviewerId,
	        @RequestParam("upFile") List<MultipartFile> files, //
	        HttpSession session) throws IOException {

		
	    //세션에서 내 아이디(신고자) 세팅
		if (session.getAttribute("uid") == null) {
	        session.setAttribute("uid", "user01"); 
	    }
		
	    String reporterId = (String) session.getAttribute("uid");
	    urDTO.setReporter_id(reporterId);
	    urDTO.setReportee_id(reviewerId);
	    
	    List<String> savedFileNames = uploadFiles(files);

	    System.out.println("=== 데이터 검증 시작 ===");
		System.out.println("세션에서 뽑은 ID: " + reporterId);
		System.out.println("DTO에 저장된 신고자 ID: " + urDTO.getReporter_id());
		System.out.println("DTO에 저장된 피신고자 ID: " + urDTO.getReportee_id()); // 이게 null이면 400이나 500에러 유발
		System.out.println("=== 데이터 검증 끝 ===");
		
	    
	    
	    urs.addCompleteReport(urDTO, savedFileNames);

	    return "<script>" +
	       "alert('신고가 접수되었습니다.');" +
	       "parent.location.replace(document.referrer);" +
	       "</script>";



    }
	
	
	
	
	@GetMapping("/report/review") 
	public String getReportForm(@RequestParam("reviewerId") String reviewerId, Model model) {
	    model.addAttribute("reviewerId", reviewerId);
	    return "report/wrapper_Report :: fragmentReport"; // 
	}
	
	
//	    @PostMapping("/report/wrapper_reportDone") // 
//	    public String scamPage4(Model model) {
//	    	model.addAttribute("viewMode","done");
//	    	// templates/manage/login/login.html 파일을 찾아가게 함
//	    	return "/report/wrapper_report :: fragmentScamComplete";
//	    }
	
	
	private List<String> uploadFiles(List<MultipartFile> files) throws IOException {
	    List<String> savedFileNames = new ArrayList<>();
	    
	    String fullPath = "C:/dev/workspace/third_prj/src/main/resources/static/images/report/";

	    for (MultipartFile mf : files) {
	        if (mf != null && !mf.isEmpty()) {
	            String fileName = UUID.randomUUID() + "-" + mf.getOriginalFilename();
	            
	            File upFile = new File(fullPath + fileName);
	            mf.transferTo(upFile);
	            
	            savedFileNames.add(fileName); 
	        }
	    }
	    return savedFileNames;
	}

}
