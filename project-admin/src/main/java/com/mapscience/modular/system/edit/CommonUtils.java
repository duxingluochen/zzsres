package com.mapscience.modular.system.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class CommonUtils {

public static String[] maches(String str , String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		List<String> list = new ArrayList<String>();
		while(m.find()) {
			 list.add(m.group());
		}
		return list.toArray(new String[0]);
	}
	
	public static JSONObject parseInputAttrs(String str) {
		 String preg_attr ="(\\w+)=\"(.*?)\"" ;
		 List<String[]> list = machesGroup(str, preg_attr);
		 JSONObject object = new JSONObject();
		 for(String[] ss : list) {
			 object.put(ss[0], ss[1]);
		 }
		return object ;
	}
	
	public static <T> T parseInputAttrsT(String str , Class<T> t) {
		 JSONObject object = parseInputAttrs(str);
		 return object.toJavaObject(t);
	}

	public static List<String[]> machesGroup(String str , String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		List<String[]> list = new ArrayList<String[]>();
		while(m.find()) {
			int cnt = m.groupCount();
			String[] ss = new String[cnt];
			for(int i = 0 ; i< cnt ; i++) {
				ss[i] = (m.group(i + 1));
			}
			list.add(ss);
		}
		return list ;
	}
}
