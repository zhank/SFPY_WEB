$(function(){
	$("#login").click(function(){
		var userName = $("#userName").val();
		var password = $("#password").val();
		if (userName == "" || password == "") {
			alert("请输入用户名或密码！");
			return;
		}
		$.ajax({
			url:"http://localhost:8088/user/login.do",
			type:"post",
			data: {"userName":userName,"password":password},
			dataType:"json",
			success:function(result){
				window.location.href = "mainPage.html";  
			},
			error:function(XMLHttpRequest, textstatus,errorThrown) {
				alert(XMLHttpRequest.status);
				alert(XMLHttpRequest.readystate);
				alert(textstatus);
			}
		});
	});
});
