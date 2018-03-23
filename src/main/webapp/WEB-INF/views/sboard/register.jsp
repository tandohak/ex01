<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<section class="content"> 
 	<div class="row">
 		<div class="col-md-12">
 			<div class="box">
 				<div class="box-header with-border">
 					<h3 class="box-title">REGISTER</h3>
 				</div>
 				<div class="box-body">
 					<form action="register" method="post" id="f1">
 						<div class="form-group">
 							<label>Title</label>
 							<input type="text" name="title" class="form-control" placeholder="title" class="input">
 						</div>
 						
 						<div class="form-group">
 							<label>Content</label>
 							<textarea rows="5" name="content" class="form-control" placeholder="content" class="input"></textarea>
 						</div>
 						
 						<div class="form-group">
 							<label>Writer</label>
 							<input type="text" name="writer" class="form-control" placeholder="wirter" class="input" value="${login.userid}" readonly="readonly">
 						</div>
 						
 						<div class="form-group">
 							<input type="submit" value="submit" class="btn btn-primary"/>
 						</div>  
 					</form>
 				</div>
 			</div>
 		</div>
 	</div>
</section>
<script>
	$(function(){ 
		$("#f1").submit(function(e){
			$(".input").each(function(i,obj){
				if($(this).val() == ""){  
					e.preventDefault(); 
					alert("내용을 모두 입력하세요." + $(this).val());
					return false;
				}
			})
		})
	})
</script>
<%@ include file="../include/footer.jsp"%>