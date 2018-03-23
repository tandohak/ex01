<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<section class="content"> 
 	<div class="row">
 		<div class="col-md-12">
 			<div class="box">
 				<div class="box-header with-border">
 					<h3 class="box-title">MODIFY</h3>
 				</div>
 				<div class="box-body">
 					<form action="modifyPage" method="post">
 						<input type="hidden" name="bno" class="form-control" value="${boardVO.bno }">
 						
 						<div class="form-group">
 							<label>Title</label>
 							<input type="text" name="title" class="form-control" placeholder="title" value="${boardVO.title }">
 						</div>
 						
 						<div class="form-group">
 							<label>Content</label>
 							<textarea rows="5" name="content" class="form-control" placeholder="content">${boardVO.content }</textarea>
 						</div>
 						
 						<div class="form-group">
 							<label>Writer</label>
 							<input type="text" name="writer" class="form-control" placeholder="wirter" value="${boardVO.writer}" readonly="readonly">
 						</div> 
 						
 						<div class="form-group">
 							<input type="submit" value="submit" class="btn btn-primary"/>
 						</div>
 						
 						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="perPageNum" value="${cri.perPageNum }"> 
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
 					</form>
 				</div>
 			</div>
 		</div>
 	</div>
</section>
<%@ include file="../include/footer.jsp"%>