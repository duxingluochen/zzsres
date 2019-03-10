package com.mapscience.modular.system.edit;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.mapscience.core.util.common.ObjectUtil;

public class ListCtrls extends FormField {

	private String orgtitle ; // "T1`T2`T3`" 
	private String orgcoltype ; //"text`text`text`"
	private String orgunit ;// "```"
	private String orgsum ;//"0`0`0`" 
	private String orgcolvalue ;//"```"
	private String orgkey ; // 实际存储每个列需要的key
	private String toaltextname;
	private String toaltext;
	public String getToaltextname() {
		return toaltextname;
	}
	public void setToaltextname(String toaltextname) {
		this.toaltextname = toaltextname;
	}
	public String getToaltext() {
		return toaltext;
	}
	public void setToaltext(String toaltext) {
		this.toaltext = toaltext;
	}
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
	public static ListCtrls parseText(String text) {
		ListCtrls t = CommonUtils.parseInputAttrsT(text , ListCtrls.class);
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
	private String parseListCtrl(ListCtrls lc , int type , JSONObject listCtrlValue) {
		String name = lc.getName();
		String jexitoaltext = lc.getToaltext();
		Set<String> set = Sets.newHashSet();
		for (String string : jexitoaltext.split("[\\+\\-\\*\\/()]")) {
			if(ObjectUtil.isNotEmpty(string)) {
				set.add(string.trim());
			}
		}
		Iterator<String> ite= set.iterator();
		String sett = "[";
		for (int i = 0; i < set.size(); i++) {
			if(i==0) {
				sett +="\""+ite.next()+"\"";
			}else {
				sett +=",\""+ite.next()+"\"";
			}
		}
		sett +="]";
		//判断有几个
		String temp_html = type != 1 ? "" :"<script> " + 
		 " function tbAddRow(dname) " + 
	         "  { " + 
	         " var set = "+sett+";"+
	         " var sTbid = dname+'_table';" + 
	         " var size = parseInt($('#' + dname + '_length').val())+1;  " + 
	         " var fields = $('#' + dname + '_field').val() ;  " + 
	         " var fs =  fields.split('`'); "  + 
	         "  var table = '<tr id=\""+name+"id'+(size+1)+'\">' ; debugger;"+ 
	         " for(var i = 0 ; i < fs.length ; i++) {"+ 
			 "	 if(fs[i] && fs[i] != '') {"+
	         "      var fas=false;       "+
	         "for(x in set){"+ 
	         "if(set[x]==fs[i])"+
	         	"fas=true;"+
	         "}"+ 
	         "if(fas){"
				 +"		 table += '<td><input class=\"input-medium\" type=\"text\"   oninput=\"sum(\\'"+name+"\\',\\'"+lc.toaltext+"\\');\" onpropertychange=\"sum(\\'"+name+"\\',\\'"+lc.toaltext+"\\');\"   name=' + dname + '[' + (size+1) + '][' + fs[i] + ']  value=\"\" "
		         + " onkeyup=\"this.value=this.value.replace(/[^0-9.]/g,\\'\\')\""
		         		+ "> </td>';"
				+ "}"
				+ "else{"
				+"		 table += '<td><input class=\"input-medium\" type=\"text\"   oninput=\"sum(\\'"+name+"\\',\\'"+lc.toaltext+"\\');\" onpropertychange=\"sum(\\'"+name+"\\',\\'"+lc.toaltext+"\\');\"   name=' + dname + '[' + (size+1) + '][' + fs[i] + ']  value=\"\"> </td>';"+
				"}"+ 
				"}" +
				"}" + 
				"table += '<td><input class=\"input-medium\" type=\"text\" name=' + dname + '[' + (size+1)+'][toaltext'+dname+(size+1)+']  value=\"\"></td>';"  + 
			"table += '<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>';"  + 
			 "table += '</tr>';" +
	         " $('#' + dname + '_length').val(size+1); " +  
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
	           "sum('"+name+"','"+lc.toaltext+"'); "+
	        " }" + 
	     "</script>"; 
		 String[] fields =  lc.getOrgtitle().split("`");
		 String[] filed_keys =  lc.getOrgkey().split("`");
		 String table = "<table id=\"" + name +"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>" ;
		 table += " <tr><th colspan=\"" +  (fields.length+1)  + "\">" + getTitle();
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
		 table += "<th>合计</th>" ;
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
					 table +=  "<td><input class=\"input-medium\" type=\"text\" name=\"toaltext" + name + "[" + index + "]\" value=\"\"> </td>" ;
					 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow show\">删除</a></td>" ;
					 table += " </tr>";
					 index++;
				 }
			 } else {
				 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
					 if(filed_keys[i] != null && filed_keys[i].length() > 0) {
						 if(set.contains(filed_keys[i])) {
							 table += "<td><input class=\"input-medium\" type=\"text\" oninput=\"sum('"+name+"','"+lc.toaltext+"');\" onpropertychange=\"sum('"+name+"','"+lc.toaltext+"');\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\" "
							 		+ " onkeyup=\"this.value=this.value.replace(/[^0-9.]/g,'')\""
							 		+ "> </td>" ;
						 }else {
							 table += "<td><input class=\"input-medium\" type=\"text\" oninput=\"sum('"+name+"','"+lc.toaltext+"');\" onpropertychange=\"sum('"+name+"','"+lc.toaltext+"');\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\" > </td>" ;
						 }
						}
				 }
				 table +=  "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][toaltext"+name+"0]\" value=\"\"> </td>" ;
				 table += "<td><a href=\"javascript:void(0);\" onclick=\"fnDeleteRow(this)\" class=\"delrow hide\">删除</a></td>" ;
				 table += " </tr>";
			 }
			 
			 table += "<tr class=\"template\" id=\"last\"> ";
			 int num =0;
			 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
				 if(filed_keys[i] != null && filed_keys[i].length() > 0){
					 if(i==0){
						 table+= "<td> </td>";
					 }else{
						 if(i==1) {
							 table += "<td >大写金额 </td>" ;
						 } else if(i < fields.length-1 && i < filed_keys.length-1){
							 num++;
						 }else {
							 table += "<td colspan=\""+(num+1)+"\"><input class=\"input-medium\" type=\"text\" name=\""+lc.toaltextname+"uppercase\" value=\"\" > </td>" ;
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + lc.toaltextname + "\" value=\"\"  > </td>" ;
						 }
					 }
				 }
			 }
		   table += "</tr> ";
			 
			 
			int size = listCtrlValue != null ? listCtrlValue.size() : 0 ;
			 
			 table += "<input type=\"hidden\" id='" + name + "_length' value='" + size + "' >" ;
			 table += "<input type=\"hidden\" id='" + name + "_field' value='" + lc.getOrgkey() + "' >" ;
		 }
		 		 		 		 
		 table += " </tbody> </table>";
		
		 String content = temp_html + "<p>" + table + "</p>" ;
	 		
		return content ;
		
	}
	
	public String getEditWithSum(String value,int shenHe,String uppercase, JSONObject jsonObjet ) {
		JSONObject jsonValue = null ;
		try{
			if(value != null && value.trim().length() > 1)
				jsonValue =  JSON.parseObject(value);
		}catch(Exception e) {
			System.out.println(e);
			jsonValue = null;
		}
		return parseListCtrlWithSum(this, 1, jsonValue,shenHe,uppercase, jsonObjet);
	}
	

	/**
	 * 解析listctrl
	 * @param listCtrilData ueform中 data里的listctrl 整个json
	 * @param type 1-编辑模式 2-纯浏览模式 采用parse替换
	 * @param value 填充的值,可以为空
	 * @return
	 */
	private String parseListCtrlWithSum(ListCtrls lc , int type , JSONObject listCtrlValue,int shenHe,String uppercase, JSONObject jsonObjet) {
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
	           "sum(); "+
	        " }" + 
	           
			   // 求和
			   " function sum()" + 
			   " {" +  
			     "   var trr =  $('#" + name + "_table tbody tr'); " + 
			     "   var totleSum = 0 ; " + 
			     " for(var i = 0 ; i < trr.length-1 ; i++) {"+ 
					"	var tdprice = trr[i].childNodes[2].children[0].value; "+
					"	var tdcount = trr[i].childNodes[3].children[0].value; "+
					
					"  tdprice = Number(tdprice) ; tdcount = Number(tdcount) ; "+
					" if('NaN'==tdprice || 'NaN'==tdcount){" +
					"     alert('请输入数字！'); continue;"+
					"  }"+
					" var oneSum = tdprice * tdcount ; oneSum=fomatFloat(oneSum,2); totleSum += oneSum ; " +
					" totleSum=fomatFloat(totleSum,2);  console.log(totleSum);  "+
					"trr[i].childNodes[4].children[0].value=oneSum; "+
				   "}  " + 
			      "  trr[trr.length-1].childNodes[5].children[0].value= totleSum ;" +
			      //" console.log(trr[trr.length-1].childNodes)" + 
			   " }" +
			      //格式化数字
			   "function fomatFloat(src,pos){   "+   
			   	"	return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);    "+ 
				"} "+	
	     "</script>"; 
		 String[] fields =  lc.getOrgtitle().split("`");
		 String[] filed_keys =  lc.getOrgkey().split("`");
		 String table = "<table id=\"" + name +"_table\" cellspacing=\"0\" class=\"table table-bordered table-condensed\" style=\"width: 100%;\"><thead>" ;
		 table += " <tr><th colspan=\"" +  (fields.length+1)  + "\">" + getTitle();
		 if(type == 1) {
			 if(shenHe == 2){
			 }else{
				 table += "<span class=\"pull-right\"> "+ 
				         "<button class=\"btn btn-small btn-success\" type=\"button\" onclick=\"tbAddRow('" + name + "')\">添加一行</button>"+ 
				         "</span> ";
			 }
		 }
		 table += "</th></tr><tr>" ;
		 for(int i = 0 ; i < fields.length ; i++) {
			 if(fields[i] != null && fields[i].length() > 0)
				 table += "<th>" + fields[i] + "</th>" ;			 
		 }
		 table += "<th>合计</th>" ;
		 table += "</tr></thead> <tbody id=\"tbId\">" ;
		 if(type == 2) {
			  //纯查看模式
			 if(listCtrlValue != null) {
				 Set<String> keys =  listCtrlValue.keySet() ;
				 for(String key : keys) {
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\" id=\"first\">";
					 //Set<String> ts = _key_v.keySet();
					 for(int i = 0 ; i < fields.length && i < filed_keys.length && i < _key_v.size() ; i++) {
						 if(fields[i] != null && fields[i].length() > 0)
							 table += "<td>"+ _key_v.getString(filed_keys[i]) + "</td>" ;
					 }
					 table += " </tr>";
				 }
			 }
			
		 } else {
			 table += " <tr class=\"template\" id=\"first\">";
			 if(listCtrlValue != null) { //查看，审核
				 Set<String> keys =  listCtrlValue.keySet() ;
				 Set<String> set = new TreeSet<>(new Comparator<String>() {//创建TreeSet同时匿名内部类传入比较器					 
			            @Override
			            public int compare(String p1, String p2) {
			            	try{
			            		 if(p1.equals("last")){
			 			    		return 1; 
			 			    	 }
			            		return Integer.valueOf(p1) > Integer.valueOf(p2) ? 1 : -1;
			            	}catch(Exception e){
			            		return -1;
			            	}
			                
			            }
			        });
				 for(String key : keys) {
					 set.add(key); 
				 }								 
				 for(String key : set) {
					 JSONObject _key_v  = listCtrlValue.getJSONObject(key);
					 table += " <tr class=\"template\">";
					 //Set<String> ts = _key_v.keySet();
					 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
						 if(filed_keys[i] != null && filed_keys[i].length() > 0){
						 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[" + key + "][" + filed_keys[i] + "]\" value=\"" + _key_v.getString(filed_keys[i]) + "\"> </td>" ;
						 }
					 }
					 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[" + key + "][toaltext"+name+key+"]\" value=\""+_key_v.getString("toaltext"+name+key)+"\"> </td>" ;
					 table += " </tr>";
				 }
			 } else {
				 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
					 if(filed_keys[i] != null && filed_keys[i].length() > 0)
						 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + name + "[0][" + filed_keys[i] + "]\" value=\"\"> </td>" ;
				 }
				 table += " </tr>";
			 }
			 table += "<tr class=\"template\" id=\"last\"> ";
			 int num=0;
			 for(int i = 0 ; i < fields.length && i < filed_keys.length ; i++) {
				 if(filed_keys[i] != null && filed_keys[i].length() > 0){
					 if(i==0){
						 table+= "<td><span class=\"btn btn-small btn-success\" onclick=\"sum('"+name+"','"+lc.toaltext+"');\">总和</span> </td>";
					 }else{
						 if(i==1) {
							 table += "<td >大写金额 </td>" ;
						 } else if(i < fields.length-1 && i < filed_keys.length-1){
							 num++;
						 }else {
							 table += "<td colspan=\""+(num+1)+"\"><input class=\"input-medium\" type=\"text\" name=\""+lc.toaltextname+"uppercase\" value=\""+jsonObjet.getString(lc.toaltextname+"uppercase")+"\" > </td>" ;
							 table += "<td><input class=\"input-medium\" type=\"text\" name=\"" + lc.toaltextname + "\" value=\""+jsonObjet.getString(lc.toaltextname)+"\"  > </td>" ;
						 }
					 }
				 }
			 } 
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
