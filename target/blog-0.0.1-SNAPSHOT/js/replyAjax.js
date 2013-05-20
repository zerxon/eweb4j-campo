$(function(){
	$("#submit").click(function(){
		$("#label_error").html('');
		
		var data=$("#textarea").val();
		if($.trim(data)=="")
		{
			$("#label_error").html('回复不能为空');
			return;
		}
		
		$.ajax({
			type: "POST",
			url: "/reply/doReply",
			data:{
				 "reply.comment": data,
				 "article.id": $("#bid").val()
			},
			dataType: "JSON",
			beforeSend: function (XMLHttpRequest) {
                    $("#loading").css("visibility","visible");
            },
			success: function(json){
				if(json.code == 1)
				{
					$("#textarea").val('');
					$("#wmd-preview").html('');
					
					var tReply=$("#t-reply").html();
					var arrData={
							replyDate: json.data.displayAddTime,
							replyComment: json.data.htmlComment,
							replyUserName: json.data.user.name,
							replyUserAvatar: json.data.user.gravatar
					}
					
					console.log(json.data);
					var html=Template.parse(arrData, tReply);
					
					$("#replies").append(html);
					$("#loading").css("visibility","hidden");	
				}
				else
				{
					$("#label_error").html('回复失败');
				}
			}
		});
	});
});