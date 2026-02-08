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
	
	public List<ProductDomain> searchAllProduct(SellerPageRangeDTO sprDTO) {
		List<ProductDomain> list = null;
		try {
			list = spDAO.selectAllProduct(sprDTO);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return list;
	}// searchAllProduct
	
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
