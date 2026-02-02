package kr.co.sist.user.productdetail;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class productAspect {

	@AfterReturning(pointcut="execution(kr.co.sist.user.productdetail.ProductDetailDomain kr.co.sist.user.productdetail.ProductDetailService.searchProduct(int))", returning="pdd")
	public void afterReturning(ProductDetailDomain pdd) {
		pdd.setTimeAgo(TimeAgo.format(pdd.getBumpDate()));
	}
}
