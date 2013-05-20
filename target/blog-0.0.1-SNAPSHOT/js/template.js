
var Template={
		parse: function (object, template){
		    if(template){
	
		        for(var item in object){
	
		            var key="{#"+item+"}";
		            var reg=new RegExp(key,"g");
		            template=template.replace(reg,object[item]);
		        }
		    }
		    
		    return template;
		}
}