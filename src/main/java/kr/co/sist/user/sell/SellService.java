package kr.co.sist.user.sell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
	
	
	public boolean addProduct(SellDTO sDTO) {
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
			
			// 상품 세부 이미지 db에 insert
			Map<String, Object> map = null ;
			for(String imgName : sDTO.getProductImgName()) {// 이미지명 DB에 저장
				map = new HashMap<String, Object>();
				map.put("imageName", imgName);
				map.put("productNum", sDTO.getProductNum());
				queryCnt += sDAO.insertImages(map, ss);
				standardCnt++;
			}// end for
			
			// 거래 유형 db에 insert
			for(String tradeCode : sDTO.getTradeType()) {
				map = new HashMap<String, Object>();
				map.put("sellCode", tradeCode);
				map.put("productNum", sDTO.getProductNum());
				queryCnt += sDAO.insertTradeType(map, ss);
				standardCnt++;
			}// end for
			
			// 거래 지역 db에 insert
			map = new HashMap<String, Object>();
			map.put("tradeArea", sDTO.getAddr());
			map.put("productNum", sDTO.getProductNum());
			queryCnt += sDAO.insertTradeArea(map, ss);
			standardCnt++;
			
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
	
	public SellDomain searchModifyProdcut(SellDTO sDTO) {
		SellDomain sDomain = null;
		try {
			sDomain = sDAO.selectSellProductInfo(sDTO);// 상품 정보 조회
			sDomain.setTradeType(sDAO.selectSellCode(sDTO.getProductNum()));// 거래 방법 조회
			sDomain.setProductImgName(sDAO.selectProductImg(sDTO.getProductNum()));// 세부 이미지명 저장
			for(int temp : sDomain.getTradeType()) {
				if(temp == 1) {
					sDomain.setDirectFlag(true);
				}// end if
				if(temp == 2) {
					sDomain.setPackageFlag(true);
				}// end if
			}// end for
			
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		}// end catch
		
		return sDomain;
	}// searchModifyProdcut
	
	public boolean modifyProduct(SellDTO sDTO) {
		System.out.println("수정하기 서비스에 입력된 sDTO : " + sDTO);
		boolean flag = false;
		int queryCnt = 0;
		int standardCnt = 0;
		SqlSession ss = null;
		MultipartFile thumbnailFile = sDTO.getThumbnail();
		File removeFile = null;
		File upFile = null;
		if(!thumbnailFile.isEmpty()) {// 새로운 이미지가 선택되어 파일로 전달될때
			removeFile = new File(thumbnailUploadDir, sDTO.getThumbnailName());// 기존 썸네일 삭제
			removeFile.delete();
			String tempthumbnailName = UUID.randomUUID() + "-" + thumbnailFile.getOriginalFilename();
			upFile = new File(thumbnailUploadDir , tempthumbnailName);
			sDTO.setThumbnailName(tempthumbnailName);// 썸네일 이미지명 DTO에 저장
			
			try {
				thumbnailFile.transferTo(upFile);// 새로운 이미지 파일 업로드
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// end catch
			
		}// end if
		
		try {
			ss = MyBatisHandler.getInstance().getMyBatisHandler(false);
		
			if(!sDTO.getDeletedImageIds().isEmpty()) {// 삭제할 이미지명 리스트의 내용이 있다면
				for(String removeFileName : sDTO.getDeletedImageIds()) {
					removeFile = new File(thumbnailUploadDir, removeFileName);// 삭제할 세부 이미지
					removeFile.delete();// 이미지 삭제
					queryCnt += sDAO.deleteDetailImg(removeFileName, ss);// db에서 이미지 삭제
					standardCnt++;
					System.out.println("db에서 이미지 삭제 standardCnt : " + standardCnt);
					System.out.println("db에서 이미지 삭제 queryCnt : " + queryCnt);
				}// end for
			}// end if
			
			String tempProductImgName ="";
			File upProductImg = null;
			Map<String, Object> map = null;
			if(sDTO.getProductImages() != null) {
				for(MultipartFile mf : sDTO.getProductImages()) {
					if(!mf.isEmpty()) {// 추가된 세부 이미지가 있다면
						tempProductImgName = UUID.randomUUID() + "-" + mf.getOriginalFilename();// uuid 붙여서 이미지명 만들고
						upProductImg = new File(productDetailUploadDir, tempProductImgName);// 만든 이미지명으로 file 개게 만들고
						map = new HashMap<String, Object>();// db에 들어갈 parameter map 만들고
						map.put("productNum", sDTO.getProductNum());// 값 넣고
						map.put("imageName", tempProductImgName);// 값 넣고
						queryCnt += sDAO.insertImages(map, ss);// db에 새로운 세부 이미지명 추가
						standardCnt++;
						System.out.println("db에 새로운 세부 이미지명 추가 standardCnt : " + standardCnt);
						System.out.println("db에 새로운 세부 이미지명 추가 queryCnt : " + queryCnt);
						try {
							mf.transferTo(upProductImg);// 이미지 업로드 하기
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}// end catch
					}// end if
				}// end for
			}// end if
			
			
			// 거래 유형 초기화
			sDAO.deleteTradeType(sDTO.getProductNum(), ss);
			
//			System.out.println("거래 유형 초기화 standardCnt : " + standardCnt);
//			System.out.println("거래 유형 초기화 queryCnt : " + queryCnt);
			
			// 거래 유형 db에 insert
			for(String tradeCode : sDTO.getTradeType()) {
				map = new HashMap<String, Object>();
				map.put("sellCode", tradeCode);
				map.put("productNum", sDTO.getProductNum());
				queryCnt += sDAO.insertTradeType(map, ss);
				standardCnt++;
//				System.out.println("standardCnt : " + standardCnt);
//				System.out.println("queryCnt : " + queryCnt);
			}// end for
			
				
			if(!sDTO.getTradeType().contains("1")) { // 거래 방법에 직거래가 없는 경우 직거래 지역 삭제
				sDAO.deleteTradeArea(sDTO.getProductNum(), ss);
			} else if(sDTO.getTradeType().contains("1")) {
				sDAO.deleteTradeArea(sDTO.getProductNum(), ss);// 거래 지역 삭제 때리고
				if(sDTO.getAddr() != null && !"".equals(sDTO.getAddr())) {// 입력된 거래 지역이 있으면
					map = new HashMap<String, Object>();
					map.put("tradeArea", sDTO.getAddr());
					map.put("productNum", sDTO.getProductNum());
					sDAO.insertTradeArea(map, ss);// 거래 지역 추가
				}// end if
			}// end else
			
			
			queryCnt += sDAO.updateSellProduct(sDTO, ss);// product table에 업데이트
			standardCnt++;
			
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
	}// modifyProduct

	
}// class