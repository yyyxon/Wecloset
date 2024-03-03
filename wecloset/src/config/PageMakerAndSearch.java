package config;

import com.wecloset.web.BoardConstants;

import lombok.Data;

@Data
public class PageMakerAndSearch {

	private int page;
	private int perPageNum;
	private int pageStart;

	private int totalCount; // 전체 개수
	private int startPage; // 시작 페이지
	private int endPage; // 끝페이지
	private boolean prev; // 이전 여부
	private boolean next; // 다음 여부

	private int displayPageNum = 10;
	private int tempEndPage; // 마지막 페이지

	// 검색처리 추가
	private String searchType;
	private String keyword;
	private Integer boardGroup;
	private String userID;
	
	
	public PageMakerAndSearch() {
		this.page = 1; // 초기 페이지는 1
		this.perPageNum = 10; // limit 10 개씩 보여준다.
	}
	

	public void setPage(int page) {
		// 페이지 번호가 0이거나 0보다 작으면 1페이지로 한다.
		//
		if (page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}

	// MyBatis SQL 의 Mapper 에서 인식해서 가져가는 파라미터 값 메소드 #{perPageNum}
	public void setPerPageNum(int perPageNum) {
		// 몇개 씩 보여줄것인가 이다. 최대 100개씩 보여 줄것으로 설정한다.
		// 만약 0보다 작거나 100 보다 크면 10으로 초기화 시킨다.
		if (perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}

	// MyBatis SQL 의 Mapper 에서 인식해서 가져가는 파라미터 값 메소드 #{pageStart}
	public int getPageStart() {
		// 실질적으로 Mybatis 에서 파라미터로 인식해서 가져오는 것은 get 이다.
		// 따라서 getPageStart 에서 값을 설정한다.
		// 시작 데이터 번호 = (페이지 번호 -1 ) * 페이지당 보여지는 개수
		this.pageStart = (this.page - 1) * perPageNum;
		return this.pageStart;
	}

	// 전체 페이지 설정과 동시에 하단에 뿌려질 페이지 계산하기
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	private void calcData() {
		// 현재 페이지 번호 / 하단 페이지번호 수
		endPage = (int) (Math.ceil(page / (double) displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		tempEndPage = (int) (Math.ceil(totalCount / (double) perPageNum));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}

		prev = startPage == 1 ? false : true;
		next = endPage * perPageNum >= totalCount ? false : true;
	}

	// 일반적인 페이징 처리 파라미터 출력 데이터 ex) /memberList?page=4&perPageNum=10
	public String makeQuery(int page) {
		return "&page="+page+"&perPageNum="+this.perPageNum;
	}

 
	
	// 일반적인 페이징 부트스트랩 출력
	public String bootStrapPagingHTML(String url) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<ul class='pagination'>");
		if (prev) {
			sBuffer.append("<li class='paginate_button page-item previous' ><a class='page-link' href='" + url + makeQuery(1) + "'>&lt;</a></li>");
		}
		 
		if (prev) {
			sBuffer.append("<li class='paginate_button page-item previous' ><a class='page-link' href='" + url + makeQuery(startPage - 1) + "'>&laquo;</a></li>");
		}

		String active = "";
		for (int i = startPage; i <= endPage; i++) {
			if (page == i) {
				active = "active";
			} else {
				active = "";
			}
			sBuffer.append("<li  class='paginate_button page-item  " + active + "' >");
			sBuffer.append("<a class='page-link' href='" + url + makeQuery(i) + "'>" + i + "</a></li>");
			sBuffer.append("</li>");
		}

		if (next && endPage > 0) {
			sBuffer.append("<li class='paginate_button page-item next'  ><a class='page-link'  href='" + url + makeQuery(endPage + 1) + "'>&gt;</a></li>");
		}
		
		if (next && endPage > 0) {
			sBuffer.append("<li class='paginate_button page-item next'  ><a  class='page-link' href='" + url + makeQuery(tempEndPage) + "'>&raquo;</a></li>");
		}

		sBuffer.append("</ul>");
		return sBuffer.toString();
	}

	// 검색 추가 페이지 파라미터
	public String makeSearch(int page) {
		String st="";
		String k="";
		if(this.searchType!=null)st=this.searchType;
		if(this.keyword!=null) k=this.keyword;
		
		return "&page="+page+"&perPageNum="+this.perPageNum+"&keyword="+k+"&searchType="+st;
	}

	
	// 검색 추가 페이징 부트스트랩 출력
	public String bootStrapPagingSearchHTML(String url) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<ul class='pagination'>");
		if (prev) {
			sBuffer.append("<li class='paginate_button page-item previous' ><a class='page-link' href='" + url + makeSearch(1) + "'>&laquo;</a></li>");
		}

		if (prev) {
			sBuffer.append("<li class='paginate_button page-item previous' ><a class='page-link' href='" + url + makeSearch(startPage - 1) + "'>&lt;</a></li>");
		}

		String active = "";
		for (int i = startPage; i <= endPage; i++) {
			if (page == i) {
				active = "active";
			} else {
				active = "";
			}
			sBuffer.append("<li class='paginate_button page-item " + active + "' >");
			sBuffer.append("<a class='page-link' href='" + url + makeSearch(i) + "'>" + i + "</a></li>");
			sBuffer.append("</li>");
		}

		if (next && endPage > 0) {
			sBuffer.append("<li class='paginate_button page-item next' ><a class='page-link' href='" + url + makeSearch(endPage + 1) + "'>&gt;</a></li>");
		}

		if (next && endPage > 0) {
			sBuffer.append("<li class='paginate_button page-item next'  ><a class='page-link' href='" + url + makeSearch(tempEndPage) + "'>&raquo;</a></li>");
		}

		sBuffer.append("</ul>");
		return sBuffer.toString();
	}

	

	public String commentPagination() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<ul class='pagination'>");
		if (prev) {
			//sBuffer.append("<li><span class='glyphicon glyphicon-chevron-left wecloset-pagination-gray' onclick='commentList(2); return false;' ></span></li>");
		}

		if (prev) {
			sBuffer.append("<li><span class='glyphicon glyphicon-chevron-left wecloset-pagination-gray' onclick='commentList("+(startPage-1)+"); return false;' ></span></li>");
		}

		String active = "";
		for (int i = startPage; i <= endPage; i++) {
			if (page == i) {			
				active = "wecloset-pagination-active";				
			} else {
				active = "";
			}
			sBuffer.append("<li>");
			sBuffer.append("<a href='#' class='wecloset-pagination "+active+"' onclick='commentList("+i+"); return false;' >" + i + "</a></li>");
			sBuffer.append("</li>");
		}

		if (next && endPage > 0) {
			sBuffer.append("<li><span class='glyphicon glyphicon-chevron-right wecloset-pagination-gray'  onclick='commentList("+(endPage + 1)+"); return false;'></span></li>");
		}

		if (next && endPage > 0) {
			//sBuffer.append("<li class='paginate_button page-item next'  ><a class='page-link' href='" + url + makeSearch(tempEndPage) + "'>&raquo;</a></li>");
		}

		sBuffer.append("</ul>");
		return sBuffer.toString();
	}

	
	public String boardPagination() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<ul class='pagination'>");

