package kr.co.sist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer : 정적 리소스 매핑, 인터셉터 등록, CORS 설정.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${user.upload-dir}")
	private String uploadDir;
	
	/**
	 * HDD의 경로를 web browser에서 인식할 수 있도록 정적 리소스 매핑.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload/**")
		.addResourceLocations("file:///"+uploadDir);
	}// addResourceHandlers
	
}// class
