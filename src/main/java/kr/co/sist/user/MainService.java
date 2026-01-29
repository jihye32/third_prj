package kr.co.sist.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MainService {
	public List<ProductDomain> selectList(){
		List<ProductDomain> list = new ArrayList<ProductDomain>();
		int cnt =1;
		ProductDomain pd = new ProductDomain("https://img2.joongna.com/media/original/2026/01/18/1768736344461BM3_LaDt3.jpg?impolicy=thumb&amp;size=150", "판매 상태 표시", "찜 많은 상품 제목"+cnt, "거래 지역", null, null, cnt, 1, 10000, null);
		for(int i = 0; i<10; i++) {
			list.add(pd);
		}
		System.out.println("service"+list);
		
		return list;
	}
}// class
