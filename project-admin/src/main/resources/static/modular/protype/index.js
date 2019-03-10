//现在字段
var cols= [[
    {field:'typeId',  title: 'ID', align: 'center', width:'12%'}
    ,{field:'typeName',  title: '类型名', align: 'center', width:'12%'}
    ,{field:'sort',  title: '排序序号', align: 'center', width:'12%'}
    ,{field:'status',  title: '状态', templet: '#status', unresize: true, minWidth:70, width:'12%', align: 'center'}
    ,{field:'createTime', title: '创建时间', align: 'center', width:'20%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.createTime);
    }} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
    ,{field:'updateTime', title: '更新时间', align: 'center', width:'20%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.updateTime);
    }}
    ,{field:'', width:137, title: '操作', align: 'center', width:'12%', toolbar: '#barDemo'}
  ]];

var cols2= [[
    {field:'typeId',  title: 'ID', align: 'center', width:'12%'}
    ,{field:'typeName',  title: '类型名', align: 'center', width:'12%'}
    ,{field:'sort',  title: '排序序号', align: 'center', width:'12%'}
    ,{field:'status',  title: '状态', templet: '#status', unresize: true, minWidth:70, width:'12%', align: 'center'}
    ,{field:'createTime', title: '创建时间', align: 'center', width:'26%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.createTime);
    }} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
    ,{field:'updateTime', title: '更新时间', align: 'center', width:'26%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.updateTime);
    }}
  ]];

layui.use('table', function(){
  var table = layui.table
  ,form = layui.form;
  table.render({
    elem: '#table'
    ,url:'/proType/findPageProType'
    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
    ,cols:cols
	,request: {
	  pageName: 'current' //页码的参数名称，默认：page
	  ,limitName: 'size' //每页数据量的参数名，默认：limit
	}
  	,title: '流程类型一级表'
    ,toolbar: '#toolbarDemo'
    ,page: true
    ,limits: [5, 10]
  	,limit:5
  	,response: {
      statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0 
  	 }
  	,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
      return {
	    "code": res.code, //解析接口状态
        "msg": res.message, //解析提示文本
		"count": res.data.total,
        "data": res.data.records //解析数据列表
      };
    },
	done: function (res, curr, count) {// 表格渲染完成之后的回调
		  var that = this.elem.next();
	    res.data.forEach(function (item, index) {
	  	  //console.log(item.empName);item表示每列显示的数据
	       if (index%2==0) {
	    	   var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "#f2f2f2");
	        } 
	    });
	}

  });
  
  //监听状态
  form.on('switch(statusDemo)', function(obj){
    layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
  });
});


layui.use('table', function(){
  var table = layui.table
  ,form = layui.form;
  table.render({
    elem: '#table2'
    ,url:'/proType/findPageProType'
    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
    ,cols:cols2
	,request: {
	  pageName: 'current' //页码的参数名称，默认：page
	  ,limitName: 'size' //每页数据量的参数名，默认：limit
	}
  	,title: '流程类型二级表'
    ,page: true
    ,limits: [5, 10]
  	,limit:5
  	,response: {
      statusCode: 200 //重新规定成功的状态码为 200，table 组件默认为 0 
  	 }
  	,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
      return {
	    "code": res.code, //解析接口状态
        "msg": res.message, //解析提示文本
		"count": res.data.total,
        "data": res.data.records //解析数据列表
      };
    },
	done: function (res, curr, count) {// 表格渲染完成之后的回调
		  var that = this.elem.next();
	    res.data.forEach(function (item, index) {
	  	  //console.log(item.empName);item表示每列显示的数据
	       if (index%2==0) {
	    	   var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "#f2f2f2");
	        } 
	    });
	}

  });
  
  //监听状态
  form.on('switch(statusDemo)', function(obj){
    layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
  });
});

