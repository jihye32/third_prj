package kr.co.sist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer : 정적 리소스 매핑, 인터셉터 등록, CORS 설정.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${user.upload-dir}")
	private String uploadDir;
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	/**
	 * HDD의 경로를 web browser에서 인식할 수 있도록 정적 리소스 매핑.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    String dir = uploadDir.endsWith("/") ? uploadDir : uploadDir + "/";
	    registry.addResourceHandler("/images/**")
	            .addResourceLocations("file:" + dir);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(userInterceptor).addPathPatterns("/buy/**","/order/**","/chat/**","/product/**","/store/report/review");
	}
	
	
	
}// class
