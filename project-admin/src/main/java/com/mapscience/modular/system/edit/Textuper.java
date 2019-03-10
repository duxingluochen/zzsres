/**
 * 
 */
package com.mapscience.modular.system.edit;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年1月29日
 *
 */
public class Textuper extends FormField{

	private String orgheight ;//行高
	private String orgfontsize;//字体大小
	private String orgalign ;// left center right
	private String textra;//解析组装的json字符串
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
	public String getTextra() {
		return textra;
	}
	public void setTextra(String textra) {
		this.textra = textra;
	}
	
	/**
	 * 转换成bean
	 * @param text
	 * @return
	 */
	public static FormField parseText(String text) {
		Textuper t = CommonUtils.parseInputAttrsT(text , Textuper.class);
		return t;
	}
	
	@Override
	public String getEdit(String value) {
		String str = super.getEdit(value);
		//获取需要大写的key
		int beg = str.indexOf("textuper=\"");
		int last = str.substring(beg).indexOf("\"");
		String key  = str.substring(beg+10).substring(0, last);
		int begn = str.indexOf("name=\"");
		int lastn = str.substring(begn).indexOf("\"");
		String keyn  = str.substring(begn+6).substring(0, lastn-1);
		String script = "<script> $(\"input[name='"+key+"']\").bind(\"input propertychange\",function(event){" + 
				"       $(\"input[name='"+keyn+"']\").val(convertCurrency($(\"input[name='"+key+"']\").val()))});</script>";
		return str+script;
	}
}
