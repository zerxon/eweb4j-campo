$(function(){
	$("#mark").click(function(){
		var articleId=$("#mark").attr("name");
		
		process(articleId);
	});
	
	function process(id){
		$.ajax({
			type: "GET",
			url: "/mark/article/"+id,
			data: "",
			dataType: "JSON",
			beforeSend: function () {
                    $("#loading").css("visibility","visible");
            },
			success: function(json){
				if(json.code == 1 || json.code == 2){
					var count=$("#collect_total").attr("rel");
					count=parseInt(count);
					count=isNaN(count)?0:count;
					
					if(json.code == 1){
						$("#mark").text("已收藏");
						count+=1;
					}
					else{
						$("#mark").text("收藏");
						count-=1;
					}
					
					if(count<1)
						$("#collect_total").text('');
					else
						$("#collect_total").text(count+"人收藏");
					
				}
				
				$("#loading").css("visibility","hidden");
			}
		});
	}
	
});