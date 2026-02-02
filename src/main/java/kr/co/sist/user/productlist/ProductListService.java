package kr.co.sist.user.productlist;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.sist.user.ProductDomain;

@Service
public class ProductListService {
	@Autowired(required = false)
	private ProductListDAO plDAO;

	public List<CategoryDomain> searchCategory() {
		List<CategoryDomain> list = null;
		try {
			list = plDAO.selectCategory();
		} catch (PersistenceException pe) {
		} // end catch
		return list;
	}// searchCategory
	
	public List<ProductDomain> searchProductList(ProductRangeDTO prDTO) {
		List<ProductDomain> list = null;
		try {
			list = plDAO.selectProductList(prDTO);
		} catch (PersistenceException pe) {
		} // end catch
//		System.out.println(list);
		return list;
	}// searchProductList
	
	public int totalCnt(ProductRangeDTO prDTO) {
		int totalCnt = 0;
		try {
			totalCnt = plDAO.selectProductTotalCnt(prDTO);
		} catch (PersistenceException pe) {
		} // end catch
		return totalCnt;
	}// searchProductTotalCnt
	
	/**
	 * 한 화면에 보여줄 게시글의 수
	 * 
	 * @return
	 */
	public int pageScale() {
		return 10;
	}// pageScale
	
	/**
	 * 총 페이지 수
	 * 
	 * @param totalCount - 전체 게시물의 수
	 * @param pageScale  - 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int totalPage(int totalCount, int pageScale) {
		/*
		 * int totalPage = totalCount / pageScale; if(totalCount % pageScale != 0){ // 딱
		 * 떨어지지 않으면 1장이 더 필요 totalPage ++; }// end if
		 */
		return (int) Math.ceil((double) totalCount / pageScale);
	}// totalPage

	/**
	 * 페이지의 시작 번호 구하기
	 * 
	 * @param currentPage - 현재 페이지
	 * @param pageScale   - 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int startNum(int currentPage, int pageScale) {
		// 1페이지 클릭 : 1*10-10+1, 1페이지 클릭 : 2*10-10+1
		return currentPage * pageScale - pageScale + 1;
	}// startNum

	/**
	 * 페이지의 끝 번호 구하기
	 * 
	 * @param startNum  - 페이지 시작 번호
	 * @param pageScale - 한 화면에 보여줄 게시물의 수
	 * @return
	 */
	public int endNum(int startNum, int pageScale) {
		return startNum + pageScale - 1;
	}// endNum
	
	/**
	 * 페이지네이션 [<<] ... [1][2][3]...[>>]
	 * 
	 * @param rDTO
	 * @return
	 */
	public String pagination(ProductRangeDTO rDTO) {
		StringBuilder pagination = new StringBuilder();

		// 1.한 화면에 보여줄 pagination의 수.
		int pageNumber = 3;

		// 2.화면에 보여줄 시작 페이지 번호 설정. 1,2,3 => 1로 시작, 4,5,6 => 4로 시작, 7,8,9 => 7로 시작
		int startPage = (rDTO.getCurrentPage() - 1) / pageNumber * pageNumber + 1;

		// 3.화면에 보여줄 마지막 페이지 번호 설정 1,2,3 => 3
		int endPage = ((startPage - 1) + pageNumber) / pageNumber * pageNumber;

		// 4. 총 페이지수가 연산된 마지막 페이지수보다 작다면 총페이지 수가 마지막 페이지수로 설정
		if (rDTO.getTotalPage() <= endPage) {
			endPage = rDTO.getTotalPage();
		} // end if

		// 5.첫페이지가 인덱스화면 (1페이지가 아닌 경우)
		int movePage = 0;
		StringBuilder prevMark = new StringBuilder();
		prevMark.append("[&lt;&lt;]");
		// prevMark.append("<li class='page-item'><a class='page-link'
		// href='#'>Previous</a></li>");
		
		if (rDTO.getCurrentPage() > pageNumber) {// 시작페이지 번호가 3보다 크면
			movePage = startPage - 1;// 4,5,6 -> 3,2,1 / 7,8,9 -> 4,5,6
			prevMark.delete(0, prevMark.length());// 이전에 링크가 없는 [<<] 삭제
			prevMark.append("[<a href='").append(rDTO.getUrl()).append("?currentPage=").append(movePage);
			// 검색 키워드가 있다면 검색 키워드를 붙인다.
			if (rDTO.getKeyword() != null && !rDTO.getKeyword().isEmpty()) {// 검색 키워드가 있다면 검색 키워드를 붙인다.
//				prevMark.append("&keyword=").append(rDTO.getKeyword()).append("&field=").append(rDTO.getField());
			} // end if	
			prevMark.append("' class='prevMark'>&lt;&lt;</a>]");
		} // end if

		// 6.시작페이지 번호부터 끝페이지 번호까지 화면에 출력
		StringBuilder pageLink = new StringBuilder();
		movePage = startPage;

		while (movePage <= endPage) {
			if (movePage == rDTO.getCurrentPage()) {// 현재 페이지 : 링크x
				pageLink.append("[ <span class='currentPage'>").append(movePage).append("</span> ]");
			} else {// 현재페이지가 아닌 다른 페이지 : 링크 O
				pageLink.append("[ <a class='notCurrentPage' href='").append(rDTO.getUrl()).append("?currentPage=")
						.append(movePage);
				// 검색 키워드가 있다면 검색 키워드를 붙인다.
				if (rDTO.getKeyword() != null && !rDTO.getKeyword().isEmpty()) {// 검색 키워드가 있다면 검색 키워드를 붙인다.
//					pageLink.append("&keyword=").append(rDTO.getKeyword()).append("&field=").append(rDTO.getField());
				} // end if
				pageLink.append("'>").append(movePage).append("</a> ]");
			} // end else
			movePage++;
		} // end while

		// 7. 뒤에 페이지가 더 있는 경우
		StringBuilder nextMark = new StringBuilder();
		nextMark.append("[&gt;&gt;]");
		if (rDTO.getTotalPage() > endPage) {// 뒤에 페이지가 더 있음
			movePage = endPage + 1;
			nextMark.delete(0, nextMark.length());
			nextMark.append("[<a class='nextMark' href='").append(rDTO.getUrl()).append("?currentPage=")
					.append(movePage);
			// 검색 키워드가 있다면 검색 키워드를 붙인다.
			if (rDTO.getKeyword() != null && !rDTO.getKeyword().isEmpty()) {// 검색 키워드가 있다면 검색 키워드를 붙인다.
//				nextMark.append("&keyword=").append(rDTO.getKeyword()).append("&field=").append(rDTO.getField());
			} // end if
			nextMark.append("'>&gt;&gt;</a>]");
		} // end if

		pagination.append(prevMark).append(" ... ");
		pagination.append(pageLink);
		pagination.append(" ... ").append(nextMark);

//		System.out.println(startPage);
//		System.out.println(endPage);
//		System.out.println(rDTO.getTotalPage());
//		System.out.println("=========");

		return pagination.toString();
	}// pagination
	
	
}// class