		if (prev) {
			sBuffer.append("<li><span class='glyphicon glyphicon-chevron-left wecloset-pagination-gray' onclick='boardPagination("+(startPage-1)+"); return false;' ></span></li>");
		}

		String active = "";
		for (int i = startPage; i <= endPage; i++) {
			if (page == i) {			
				active = "wecloset-pagination-active";				
			} else {
				active = "";
			}
			sBuffer.append("<li>");
			sBuffer.append("<a href='#' class='wecloset-pagination "+active+"' onclick='boardPagination("+i+"); return false;' >" + i + "</a></li>");
			sBuffer.append("</li>");
		}

		if (next && endPage > 0) {
			sBuffer.append("<li><span class='glyphicon glyphicon-chevron-right wecloset-pagination-gray'  onclick='boardPagination("+(endPage + 1)+"); return false;'></span></li>");
		}

		sBuffer.append("</ul>");
		return sBuffer.toString();
	}



	/**
	 * 페이징 디폴트 셋팅
	 * @param page
	 * @param perPageNum
	 */
	public void settingPage(String page, String perPageNum, String boardName) {
		if(page!=null && !page.equals("")) this.setPage(Integer.parseInt(page));
		else  this.setPage(1);
		
		if(perPageNum!=null && !perPageNum.equals("")) this.setPerPageNum(Integer.parseInt(perPageNum));
		else this.setPerPageNum(10);
		
		if(boardName!=null && !boardName.equals(""))
		this.setBoardGroup(BoardConstants.getBoardGroup(boardName));
	}

	

	@Override
	public String toString() {
		return "PageMakerAndSearch [page=" + page + ", perPageNum=" + perPageNum + ", pageStart=" + pageStart
				+ ", totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev
				+ ", next=" + next + ", displayPageNum=" + displayPageNum + ", tempEndPage=" + tempEndPage
				+ ", searchType=" + searchType + ", keyword=" + keyword + ", boardGroup=" + boardGroup + ", userID="
				+ userID + "]";
	}


	

	
	
	
	
	
}