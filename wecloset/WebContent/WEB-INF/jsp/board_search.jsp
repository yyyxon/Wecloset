<%@page import="com.wecloset.web.BoardConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!--헤드	시작 -->
	<%@ include file="./include/head.jsp" %>
<style type="text/css">
/*search box css start here*/
.search-sec{
    padding: 2rem;
}
.search-slt{
    display: block;
    width: 100%;
    font-size: 0.875rem;
    line-height: 1.5;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    height: calc(3rem + 2px) !important;
    border-radius:0;
}
.wrn-btn{
    width: 100%;
    font-size: 16px;
    font-weight: 400;
    text-transform: capitalize;
    height: calc(3rem + 2px) !important;
    border-radius:0;
}
.search-row{
	margin-bottom: 50px;
}
</style>
</head>
<body>
	<!--상단메뉴	시작 -->
	<%@ include file="./include/nav_top_menu.jsp" %>
	<!--//상단메뉴		끝 -->

	<div class="container">	
		<p class="container-main-img"> 				
		</p>
			

	 <form action="${pageContext.request.contextPath}/WeclosetServlet" method="get" novalidate="novalidate">
            <div class="row search-row">
                <div class="col-lg-12">
                    <div class="row">
                  
                   <div class="col-lg-2 col-md-2 col-sm-12 p-0">
                            <select class="form-control" name="boardName">
                             	<option value="all" ${param.boardName eq 'all' ?  'selected' : '' }>전체</option>
                                <option value="ppippi" ${param.boardName eq 'ppippi' ?  'selected' : '' }>삐삐</option>
                                <option value="free" ${param.boardName eq 'free' ?  'selected' : '' }>자유</option>
                                <option value="question" ${param.boardName eq 'question' ?  'selected' : '' }>질문</option>
                                <option value="review" ${param.boardName eq 'review' ?  'selected' : '' }>후기</option>
                                <option value="fleaMarket" ${param.boardName eq 'fleaMarket' ?  'selected' : '' }>플리마켓</option>
                                <option value="stationery" ${param.boardName eq 'stationery' ?  'selected' : '' }>문방구</option>
                            </select>
                    </div>
                    
					<div class="col-lg-2 col-md-2 col-sm-12 p-0">
                            <select class="form-control" name="searchType">
                                <option value="all" ${param.searchType eq 'all' ?  'selected' : '' }>전체</option>
                                <option value="boardTitle"  ${param.searchType eq 'boardTitle' ?  'selected' : '' }>제목</option>
                                <option value="boardContent"  ${param.searchType eq 'boardContent' ?  'selected' : '' } >내용</option>
                            </select>
                    </div>

                                        
                        <div class="col-lg-6 col-md-6 col-sm-12 p-0">
                            <input type="text" class="form-control"  name="keyword" placeholder="검색할 단어를 입력 해 주세요." value="${param.keyword}">
                        </div>

                        <div class="col-lg-2 col-md-2 col-sm-12 p-0">
                        	<input type="hidden"   name="command"     value="boardSearch" >
                            <button type="submit" class="btn btn-primary wrn-btn">검색</button>
                        </div>
                        
                        
	              <div class="col-lg-12 col-md-12 col-sm-12 p-0">
			 			<h2 style="font-weight: 600; font-size: 15px;">'${param.keyword}' 에 대한 검색결과  ${totalCount} 개 </h2>			 			
			 	  </div>
                        
                    </div>
                </div>
            </div>
        </form>
			
				
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
			<thead>
				<tr>
					<th class="thead-type-1"><h5>번호</h5></th>
					<th class="thead-type-1" width="50%"><h5>제목</h5></th>
					<th class="thead-type-1"><h5>작성자</h5></th>
					<th class="thead-type-1"><h5>작성 날짜</h5></th>
					<th class="thead-type-1"><h5>조회수</h5></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList}" var="row">
					<tr class="${row.boardAvailable == 1 ? 'tr-board' : ''}" data-boardid="${row.boardID}" data-boardName="${row.boardName }">
						<td>${row.num}</td>
						
						<td class="text-left">
				
							<c:choose>
								<c:when test="${row.boardAvailable != 1}">
									<span style="color:#999999">(삭제된 게시물입니다.)</span>
								</c:when>
								<c:when test="${empty param.boardName or param.boardName eq 'all'}">	
										<strong>[${BoardConstants.getBoardNameKor(row.boardName)}]</strong> &nbsp;&nbsp; ${row.boardTitle}						
										<c:if test="${row.commentCount >0}">
											<span class="pd-l-10"><i class="fa fa-comments" title="댓글" ></i> ${row.commentCount}</span>
										</c:if>
										<c:if test="${row.attachCount >0}">
											<span class="pd-l-10"><i class="fa fa-file" title="첨부파일"></i></span>
										</c:if>	
								</c:when>								
								<c:otherwise>
										${row.boardTitle}						
										<c:if test="${row.commentCount >0}">
											<span class="pd-l-10"><i class="fa fa-comments" title="댓글" ></i> ${row.commentCount}</span>
										</c:if>
										<c:if test="${row.attachCount >0}">
											<span class="pd-l-10"><i class="fa fa-file" title="첨부파일"></i></span>
										</c:if>	
								</c:otherwise>
							</c:choose>
						</td>
											
						<td>${row.userName}</td>
						<td>${row.boardDateStr} </td>
						<td>${row.boardHit}</td>
					</tr>				
				</c:forEach>

				<tr>
					<td colspan="5">					
						${pagination}
					</td>
				</tr>
			</tbody>
		</table>
	
	

			
	</div>
	
		
	<!--모달팝업창	시작 -->
	<%@ include file="./include/modal_popup.jsp" %>
	<!--//모달팝업창	끝 -->
  
  	<!--footer	시작 -->
	<%@ include file="./include/footer.jsp" %>
	<!--//footer	끝 -->
	
</body>
</html>