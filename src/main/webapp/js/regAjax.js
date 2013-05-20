$(function(){
	var ckName=false;
	var ckUpwd=false;
	var ckCpwd=false;
	var ckEmail=false;
	
	$("#name").blur( function(){ ajaxcheck($("#name")); } );
	$("#email").blur( function(){ ajaxcheck($("#email")); } );
	
	$("#pwd").blur( function(){ 
		$("#label_pwd").removeClass();
		var v = $("#pwd").val();
		if(v.length < 6 || v.length > 20)
		{
			$("#label_pwd" ).addClass('error');
			$("#label_pwd").html("密码长度要求在6-20个字符内！");
			ckUpwd=false;
		}
		else
		{
			$("#label_pwd" ).addClass('ok');
			$("#label_pwd").html("符合要求");
			ckUpwd=true;
		}
	} );
	
	$("#repwd").blur( function(){ 
		$("#label_cpwd").removeClass();
		var v = $("#repwd").val();
		if(v.length < 6 || v.length > 20)
		{
			$("#label_cpwd" ).addClass('error');
			$("#label_cpwd").html("密码长度要求在6-20个字符内！");
			ckCpwd=false;
			return ;
		}
		if(v != $("#pwd").val())
		{
			$("#label_cpwd" ).addClass('error');
			$("#label_cpwd").html("两次输入的密码不一致！");
			ckCpwd=false;
			return ;
		}
		$("#label_cpwd" ).addClass('ok');
		$("#label_cpwd").html("两次密码一致");
		ckCpwd=true;
	} );
	
	$("#submit").click(function(){
		if($.trim($("#name").val())!="" && $.trim($("#email").val())!="" && $.trim($("#pwd").val())!="" && $.trim($("#repwd").val())!=""){
			if(ckName && ckUpwd && ckCpwd && ckEmail)
				return true;
			else
				return false;
		}
		else{
			return false;
		}
	});
	
	function ajaxcheck(obj)
	{
		var id = obj.attr("id");
		$("#label_" + id).removeClass();
		
		var url="";
		var data="";
		
		if(id=='name'){
			url="/user/check_user_name";
			data="user_name="+encodeURIComponent(obj.val());
		}
		else{
			url="/user/check_email";
			data="email="+encodeURIComponent(obj.val());
		}
		
		$.ajax({
			type: "GET",
			url: url,
			data: data,
			beforeSend: function (XMLHttpRequest) {
                    $("#label_" + id).html('正在检测...');
            },
			success: function(code){
				if(code == 1)
				{
					$("#label_" + id).addClass('ok');
					$("#label_" + id).html('符合要求');
					if(id=='name')
						ckName=true;
					else if(id=='email')
						ckEmail=true;
				}
				else
				{
					var error="";
					
					if(code == -1)
						error="仅限英文、数字、下划线，3-10字符";
					else if(code == -2)
						error="邮箱格式不正确"
					else if(code == -3)
						error="该用户名已存在";
					else if(code == -4)
						error="该邮箱已存在";
						
					$("#label_" + id).addClass('error');
					$("#label_" + id).html(error);
					
					if(id=='name')
						ckName=false;
					else if(id=='email')
						ckEmail=false;
				}
			} 
		});
	}
});