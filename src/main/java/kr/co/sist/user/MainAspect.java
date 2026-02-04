package kr.co.sist.user;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MainAspect {

	@AfterReturning(
			pointcut = 
	        "execution(* kr.co.sist.user.MainService.searchMostViewProdcut(..)) || " +
	                "execution(* kr.co.sist.user.productlist.ProductListService.searchProductList(..))",
			returning = "result")
	public void afterReturning2(JoinPoint jp, Object result) {
		List<ProductDomain> list = (List<ProductDomain>) result;
//		System.out.println("반환형이 있는 method가 호출" + jp.getSignature());
//		System.out.println("Aspect 반환값 : " + list);
		
		for(ProductDomain pd : list) {
			pd.setTradingArea(cutArea( pd.getTradingArea() ));
			pd.setTimeString(formatDate(pd.getTimDif(), pd.getLastModify()));
		}// end for
	}// afterReturning2
	
	/**
	 * 풀 주소를 동만 남기고 자르는 method
	 * @param area
	 * @return
	 */
	public String cutArea(String area) {
		String cutArea = "";
		cutArea = area.substring(area.lastIndexOf(" "), area.length());
		return cutArea;
	}// cutArea
	 
	public static String formatDate(double timeDifference, Date lastModify) {
		// 1일 = 86400초
        long totalSeconds = Math.round(timeDifference * 86400);
        
        // 일, 시간, 분, 초로 변환
        long d = totalSeconds / 86400; // 일
        totalSeconds %= 86400;
        
        long h = totalSeconds / 3600;  // 시간
        totalSeconds %= 3600;
        
        long m = totalSeconds / 60;    // 분
        long s = totalSeconds % 60;    // 초
        // 출력 형식
        if (d > 30) {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(lastModify).toString();
        } else if(d > 0) {
        	return d + "일 전";
        } else if (h > 0) {
            return h + "시간 전";
        } else if (m > 0) {
            return m + "분 전";
        } else {
            return s + "초 전";
        }
    }// formatDate

	
}// class
