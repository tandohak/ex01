<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
<script>
	var bno = ${boardVo.bno};
	var replyPage = 1;

	$(function() {
		getPage(1);
		$("#remove").click(function() {
			if (confirm("삭제하시겠습니까?")) {
				$("#f1").attr("action", "remove");
				$("#f1").submit();
			}
		})

		$("#golist").click(function() {

			$("#f1").attr("action", "listPage");
			$("#f1").submit();

		})
		$("#modify").click(function() {
			$("#f1").attr("action", "modify");
			$("#f1").submit();
		})
		$("#replyAddBtn").click(function() {

			var replyer = $("#writer").val();
			var replytest = $("#replytest").val();
			var sendData = {
				bno : bno,
				replyer : replyer,
				replytest : replytest
			};
			$.ajax({
				url : "${pageContext.request.contextPath}/replies/",
				type : "post",
				headers : {
					"Content-Type" : "application/json"
				},
				dataType : "text",
				data : JSON.stringify(sendData), //json객체를 json string으로 변경해줌
				success : function(result) {
					console.log(result);
					getPage(1);
					alert(result);
					
				}

			})
		})
		
		$(document).on("click","#updateReplyBtn",function(){
			var replytest = $("#updatereplytext").val();
			var rno = $("#rnonum").text();
			var sendData = {replytest:replytest};
			$.ajax({
				url:"/ex01/replies/" + rno,
				type:"put",
				headers:{"Content-Type":"application/json"},
				dataType:"text",
				data:JSON.stringify(sendData),
				success:function(result){
					getPage(1);
					alert("수정되었습니다.");
					
				}
				
			})
		})
		$(document).on("click","#deleteReplyBtn",function(){
			if(confirm("삭제하시겠습니까?")){
				
				var rno = $("#rnonum").text();
				var sendData = {rno:rno};
				
				$.ajax({
					url:"/ex01/replies/" + rno,
					type:"delete",
					headers:{"Content-Type":"application/json"},
					dataType:"text",
					data:JSON.stringify(sendData),
					success:function(result){    
						getPage(1);
						alert("삭제되었습니다.");
					}
				})
			}
		})
		$(document).on("click","#pagination li a",function(e){
			e.preventDefault();
			var page = $(this).text();
			getPage(page);
			$(this).addClass("active");

		})
		$("#replyesDiv").click(function() {
			getPage(1);
		})
		
	})
</script>


<script id="template" type="text/x-handlebars-template">
	{{#each.}}
		<li class="replyLi" data-rno ="{{rno}}">
			<i class="fa fa-comments bg-blue"></i>
			<div class="timeline-item">
				<span class="time">
					<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
				</span>
					<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
					<div class="timeline-body">{{replytest}}</div>
					<div class="timeline-footer">
						<a class="btn btn-primary btn-xs modifybtn" data-toggle="modal"
                  data-target="#modifyModal">Modify</a>
					<div>
			<div>
		</li>
	{{/each}}

</script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">List All</h3>
				</div>
				<div class="box-body">
					<form method="get" action="" id="f1">
						<input type="hidden" name="bno" value="${boardVo.bno }"> <input
							type="hidden" name="page" value="${cri.page }"> <input
							type="hidden" name="perPageNum" value="${cri.perPageNum }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="deleteFile">
					</form>
					<div class="form-group">
						<label>Title</label> <input type="text" name="title"
							class="form-control" value="${boardVo.title }"
							placeholder="title" readonly="readonly">
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" class="form-control" name="content"
							placeholder="content" readonly="readonly">${boardVo.content }</textarea>
					</div>
					<div class="form-group">
						<label>Writer</label> <input type="text" name="writer"
							class="form-control" value="${boardVo.writer }"
							placeholder="writer" readonly="readonly">
					</div>
					<div class="form-group" id="image_wrap">
						<c:forEach var="file" items="${boardVo.files }">
							<img src="displayFile?filename=${file }" data-del="${file }" class="imgsrc">
						</c:forEach>
					</div>
					<div class="form-group">
						<input type="button" value="Modify" id="modify"
							class="btn btn-warning"> <input type="button"
							value="Remove" id="remove" class="btn btn-danger"> 
							<input type="button" value="GO List" id="golist" class="btn btn-primary">
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- replies[ -->
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">Add Reply</h3>
				</div>
				<div class="box-body">
					<label for="writer">Writer</label> <input type="text" id="writer"
						class="form-control"> <label for="replytest">Reply
						Text</label> <input type="text" id="replytest" class="form-control">
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-primary" id="replyAddBtn">Add
						Reply</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="replyesDiv"><span class="bg-green">Replies
						List[${boardVo.replycnt}]</span></li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin">
				</ul>
			</div>
		</div>
	</div>
	<script>
		Handlebars.registerHelper("prettifyDate", function(value) {
			
			var dateObj = new Date(value);
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth() + 1;
			var day = dateObj.getDate();

			return year + "-" + month + "-" + day;
		});
		var t_fn = Handlebars.compile($("#template").html());
		function getPage(page) {
			$.ajax({
				url : "${pageContext.request.contextPath}/replies/" + bno + "/"
						+ page,
				type : "get",
				dataType : "json",
				success : function(json) {
					$(".replyLi").remove();
					$(".timeline").append(t_fn(json.list));
					printPaging(json.pageMaker);
				}
			})
		}
		function printPaging(pageMaker) {
			var str = "";
			if (pageMaker.prev) {
				str += "<li><a href='"+pageMaker.startPage-1+"'> << </a></li>";
			}
			for (var i = pageMaker.startPage; i <= pageMaker.endPage; i++) {
				if (pageMaker.cri.page == i) {
					str += "<li class='active'><a href='"+i+"'>" + i
							+ "</a></li>";
				} else {
					str += "<li><a href='"+i+"'>" + i + "</a></li>";
				}

			}
			if (pageMaker.next) {
				str += "<li><a href='"+pageMaker.endPage+1+"'> >> </a></li>";
			}

			$("#pagination").html(str);
		}
		$(document).on("click",".modifybtn",function(){
			
			var rno = $(this).parents(".timeline-item").find(".timeline-header").find("strong").text();
			var replytext = $(this).parents(".timeline-item").find(".timeline-body").text();
            $("#rnonum").text(rno);
            $("#updatereplytext").val(replytext);
            
		})
		
	</script>
</section>
<div id="modifyModal" class="modal fade modal-primary" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h3 id="rnonum"></h3>
      </div>
      <div class="modal-body">
        <input type="text" value="" id="updatereplytext" class="form-control">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="updateReplyBtn">Modify</button>
        <button type="button" class="btn btn-danger" id="deleteReplyBtn">DELETE</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="../include/footer.jsp"%>