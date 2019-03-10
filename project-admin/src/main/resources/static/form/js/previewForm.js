//初始化
$(function(){
	var template = GetUrlParam("template");
	var base = new Base64();
    template = base.decode(template);
    $.ajax({
       type: 'POST',
       url : '/customForm/previewCustomForm',
       data : {'template': template, "flag":false },
       async : false,
	   datatype : "json",
       success : function(data){
    	   $("#tableHtml").html(data.data.html);
       },error : function(errorDate) {
      	swal({title: "前端访问出错请刷新界面！", type: "error",confirmButtonText: "知道了"},function() { });
			console.log(errorDate);
		}
   });
})


 //返回
var back = function(){
	window.opener=null;
	window.open('','_self');
	window.close();
}


//paraName 等找参数的名称
function GetUrlParam(paraName) {
　　　　var url = document.location.toString();
　　　　var arrObj = url.split("?");
　　　　if (arrObj.length > 1) {
　　　　　　var arrPara = arrObj[1].split("&");
　　　　　　var arr;
　　　　　　for (var i = 0; i < arrPara.length; i++) {
　　　　　　　　arr = arrPara[i].split("=");
　　　　　　　　if (arr != null && arr[0] == paraName) {
　　　　　　　　　　return arr[1];
　　　　　　　　}
　　　　　　}
　　　　　　return "";
　　　　}
　　　　else {
　　　　　　return "";
　　　　}
　　}