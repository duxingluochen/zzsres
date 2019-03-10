$(function() {

	// 点击回车登录
	$(document).ready(function() {
		// 回车提交事件
		$("body").bind('keydown', function(event) {
			if (event.keyCode == 13) {
				$(".login-btn").click();
			}
		});
	});

	// 登录
	$(".login-btn").click(function() {
		var valid = $('#form').Validform();
		if (!valid) {
			return;
		}
		var data = $("#form").serializeObject();
		$.ajax({
			method : "post",
			url : "/login",
			data : data,
			async : false,
			datatype : "json",
			success : function(result) {
				if (result.code == 200) {
					location.href = "/";
				} else {
					alert(result.message);
				}
			},
			error : function(errordata) {
				alert("登录页面请求异常！");
			}
		});
	});
});

$(".app-btn").click(function() {
	$(".appCode").slideDown()
});
$(".closeA").click(function() {
	$(".appCode").hide();
});

$.fn.serializeObject = function () {
   var o = {};
   var obj = this.serializeJSON();
   var $checks = $('input[type=checkbox]' , this);
   $.each($checks,function(){
    	var name = this.name; 
        var c_vs = obj[this.name];
        if(!(c_vs instanceof Array) ){
        	obj[this.name] = [];
        }
        var checked = this.checked; 
        if(checked ) {
        	obj[this.name].push(this.value);
        }
    });
    
    var $radio = $('input[type=radio]',this);
    $.each($radio,function(){
        if(!obj.hasOwnProperty(this.name)){
            obj[this.name] = '';
        }
    });
    return obj;
};