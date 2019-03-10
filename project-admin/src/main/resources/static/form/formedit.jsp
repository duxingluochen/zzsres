<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>WEB表单设计器</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="author" content="leipi.org">
<link href="${pageContext.request.contextPath}/res/css/bootstrap/css/bootstrap.css?2023" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/res/css/site.css?2023" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/TextEdit/dist/bootstrap.css" />
<style type="text/css">
html, body {
	height: 100%;
}

body {
	background: white !important;
}

.main {
	padding: 20px;
	height: 100%;
}

.main>DIV {
	background: #fff;
	border-radius: 5px;
	padding: 10px;
	height: 100%;
	overflow: auto;
}

.main .row {
	margin: 0;
}

.main .alert .close {
	right: 10px;
}

.main .nav-header {
	text-align: center;
	padding: 7px 15px;
	background: #EDF0F5;
	border-left: 5px solid #29ADEB;
	font-size: 20px;
	font-weight: bold;
	color: #29ADEB;
}

.main .nav-list a {
	color: #444;
}

.edui-default .edui-editor, .edui-default .edui-editor-iframeholder {
	width: 100% !important;
}

.edui-default .edui-editor-iframeholder {
	/* height: auto!important; */
	height: 700px !important;
}

.biao {
	margin: 3px;
	background-color: #5f9ce8;
	width: 100%;
	color: white;
}
</style>
<script type="text/javascript">
  var rootPath = '<%=basePath%>';
</script>
</head>
<body>
	<div class="main">
		<div>
			<form method="post" id="saveform" name="saveform" action="./save">
				<input type="hidden" name="fields" id="fields" value="0"> <input type="hidden" name="formeditor" id="formeditor"> <input type="hidden" name="data" id="data"> <input type="hidden" name="form_id" id="form_id"> <input type="hidden" name="type" id="type" value='1'>
				<div class="row">
					<div class="col-md-12">
						<form role="form">
							<div class="form-group">
								<label class="radio-inline"> 表单名称: <input style="height: 30px;" type="text" id="form_name" name="form_name" value="${form_name }">

								</label> 显示关联表单按钮: <label class="radio-inline"> <input type="radio" id="radio1" name="radio" value="1" checked="checked">否
								</label> <label class="radio-inline"> <input type="radio" id="radio2" name="radio" value="2">是
								</label> 被成为关联表单: <label class="radio-inline"> <input type="radio" id="rad1" name="rad" value="1" checked="checked">否
								</label> <label class="radio-inline"> <input type="radio" id="rad2" name="rad" value="2">是
								</label>
							</div>
						</form>
					</div>
				</div>
				<div class="alert">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<strong>提醒：</strong>单选框和复选框，如：
					<code>{|-</code>
					选项
					<code>-|}</code>
					两边边界是防止误删除控件，程序会把它们替换为空，请不要手动删除！<span style="color: red;">注意控件是点击不是拖拽</span>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('text');" class="btn btn-link biao">文本框</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('textarea');" class="btn btn-link biao">多行文本</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('select');" class="btn btn-link biao">下拉菜单</a>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('datepicker');" class="btn btn-link biao">日期选择框</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('listctrl');" class="btn btn-link biao">列表控件</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('listctrls');" class="btn btn-link biao">可设置计算的列表控件框</a>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('relation');" class="btn btn-link biao">关联表单文本框</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('textareams');" class="btn btn-link biao">关联多文本框</a>
							</div>
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('checkboxs');" class="btn btn-link biao">复选框</a>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<a href="javascript:void(0);" onclick="leipiFormDesign.exec('radios');" class="btn btn-link biao">单选框</a>
							</div>
						</div>
					</div>

					<div class="col-md-12" style="font-size: 16px; font-weight: bold; text-align: center; margin-top: 15px; margin-bottom: 13px">
						<hr>
						表单编辑区
					</div>
					<div class="col-md-12">
						<script id="myFormDesign" type="text/plain" style="width: 100%;">
						 ${form_template} 
						</script>
					</div>
				</div>
			</form>

			<div align="center" id="center">
				<button type="button" id="save" class="btn btn-info">保存</button>
				<button type="button" onclick="javascript :history.back(-1);" class="btn btn-info">返回</button>
			</div>
		</div>
	</div>
	<!--end container-->
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/jquery-1.7.2.min.js?2023"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/ueditor/ueditor.config.js?2023"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/ueditor/ueditor.all.min.js?2023"> </script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/ueditor/lang/zh-cn/zh-cn.js?2023"></script>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/res/js/ueditor/formdesign/leipi.formdesign.v4.js?2023"></script>
	<!-- script start-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/res/view/form/form_edit.js"> </script>

</body>
</html>