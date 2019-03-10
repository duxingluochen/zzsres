//现在字段
var cols= [[
    {field:'formId',  title: '表单Id', align: 'center', width:'20%'}
    ,{field:'formName',  title: '表单名', align: 'center', width:'12%'}
    ,{field:'merchantName',  title: '应用系统名', align: 'center', width:'12%'}
    ,{field:'status',  title: '状态', templet: '#status', unresize: true, minWidth:70, width:'8%', align: 'center'}
    ,{field:'createTime', title: '创建时间', align: 'center', width:'20%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.createTime);
    }} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
    ,{field:'updateTime', title: '更新时间', align: 'center', width:'20%', sort: true, templet: function(res){
        return DateUtils.dateFormat(res.updateTime);
    }}
    ,{field:'', width:137, title: '操作', align: 'center', width:'8%', toolbar: '#barDemo'}
  ]];

layui.use('table', function(){
  var table = layui.table
  ,form = layui.form;
  var tableIns = table.render({
    elem: '#table'
    ,url:'/customForm/getPlatCustomFormPage'
    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
    ,cols:cols
	,request: {
	  pageName: 'current' //页码的参数名称，默认：page
	  ,limitName: 'size' //每页数据量的参数名，默认：limit
	}
  	,title: '流程类型一级表'
    ,toolbar: '#toolbarDemo'
    ,page: true
    ,limits: [10, 15, 20]
  	,limit:10
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
  
  //头工具栏事件
  table.on('toolbar(table)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'add'://增加
    	  	layer.open({
    	        type: 2 //此处以iframe举例
    	        ,title: [ '新增应用程序', 'font-size:18px;' ]
    	        ,area: ['100%', '100%']
    	        ,shade: 0
    	        ,maxmin: false
    	        ,id: 'LAY_layuipro'
    	        ,btnAlign: 'c'
    	        ,moveType: 0
    	        ,content: '/customForm/createIndex?flag=true'
    	        ,btn: ['预览', '保存','关闭'] //只是为了演示
    	        ,btn1: function(index){
    	        	//当点击‘确定’按钮的时候，获取弹出层返回的值
    	            var data = window["layui-layer-iframe" + index].callbackdata();
    	        }
    	        ,btn2: function(index){
    	        	//当点击‘确定’按钮的时候，获取弹出层返回的值
    	            var data = window["layui-layer-iframe" + index].callbackdata();
    	            var formName = data["formName"];
    	            if(formName==null || formName==""){return false}
    	            var template = data["template"];
    	            if(template==null || template==""){return false}
    	        	 $.ajax({
      	         	   url : "/customForm/saveOrUpdateCustomForm",
      	         	   type:"POST",
      	                data:data,
      	                dataType:"json",
      	                success:function(data){
      	             	   if(data.code==200){
      	             		  tableIns.reload();
      	             		  layer.close(index);
  	    	         		}else if(data.code==500){
  	    	         		   layer.msg(data.message);
  	    	         		}
      	                }
      	         	});
    	        }
    	        ,btn3: function(){
      	          layer.closeAll();
      	        }
    	        ,success: function(layero){
    	          layer.setTop(layero); //重点2
    	        }
    	      });
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
    };
  });
});


var add = function(data){
	
}