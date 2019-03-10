package com.mapscience.modular.system.edit;
/**
 * 
 *说明：文本框实体类
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年1月25日
 *
 */
public class Text extends FormField {

	private String orgheight ;//行高
	private String orgfontsize;//字体大小
	private String orgalign ;// left center right
	private String orgtype ;// 文本中的内容- text,email,int,float-小数,idcard-身份证
	private String textra;//解析组装的json字符串
	
	public String getTextra() {
		return textra;
	}
	public void setTextra(String textra) {
		this.textra = textra;
	}
	public String getOrgheight() {
		return orgheight;
	}
	public void setOrgheight(String orgheight) {
		this.orgheight = orgheight;
	}
	public String getOrgfontsize() {
		return orgfontsize;
	}
	public void setOrgfontsize(String orgfontsize) {
		this.orgfontsize = orgfontsize;
	}
	public String getOrgalign() {
		return orgalign;
	}
	public void setOrgalign(String orgalign) {
		this.orgalign = orgalign;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	
	/**
	 * 转换成bean
	 * @param text
	 * @return
	 */
	public static FormField parseText(String text) {
		Text t = CommonUtils.parseInputAttrsT(text , Text.class);
		return t;
	}
	
	@Override
	public String getEdit(String value) {
		return super.getEdit(value);
	}
	
	
}
