var callbackdata = function () {
	var baseData = $(".layui-form").serializeArray();
	var frondate = {};
	for(x in baseData){
		frondate[baseData[x].name]=baseData[x].value;
	}
	return frondate;
}