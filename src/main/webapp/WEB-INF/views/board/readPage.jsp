<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<script>
	$(function() {

	})
	function goList() {
		$("#f1").attr("method","get");
		$("#f1").attr("action","listPage");
		$("#f1").submit();
		//location.href = "listPage";
	}
	function goModify() {
		$("#f1").attr("method","get");
		$("#f1").attr("action","modifyPage");
		$("#f1").submit();
	}
	function onSubmit() {
		$("#f1").submit();
	}
</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">  
				<div class="box-header with-border"> 
					<h3 class="box-title">READ</h3>
				</div>
				<div class="box-body">
					<form method="post" action="removePage" id="f1">
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="page" value="${cri.perPageNum }">
					</form>
					<div class="form-group">
						<label>Title</label> <input type="text" name="title"
							class="form-control" placeholder="title"
							value="${boardVO.title }" readonly="readonly">
					</div>

					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" name="content" class="form-control"
							placeholder="content" readonly="readonly">${boardVO.content }</textarea>
					</div>

					<div class="form-group">
						<label>Writer</label> <input type="text" name="writer"
							class="form-control" placeholder="wirter"
							value="${boardVO.writer }" readonly="readonly">
					</div>

					<div class="form-group">
						<input type="button" value="Modify" class="btn btn-warning" onclick="goModify()"/>
						<input type="button" value="Remove" class="btn btn-danger" onclick="onSubmit()" /> 
						<input type="button" value="go list" class="btn btn-primary" onclick="goList()" />
					</div>
				</div>
			</div> 
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp"%>