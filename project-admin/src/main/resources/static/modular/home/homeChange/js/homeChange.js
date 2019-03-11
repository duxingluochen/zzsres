$(function(){
	//当前时间
    setInterval(function() {
        var date = new Date();

        var yy = date.getFullYear(); //年
        var MM = date.getMonth() + 1; //月
        var dd = date.getDate(); //日
        var hh = date.getHours(); //时
        var mm = date.getMinutes(); //分
        var ss = date.getSeconds(); //秒
        var week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()]; //星期

        if (hh < 10) {
            hh = "0" + hh
        }
        if (mm < 10) {
            mm = "0" + mm
        }
        if (ss < 10) {
            ss = "0" + ss
        }

        $(".time").html(yy + "年" + MM + "月" + dd + "号" + "&nbsp; &nbsp;" + week + "&nbsp; &nbsp;" + hh + ":" + mm + ":" + ss);

    }, 1000);
});