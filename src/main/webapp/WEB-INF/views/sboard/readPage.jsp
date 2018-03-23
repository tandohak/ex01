<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
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
						<c:if test="${login.userid == boardVO.writer}">
							<input type="button" value="Modify" class="btn btn-warning" onclick="goModify()"/>
							<input type="button" value="Remove" class="btn btn-danger" onclick="onSubmit()" /> 
						</c:if>
						<input type="button" value="go list" class="btn btn-primary" onclick="goList()" />
					</div>
				</div>
			</div> 
		</div>
	</div>
	
	<!-- replies [ -->
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">Add Reply</h3>
				</div>
				<div class="box-body">
					<label for="writer">Writer</label>
					<input type="text" id="writer" class="form-control" value="${login.userid}" readonly="readonly"/>
					<label for="replytext">Reply Text</label>
					<input type="text" id="replytext" class="form-control"/>
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">Add Reply</button>
				</div>
			</div>
		</div>
	</div>
	
	<ul class="timeline">
		<li class="time-label" id="replyesDiv">
			<span class="bg-green">Replies List [<span class="replycnt">${boardVO.replycnt}</span>]</span>
		</li>	
	</ul>
	
	<div class="text-center">
		<ul id="pagination" class="pagination apgination-sm no-margin">
			
		</ul>
	</div>
	
	<div class="modal fade" id="modifyModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="modalRno"></h4>
				</div>
				<div class="modal-body">
					<p>
						<input type="text" id="modifyText" placeholder="Text" class="form-control">
					</p>
				</div>
				<div class="modal-footer"> 
					<button type="button" class="btn btn-default" data-dismiss="modal" id="modifyReplyBtn">modify</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" id="deleteReplyBtn">delete</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
	<script id="template" type="text/x-handlebars-template">
		{{#each.}}			
			<li class="replyLi" data-rno={{rno}}>
				<i class="fa fa-comments bg-blue"></i>
				<div class="timeline-item">
					<span class="time">
						<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
					</span>
					<h3 class="timeline-header">
						<strong class='selRno'>{{rno}}</strong>-{{replyer}}
					</h3>
					<div class="timeline-body selText">{{replytext}}</div>
					{{#if replyer}}
					<div class="timeline-footer">
						<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal" id="openModal">Modify</a>
					</div> 
					{{/if}}
				</div>
			</li>
		{{/each}} 
	</script>
	<script>
		Handlebars.registerHelper("if",function(replyer,options){
			if(replyer == "${login.userid}"){
				return options.fn(this);  
			}else{
				return "";			
			}
		});
	
		Handlebars.registerHelper("prettifyDate",function(val){
			var dateObj = new Date(val);
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth() +1 ;
			var date = dateObj.getDate();
			
			return year + "." + month + "." + date;			
		})	
	
	
		var bno = ${boardVO.bno };
		var replyPage = 1;
		var templateFunc = Handlebars.compile($("#template").html());
		var page = 1;
		
		$("#modifyReplyBtn").click(function(){
			var rno = $("#modalRno").text();
			var replytext = $("#modifyText").val();
			
			var sendData = {replytext:replytext};
			
			$.ajax({
				url:"/ex01/replies/"+rno,
				type:"put",
				headers:{"Content-Type":"application/json"},
				dataType:"text",
				data:JSON.stringify(sendData),//json객체를 json String으로 변경해줌
				success:function(res){
					console.log(res);
					getPage(page);
				}
			})
		})
		
		$("#deleteReplyBtn").click(function(){
			var rno = $("#modalRno").text();
			
			$.ajax({
				url:"/ex01/replies/"+rno,
				type:"delete",
				dataType:"text",
				success:function(res){
					console.log(res);
					getPage(page);
					var replycnt = $(".replycnt").text()-1;
					$(".replycnt").text(replycnt);
				}
			})
		})
		
		$(document).on("click","#openModal",function(){
			var index = $(this).parents(".replyLi").index();
			var modifyRno = $(".selRno").eq(index-1).text(); 
			var modifyText = $(".selText").eq(index-1).text();  
			 
			$("#modalRno").text(modifyRno); 
			$("#modifyText").val(modifyText);
		}); 
		
		$("#replyAddBtn").click(function(){
			var replyer = $("#writer").val();
			var replytext = $("#replytext").val();
			
			var sendData = {bno:bno,replyer:replyer, replytext:replytext};
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/",
				type:"post",
				headers:{"Content-Type":"application/json"},
				dataType:"text",
				data:JSON.stringify(sendData),//json객체를 json String으로 변경해줌
				success:function(res){
					console.log(res);
					alert(res);
					getPage(page); 
					var replycnt = Number($(".replycnt").text())+1;
					$(".replycnt").text(replycnt);
				}
			})

		})
		
	
		function printPaging(pageMaker){
			var str = "";
			if(pageMaker.prev){
				str += "<li><a href='" + pageMaker.startPage-1 + "'> << </a></li>";
			}
			 
			for(var i = pageMaker.startPage; i <= pageMaker.endPage; i++){
				if(pageMaker.cri.page == i){
					str += "<li class='active'><a href='" + i + "'> "+ i +" </a></li>";
				}else{
					str += "<li><a href='" + i + "'> "+ i +" </a></li>";
				}
				
			}  
			
			if(pageMaker.next){
				str += "<li><a href='" + pageMaker.startPage+1 + "'> >> </a></li>";
			}
			$("#pagination").html(str);
		}
		
		$("#pagination").on("click","li a", function(e){
			e.preventDefault();
			page = $(this).attr("href");
			getPage(page);
		});
		
		function getPage(page){
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/"+bno + "/" + page,
				type:"get",
				dataType:"json",
				success:function(res){ 
					$(".timeline li").not(".timeline li:eq(0)").remove();
					$(".timeline").append(templateFunc(res.list));				
					printPaging(res.pageMaker); 
				}
			}) 		
		}
		getPage(1);
	/* 	$("#replyesDiv").click(function(){
			getPage(1);
		}) */
	</script>
	<!-- ] replies -->
</section>
<%@ include file="../include/footer.jsp"%>