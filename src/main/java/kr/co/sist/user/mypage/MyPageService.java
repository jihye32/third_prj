package kr.co.sist.user.mypage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.user.ProductDomain;
import kr.co.sist.user.sellerpage.SellerPageRangeDTO;

@Service
public class MyPageService {
	
	@Autowired(required = false)
	private MyPageDAO mpDAO;
	
	@Value("${user.profile-dir}")
	private String uploadDir;
	
	public MyPageDomain searchMyPageInfo(int myId) {
		MyPageDomain mpd = null;
		try {
			mpd = mpDAO.selectMyPageInfo(myId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return mpd;
	}// searchMyPageInfo
	
	public List<ProductDomain> searchMySellProdcut(MyPageRangeDTO mprDTO){
		List<ProductDomain> list = null;
//		System.out.println(mprDTO);
		try {
			if(mprDTO.getSearchCode() == 0) {
				list = mpDAO.selectMySellProduct(mprDTO);
			} else if(mprDTO.getSearchCode() == 1) {
				list = mpDAO.selectMyBookmarkProduct(mprDTO);
			}// end else
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return list;
	}// searchAllMySellProdcut
	
	public List<ProductDomain> searchMyBookmarkProdcut(MyPageRangeDTO mprDTO){
		List<ProductDomain> list = null;
		try {
			list = mpDAO.selectMyBookmarkProduct(mprDTO);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return list;
	}// searchMyBookmarkProdcut
	
	public boolean removeCount(String id, int storeNum) {
		boolean flag = false;
		try {
			mpDAO.deleteId(id);
			mpDAO.deleteProduct(storeNum);
			flag = true;
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return flag;
	}// removeCount
	
	public boolean modifyProfile(ProfileDTO pDTO) {
		boolean flag = false;
		
		String fileName = UUID.randomUUID() + "-" + pDTO.getProfileImg().getOriginalFilename();
		File upFile = new File(uploadDir , fileName);
		pDTO.setNewImg(fileName);
		
		String removeImgName = pDTO.getOldImg();
		if(!"default.jpg".equals(removeImgName)) {
			File removeFile = new File(uploadDir , removeImgName);
			removeFile.delete();// 기존 파일 삭제
		}// end if
		
		try {
			pDTO.getProfileImg().transferTo(upFile);// 파일 업로드를 수행
			mpDAO.updateProfile(pDTO);
			flag = true;
		} catch (IllegalStateException ie) {
			ie.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}// end catch
		
		return flag;
	}// modifyProfile
	
	public List<BuyProductDomain> searchBuyProdcut(String myId){
		List<BuyProductDomain> list = null;
		try {
			list = mpDAO.selectBuyProduct(myId);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return list;
	}// searchBuyProdcut
	
	
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
	
	public int totalCnt(MyPageRangeDTO mprDTO) {
		int cnt = 0;
		try {
			if(mprDTO.getSearchCode() == 0) {
				cnt = mpDAO.selectMySellCnt(mprDTO.getMyStoreNum());
			} else if (mprDTO.getSearchCode() == 1) {
				cnt = mpDAO.selectMyBookmarkCnt(mprDTO.getMyStoreNum());
			}// end else
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end cathc
		return cnt;
	}// totalCnt
	
}// class
