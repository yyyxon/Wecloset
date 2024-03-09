<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>    
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
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
					<tr class="${row.boardAvailable == 1 ? 'tr-board' : ''}" data-boardid="${row.boardID}">
						<td>${row.num}</td>
						
						<td class="text-left">
						
							<c:choose>
								<c:when test="${row.boardAvailable != 1}">
									<span style="color:#999999">(삭제된 게시물입니다.)</span>
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
					<a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardWrite&boardName=${param.boardName}" class="btn btn-primary pull-right" type="submit">
						<i class="glyphicon glyphicon-pencil"></i> 글쓰기
					</a>
					
						${pagination}
					</td>
				</tr>
			</tbody>
		</table>
	

