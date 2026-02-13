package kr.co.sist.user.sell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sist.dao.MyBatisHandler;

@Service
public class SellService {
	
	@Autowired(required = false)
	private SellDAO sDAO;
	
	@Value("${user.thumbnail-dir}")
	private String thumbnailUploadDir;
	@Value("${user.product_detail-dir}")
	private String productDetailUploadDir;
	
	public int searchProductNum() {
		int productNum = 0;
		try {
			productNum = sDAO.selectProductNum();
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		return productNum;
	}// searchProductNum
	
	
	public boolean addProdcut(SellDTO sDTO) {
		boolean flag = false;
		int queryCnt = 0;
		int standardCnt = 0;
		String thumbnailName = UUID.randomUUID() + "-" + sDTO.getThumbnail().getOriginalFilename();
		File upFile = new File(thumbnailUploadDir , thumbnailName);
		sDTO.setThumbnailName(thumbnailName);// 썸네일 이미지명 DTO에 저장
		
		File tempUpFile = null;
		String prodcutDetailImg = "";
		try {
			sDTO.getThumbnail().transferTo(upFile);
			
			MultipartFile[] tempArr = sDTO.getProductImages();
			if(tempArr != null) {
				for(MultipartFile mf : sDTO.getProductImages()) {
					prodcutDetailImg = UUID.randomUUID() + "-" + mf.getOriginalFilename();
					sDTO.getProductImgName().add(prodcutDetailImg);// 세부 이미지명 DTO에 저장
					tempUpFile = new File(productDetailUploadDir , prodcutDetailImg);
					mf.transferTo(tempUpFile);
					prodcutDetailImg = "";
				}// end for
			}// end if
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end catch
		SqlSession ss = null;
		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
			queryCnt += sDAO.insertProduct(sDTO, ss);
			standardCnt++;
			
			
			Map<String, Object> map = null ;
			for(String imgName : sDTO.getProductImgName()) {// 이미지명 DB에 저장
				map = new HashMap<String, Object>();
				map.put("imageName", imgName);
				map.put("productNum", sDTO.getProductNum());
				queryCnt += sDAO.insertImages(map, ss);
				standardCnt++;
			}// end for
			
			for(String tradeCode : sDTO.getTradeType()) {
				map = new HashMap<String, Object>();
				map.put("sellCode", tradeCode);
				map.put("productNum", sDTO.getProductNum());
				queryCnt += sDAO.insertTradeType(map, ss);
				standardCnt++;
			}// end for
			if(standardCnt == queryCnt) {// 정상 시행인 경우
				flag = true;
				ss.commit();
			}else {
				ss.rollback();
			}// end else
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} finally {
			if(ss != null) {ss.close();}// end if
		}// end finally
		
		return flag;
	}// addProdcut
	
}// class
