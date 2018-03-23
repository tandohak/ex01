<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<title>Insert title here</title>
<style>
	.pagination{
		width:100%
	}
	
	.pagination li{
		list-style: none;
		float:left;
		padding:3px;
		border:1px solid blue;
		margin:3px;
	}
	
	.pagination a{
		text-decoration: none;
		color:black;
	}
	.pagination li.active{
		background: blue;
	}
	.pagination li.active a{
		color:#fff;
	}
</style>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.js"></script>
<script>
	
	var page = 1;
	 
	function getAllList(){
		var bno = $("#bno").val(); 
		$.ajax({
			url:"/ex01/replies/"+bno + "/" + page,
			type:"get",
			dataType:"json",
			success:function(res){
				var source = $("#template").html();
				var t_fn = Handlebars.compile(source);
				$("#list").html(t_fn(res.list));				
				printPaging(res.pageMaker);
			}
		}) 
	}
	
	function printPaging(pageMaker){
		var bno = $("#bno").val(); 
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
		$(".pagination").html(str);
	}

	$(function(){
		$("#newReplyBtn").click(function(){
			var bno = $("#bno").val();
			var replyer = $("#replyer").val();
			var replytext = $("#replytext").val();
			
			var sendData = {bno:bno,replyer:replyer, replytext:replytext};
			
			$.ajax({
				url:"/ex01/replies/",
				type:"post",
				headers:{"Content-Type":"application/json"},
				dataType:"text",
				data:JSON.stringify(sendData),//json객체를 json String으로 변경해줌
				success:function(res){
					console.log(res);
				}
			})
		})
		
		$("#modifyReplyBtn").click(function(){
			var rno = $("#rno").val();
			var replytext = $("#replytext").val();
			
			var sendData = {replytext:replytext};
			
			$.ajax({
				url:"/ex01/replies/"+rno,
				type:"put",
				headers:{"Content-Type":"application/json"},
				dataType:"text",
				data:JSON.stringify(sendData),//json객체를 json String으로 변경해줌
				success:function(res){
					console.log(res);
				}
			})
		})
		
		$("#deleteReplyBtn").click(function(){
			var rno = $("#rno").val();
			var replytext = $("#replytext").val();
			
			$.ajax({
				url:"/ex01/replies/"+rno,
				type:"delete",
				dataType:"text",
				success:function(res){
					console.log(res);
				}
			})
		})
		
		$(document).on("click",".del",function(){
			var no = $(this).parent().index();
			var rno = $(this).siblings(".listRno").text(); 
			console.log(rno);  
			$.ajax({
				url:"/ex01/replies/"+rno,
				type:"delete",
				dataType:"text",
				success:function(res){
					console.log(res);
					getAllList();
				}
			})
		});
		
		$("#listReplyBtn").click(function(){
			var bno = $("#bno").val();
			$("#list").empty();
			$.ajax({
				url:"/ex01/replies/all/"+bno,
				type:"get",
				dataType:"json",
				success:function(res){
					
					/*for(var i=0; i<res.length; i++){
						console.log();
						 $("#list").append(
							"<li>" +
							"rno : <p class='listRno'>" + res[i].rno + "</p>" +
							"작성자 : " + res[i].replyer +
							"작성내용: " + res[i].replytext +
							"<button class='del'>삭제</button>" +
							"</li>"
						); 
					}*/
					var source = $("#template").html();
					var t_fn = Handlebars.compile(source);
					$("#list").html(t_fn(res));
				}
			})
		})
		
		$(".pagination").on("click","li a", function(e){
			e.preventDefault();
			page = $(this).attr("href");
			getAllList();
		});
	})
</script>
</head>
<body>
	<h1>Ajax Test Page</h1>
	<div>
		<div>
			게시글 번호
			<input type="text" name="bno" id="bno">
		</div>
		<div>
			댓글 번호
			<input type="text" name="rno" id="rno"/>
		</div>
		<div>
			Replyer <input type="text" name="replyer" id="replyer" />
		</div>
		<div>
			Reply Text <input type="text" name="replytext" id="replytext" />
		</div>
		<button id="newReplyBtn">Add Reply</button>
		<button id="modifyReplyBtn">Modify Reply</button>
		<button id="deleteReplyBtn">delete Reply</button>
		<button id="listReplyBtn">list Reply</button>
		<button onclick="getAllList()">list all</button>
	</div>
	<ul id="list">  
		
	</ul>
	<script id="template" type="text/x-handlerbars-template">
		{{#each.}}
		<li class="item">
			<div class='listRno'>{{rno}}</div>
			<div>작성자 : {{replyer}}</div>
			<div>작성내용 : {{replytext}}</div>
			<div>{{replydate}}</div>
   			<button class='del'>삭제</button>
		</li> 
		{{/each}}
	</script>
	
	<ul class="pagination">
		
	</ul>
</body>
</html>