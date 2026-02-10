package kr.co.sist.user.sellerpage;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.ProductDomain;

@Service
public class SellerPageService {

	@Autowired(required = false)
	private SellerPageDAO spDAO;
	
	public SellerPageDomain searchSeller(int storeNum) {
		SellerPageDomain spd = null;
		try {
			spd = spDAO.selectSeller(storeNum);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return spd;
	}// searchSeller
	
	public List<ProductDomain> searchProductList(SellerPageRangeDTO sprDTO) {
		List<ProductDomain> list = null;
		try {
			list = spDAO.selectProductList(sprDTO);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return list;
	}// searchProductList
	
	/**
	 * 한 화면에 보여줄 게시글의 수
	 * 
	 * @return
	 */
	public int pageScale() {
		return 10;
	}// pageScale

	
	public int startNum(int page) {
		return (page-1) * pageScale()+1;
	}

	/**
	 * 페이지의 끝 번호 구하기
	 * 
	 * @param page  - 페이지 수
	 * @param pageScale - 페이지당 보여줄 게시물 수
	 * @return
	 */
	public int endNum(int page, int totalCnt) {
		return page * pageScale() ;
	}// endNum
	
	public int totalCnt(SellerPageRangeDTO sprDTO) {
		int cnt = 0;
		try {
			cnt = spDAO.selectProductListCnt(sprDTO);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return cnt;
	}// totalCnt
	
	public List<ProductDomain> findSelling(List<ProductDomain> bigList){
		List<ProductDomain> resultList = new ArrayList<ProductDomain>();
		for(ProductDomain pd : bigList) {
			if(pd.getSellStateCode() == 1) {// 판매중
				resultList.add(pd);
			}// end if
		}// end for
		return resultList;
	}// findSelling
	
	public List<ProductDomain> findReserve(List<ProductDomain> bigList){
		List<ProductDomain> resultList = new ArrayList<ProductDomain>();
		for(ProductDomain pd : bigList) {
			if(pd.getSellStateCode() == 2) {// 예약중
				resultList.add(pd);
			}// end if
		}// end for
		return resultList;
	}// findSelling
	
	public List<ProductDomain> findSelled(List<ProductDomain> bigList){
		List<ProductDomain> resultList = new ArrayList<ProductDomain>();
		for(ProductDomain pd : bigList) {
			if(pd.getSellStateCode() == 3) {// 판매완료
				resultList.add(pd);
			}// end if
		}// end for
		return resultList;
	}// findSelling
	
	
}// class