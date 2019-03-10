$(function(){
   var url = location.search; //获取url中"?"符后的字串  
   var theRequest = new Object();  
   if (url.indexOf("?") != -1) {  
      var str = url.substr(1);  
      strs = str.split("&");  
      for(var i = 0; i < strs.length; i ++) {  
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
      }  
   };
   var root = window.location.host;
   //获取表单
   $.ajax({
		type:"post",
		url:root+"/customForm/getCustomFormByFormId",
		async : false,
		data:{"formId":theRequest["formId"]},
		datatype : "json",
		success : function(data) {
			 if(200==data.code){
				 $("#tableHtml").html(data.data.html);
			 }
		},
		error:function(this) {
			 
		}
	});
   
})

