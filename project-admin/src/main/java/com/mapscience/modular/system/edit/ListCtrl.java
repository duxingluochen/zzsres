package com.mapscience.modular.system.edit;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ListCtrl extends FormField {

	private String orgtitle ; // "T1`T2`T3`" 
	private String orgcoltype ; //"text`text`text`"
	private String orgunit ;// "```"
	private String orgsum ;//"0`0`0`" 
	private String orgcolvalue ;//"```"
	private String orgkey ; // 实际存储每个列需要的key
	public String getOrgtitle() {
		return orgtitle;
	}
	public void setOrgtitle(String orgtitle) {
		this.orgtitle = orgtitle;
	}
	public String getOrgcoltype() {
		return orgcoltype;
	}
	public void setOrgcoltype(String orgcoltype) {
		this.orgcoltype = orgcoltype;
	}
	public String getOrgunit() {
		return orgunit;
	}
	public void setOrgunit(String orgunit) {
		this.orgunit = orgunit;
	}
	public String getOrgsum() {
		return orgsum;
	}
	public void setOrgsum(String orgsum) {
		this.orgsum = orgsum;
	}
	public String getOrgcolvalue() {
		return orgcolvalue;
	}
	public void setOrgcolvalue(String orgcolvalue) {
		this.orgcolvalue = orgcolvalue;
	}
	 
	public String getOrgkey() {
		return orgkey;
	}
	public void setOrgkey(String orgkey) {
		this.orgkey = orgkey;
	}
	public static ListCtrl parseText(String text) {
		ListCtrl t = CommonUtils.parseInputAttrsT(text , ListCtrl.class);
		
		return t;
	}
	@Override
	public String getView(String value) {
		
		JSONObject jsonValue = null ;
		try{
			if(value != null && value.trim().length() > 1)
				jsonValue =  JSON.parseObject(value);
		}catch(Exception e) {
			System.out.println(e);
			jsonValue = null;
		}
		return parseListCtrl(this, 2, jsonValue);
	}
	@Override
	public String getEdit(String value) {
		JSONObject jsonValue = null ;
		try{
			if(value != null && value.trim().length() > 1)
				jsonValue =  JSON.parseObject(value);
		}catch(Exception e) {
			System.out.println(e);
			jsonValue = null;
		}
		return parseListCtrl(this, 1, jsonValue);
	}
	
	
	/**
	 * 解析listctrl
	 * 2018-01-25 listctrl对单位和默认值的拼装
	 * 
	 * @param listCtrilData ueform中 data里的listctrl 整个json
	 * @param type 1-编辑模式 2-纯浏览模式 采用parse替换
	 * @param value 填充的值,可以为空
	 * @return
	 */
	private String parseListCtrl(ListCtrl lc , int type , JSONObject listCtrlValue) {
		String name = lc.getName();
		String temp_html = type != 1 ? "" :"<script> " + 
		 " function tbAddRow(dname) " + 
	         "  { " + 
	         " var sTbid = dname+'_table';" + 
	         " var size = parseInt($('#' + dname + '_length').val())+1;  " + 
	         " var fields = $('#' + dname + '_field').val() ;  " + 
	         " var fs =  fields.split('`'); "  + 
	         "  var table = '<tr>' ; "+ 
	         " for(var i = 0 ; i < fs.length ; i++) {"+ 
			"	 if(fs[i] && fs[i] != '') {"+
			"		 table += '<td><input class=\"input-medium\" type=\"text\" name=' + dname + '[' + (size+1) + '][' + fs[i] + ']  value=\"\"> </td>';"+
				" }" +
			"}  " + 
			"  		 table += '<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>';"  + 
			 "   table += '</tr>';" +
	         "  $('#' + dname + '_length').val(size+1); " +  
	         "$('#last').before(table) " +
	        " }" + 
	          // 删除tr
	        " function fnDeleteRow(obj)" + 
	        " {" + 
	          "   var sTbid = '"+ name +"_table';" + 
	          "   var oTable = document.getElementById(sTbid);" + 
	          "   while(obj.tagName !='TR')" + 
	           "  {" + 
	          "       obj = obj.parentNode;" + 
	           "  }" + 
	           "  oTable.deleteRow(obj.rowIndex);" + 
	           "  var size = $('#" + name + "_length').val(); " + 
	           "  $('#" + name + "_length').val(size - 1);  " +
	        " }" + 
	     "</script>"; 
		 String[] fields =  lc.getOrgtitle().split("`");
		 String[] filed_keys =  lc.getOrgkey().split("`");
		 String table = "<table id=\"" + name +"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>" ;
		 table += " <tr><th colspan=\"" +  fields.length  + "\">" + getTitle();
		 if(type == 1) {
			 table += "<span class=\"pull-right\"> "+ 
			         "<button class=\"btn btn-small btn-success\" type=\"button\" onclick=\"tbAddRow('" + name + "')\">添加一行</button>"+ 
			         "</span> ";
		 }
		 table += "</th></tr><tr>" ;
		 for(int i = 0 ; i < fields.length ; i++) {
			 if(fields[i] != null && fields[i].length() > 0)
				 table += "<th>" + fields[i] + "</th>" ;			 
		 }
		 table += "</tr></thead> <tbody id=\"tbId\">" ;
		 if(type == 2) {
			  //纯查看模式
			 if(listCtrlValue != null) {
				 Set<String> keys =  listCtrlValue.keySet() ;
				 for(String key : keys) {
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\" id=\"first\">";
					 for(int i = 0 ; i < fields.length && i < filed_keys.length && i < _key_v.size() ; i++) {
						 if(fields[i] != null && fields[i].length() > 0)
							 table += "<td>"+ _key_v.getString(filed_keys[i]) + "</td>" ;
					 }
					 table += " </tr>";
				 }
			 }
			
		 } else {
			 table += " <tr class=\"template\" id=\"first\">";
			 if(listCtrlValue != null) {
				 Set<String> keys =  listCtrlValue.keySet() ;
				 int index = 0;
				 for(String key : keys) {
					 
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\">";
					 for(int i = 0 ; i < fields.length && i < filed_keys.length && i < _key_v.size() ; i++) {
						 if(filed_keys[i] != null && filed_keys[i].length() > 0) 
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[" + index + "][" + filed_keys[i] + "]\" value=\"" + _key_v.getString(filed_keys[i]) + "\"> </td>" ;
						 
					 }
					 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>" ;
					 table += " </tr>";
					 index++;
				 }
			 } else {
				 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
					 if(filed_keys[i] != null && filed_keys[i].length() > 0)
						 if(i < fields.length-1 && i < filed_keys.length-1) {
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\" > </td>" ;
						 }else {
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\" > </td>" ;
						 }
				 }
 
				 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow hide\">删除</a></td>" ;
				 table += " </tr>";
			 }
			 table += "<tr class=\"template\" id=\"last\"> ";
			 table += "</tr> ";
			int size = listCtrlValue != null ? listCtrlValue.size() : 0 ;
			 table += "<input type=\"hidden\" id='" + name + "_length' value='" + size + "' >" ;
			 table += "<input type=\"hidden\" id='" + name + "_field' value='" + lc.getOrgkey() + "' >" ;
		 }
		 table += " </tbody> </table>";
		 String content = temp_html + "<p>" + table + "</p>" ;
		 return content ;
		
	}
}
