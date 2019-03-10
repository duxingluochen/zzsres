var url = location.search; //获取url中"?"符后的字串  
var theRequest = new Object();  
if (url.indexOf("?") != -1) {  
  var str = url.substr(1);  
  strs = str.split("&");  
  for(var i = 0; i < strs.length; i ++) { 
	  var index =strs[i].indexOf("=");
	  theRequest[strs[i].split("=")[0]]=unescape(strs[i].substr(index+1));  
  }  
}  

var leipiEditor = UE.getEditor('myFormDesign',{
    allowDivTransToP: true,//阻止转换div 为p
    toolleipi:true,//是否显示，设计器的 toolbars
    textarea: 'design_content',   
    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
    toolbars:[[
    'fullscreen', 'source', '|', 'undo', 'redo', '|',
    'bold', 'italic', 'underline', 'fontborder',
    'strikethrough',  'removeformat', '|', 'forecolor',
    'backcolor', 'insertorderedlist', 'insertunorderedlist','|',
    'fontfamily', 'fontsize', '|', 'indent', '|', 'justifyleft',
    'justifycenter', 'justifyright', 'justifyjustify', '|',  'link',
    'unlink',  '|',  'horizontal',  'spechars',  'wordimage', '|',
    'inserttable', 'deletetable',  'mergecells',  'splittocells']],
    //关闭字数统计
    wordCount:false,
    //关闭elementPath
    elementPathEnabled:false,
    //默认的编辑区域高度
    initialFrameHeight:600
});
 var leipiFormDesign = {
    /*执行控件*/
    exec : function (method) {
        leipiEditor.execCommand(method);
    } 
};
 
 var callbackdata = function () {
	 var data ={};
	 if(leipiEditor.queryCommandState( 'source' ))
         leipiEditor.execCommand('source');/*切换到编辑模式才提交，否则部分浏览器有bug*/
     if(leipiEditor.hasContents()){
         leipiEditor.sync();       /*同步内容*/
         //获取表单设计器里的内容
         template=leipiEditor.getContent();
         var formName = $("#formName").val();
         var merchantId = $("#merchantId").val();
         data["template"]=template;
         if(formName==null || formName=="" ||formName==undefined){
        	 swal({title: "表单名不能为空！", type: "error",confirmButtonText: "知道了"},function() {return null });
         }
         if(template==null || template=="" || template==undefined){
        	 swal({title: "预览对象不能为空！", type: "error",confirmButtonText: "知道了"},function() { return null});
         }
         data["formName"]=formName;
         data["merchantId"]=merchantId;
     }
	return data;
}

 
 $('#preview').click(function(){
	 if(leipiEditor.queryCommandState( 'source' ))
         leipiEditor.execCommand('source');/*切换到编辑模式才提交，否则部分浏览器有bug*/
     if(leipiEditor.hasContents()){
         leipiEditor.sync();       /*同步内容*/
         //获取表单设计器里的内容
         template=leipiEditor.getContent();
         var base = new Base64();
         template = base.encode(template);
         //异步提交数据
         window.open('previewForm.html?template='+template,'_blank');
     } else {
    	 return false;
     }
 });
 
 /**
  * 保存自定义表单
  * @returns
  */
 $('#save').click(function(){
	 if(leipiEditor.queryCommandState( 'source' ))
         leipiEditor.execCommand('source');//切换到编辑模式才提交，否则有bug
     if(leipiEditor.hasContents()){
    	 /*同步内容*/ 
         leipiEditor.sync();
         //获取表单设计器里的内容
         var template=leipiEditor.getContent(); 
         var formName = $("#formName").val();
         if(!formName) {
        	 swal({title: "表单名不能为空！", type: "error",confirmButtonText: "知道了"},function() { });
        	 return false;
         }
         var merchant = theRequest["merchant"];
          //异步提交数据
          $.ajax({
             type: 'POST',
             url : '/customForm/saveOrUpdateCustomForm',
             data : {'template': template , 'formName':formName, "merchant":merchant},
             async : false,
     		 datatype : "json",
             success : function(data){
            	 swal({title: "表单：【"+formName+"】保存成功！", type: "success",confirmButtonText: "知道了"},function() { });
             },error : function(errorDate) {
            	swal({title: "前端访问出错请刷新界面！", type: "error",confirmButtonText: "知道了"},function() { });
     			console.log(errorDate);
     		}
         });
     } else {
    	 swal({title: "未定义表单不能保存！", type: "error",confirmButtonText: "知道了"},function() { });
         return false;
     }
 });
