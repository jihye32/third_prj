package kr.co.sist.user.mypage;

import java.util.List;

import kr.co.sist.user.ProductDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSearchDTO {
	private List<ProductDomain> list;
	private boolean hasNext;
	private int cnt;
}
