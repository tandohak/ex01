<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.image_div{
		width:150px;
		height:150px;
		position: relative;
		
		float:left;
	}
	.image_div button{
		position:absolute;
		top:0;
		right:0;
	}
	#image_wrap{
		overflow: hidden;
	}
	.image_div img{
		max-width:150px;
		max-height:150px;
		margin-right:15px;
		
	}
</style>
<script>
	$(function(){
		var index = 1;
		$("#imageFile").change(function(e){
			var file = document.getElementById("imageFile");
			
			
			
			var reader = new FileReader();
			reader.onload = function(e){
				var imgObj = $("<img>").attr("src",e.target.result);
				var butttonObj = $("<button>").text("X").addClass("del_img_btn").attr("type","button");
				var divObj = $("<div>").addClass("image_div").append(imgObj).append(butttonObj);
				$("#image_wrap").append(divObj);
			}

			reader.readAsDataURL(file.files[0]);
			
			reader.onloadend = function(e){
				if(index >= file.files.length){
					index = 1;
					return;
				}
				reader.readAsDataURL(file.files[index]);
				index += 1;
			}
		})
		$(document).on("click",".del_img_btn",function(){
			var imgsrc =$(this).parent().find("img").attr("data-del");
			if(imgsrc !=null){
				
				$("#modifyForm").append("<input type='hidden' name='deleteFiles' value='"+imgsrc+"'>");
			}
			
			$(this).parent().remove();
		})
	})
</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">List All</h3>
				</div>
				<div class="box-body">
					<form method="post" action="modify?bno=${boardVo.bno }" id="modifyForm" enctype="multipart/form-data">
					<input type="hidden" name="keyword" value="${cri.keyword }">
					<input type="hidden" name="searchType" value="${cri.searchType }">
						<div class="form-group">
						<label>Title</label> <input type="text" name="title"
							class="form-control" value="${boardVo.title }"
							placeholder="title" >
						</div>    
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" class="form-control" name="content"
								placeholder="content" >${boardVo.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label> <input type="text" name="writer"
								class="form-control" value="${boardVo.writer }"
								placeholder="writer" >
						</div>
						<div class="form-group" id="image_wrap">
							<c:forEach var="file" items="${boardVo.files }">
								<div class="image_div">
									<img src="displayFile?filename=${file }" data-del="${file }">
									<button type="button" class="del_img_btn">X</button>
								</div>
							</c:forEach>
						</div>
						<div class="form-group">
							<label>Image File</label>
							<input type="file" name="insertFiles" class="form-control" multiple="multiple" id="imageFile">
						</div>
						<div class="form-group">
							<input type="submit" value="수정">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp"%>