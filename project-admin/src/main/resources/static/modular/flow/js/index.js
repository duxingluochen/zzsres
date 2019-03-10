$(function(){
	/*tab切换*/
	$(".contMenu li").click(function(){
		$(".contMenu li").eq($(this).index()).addClass("on").siblings().removeClass("on");
		var index =  $(this).index();
        $(".contList>div").hide().eq($(this).index()).show();
        getData(index);
	});
	/*打开模态框*/
	$(".openModal").click(function(){
		$(".modal").show();
	});
	/*关闭模态框*/
	$(".close,.reset").click(function(){
		$(".modal").hide();
	})
})