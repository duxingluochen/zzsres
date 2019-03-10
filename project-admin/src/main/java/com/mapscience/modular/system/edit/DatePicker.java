package com.mapscience.modular.system.edit;

import com.mapscience.core.util.common.ObjectUtil;

public class DatePicker extends FormField {

	private String now;
	
	public void setNow(String now) {
		this.now = now;
	}
	public String getNow() {
		return now;
	}
	
	public static DatePicker parseText(String text) {
		DatePicker t = CommonUtils.parseInputAttrsT(text , DatePicker.class);
		
		return t;
	}
	
	@Override
	public String getEdit(String value) {
		
		/*<input size="16" type="text" value="2012-06-15 14:45" readonly class="form_datetime">
		 
		<script type="text/javascript">
		    $(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
		</script> */
		
		String js = "<script> "+
				"  $('[name=\"" + getName() + "\"]').datepicker({ language:\"zh-CN\", format:'yyyy-mm-dd',defaultDate:+0}); "+
				"$('[name=\"" + getName() + "\"]').attr('readOnly','readOnly')";
				
		if(value == null && getNow() != null && getNow().equals("1")) {
			//赋值
			js += " $('[name=\"" + getName() + "\"]').datepicker('setDate', new Date());";
		} else if(value != null) {
			
		}
				
		js += "</script>" ;
		
		String content = super.getEdit(value);
		if(ObjectUtil.isNotEmpty(content)) {
			String str1= content.substring(0,content.length()-2);
			if(!ObjectUtil.isEmpty(value)){
				str1 += "value=\""+value+"\"\\>";
			}else{
			str1 += "\\>";
			}
			content=str1;
		}
		
		content += js ;
		
		return content;
	}
	
}
