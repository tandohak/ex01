<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp"%>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST ALL</h3>
				</div>
				<div class="box-body">
					<select name="searchType">
						<option value="n">----------------</option>
						<option value="t" ${cri.searchType == 't' ? 'selected' : ''}>title</option>
						<option value="c" ${cri.searchType == 'c' ? 'selected' : ''}>content</option>
						<option value="w" ${cri.searchType == 'w' ? 'selected' : ''}>writer</option>
						<option value="tc" ${cri.searchType == 'tc' ? 'selected' : ''}>title or content</option>
						<option value="cw" ${cri.searchType == 'cw' ? 'selected' : ''}>content or writer</option>
						<option value="tcw" ${cri.searchType == 'tcw' ? 'selected' : ''}>title or content or writer</option>
					</select>
					<input type="text" name="keyword" id="keywordInput" value="${cri.keyword}">
					<button id="searchBtn">Search</button>
					<button id="newBtn">New Board</button>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>BNO</th>
								<th>TITLE</th>
								<th>WRITER</th>
								<th>REGDATE</th>
								<th>VIEWCNT</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${list }">
								<tr>
									<td>${item.bno }</td>
									<td><a href="readPage${pageMaker.makeSearch(pageMaker.cri.page) }&bno=${item.bno }">${item.title }</a><span style="color:black;">[${item.replycnt }]</span></td>
									<td>${item.writer }</td>
									<td><fmt:formatDate value="${item.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
									<td style="width:40px;">
										<span class="badge bg-red">${item.viewcnt }</span>
									</td>  
								</tr> 
							</c:forEach>
						</tbody>
					</table> 
				</div>
				<div class="box-footer">
					<ul class="pagination">
						<c:if test="${pageMaker.prev}">
							<li><a href="listPage${pageMaker.makeSearch(pageMaker.startPage-1)}">&laquo;</a></li>
						</c:if>
						<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
							<li ${pageMaker.cri.page == idx? 'class=active' : ''}><a href="listPage${pageMaker.makeSearch(idx)}">${idx }</a></li>
						</c:forEach>
						<c:if test="${pageMaker.next}">
							<li><a href="listPage${pageMaker.makeSearch(pageMaker.endPage+1)}">&raquo;</a></li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</section>
<script>
	$(function(){
		$("#searchBtn").click(function(){
			var searchType = $("select[name='searchType']").val();
			var keyword = $("input[name='keyword']").val();
			location.href = "listPage${pageMaker.makeQuery(1)}" 
						  + "&searchType=" + searchType
						  + "&keyword=" + keyword;
		})
		
		$("#newBtn").click(function(){
			var searchType = $("select[name='searchType']").val();
			var keyword = $("input[name='keyword']").val();
			location.href = "register";
		})
				
	})
</script>
<%@ include file="../include/footer.jsp"%>