/**
 * 
 */
package com.mapscience.core.getway;



/**
 *说明：
 *<p>公共请求基本参数 </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public class BaseWayRequest implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	public BaseWayRequest(){}
	private String method;//调用方法
	private String version = CommConst.VERSION_1_0_2;//版本号
	public String getMethod() {
		return method;
	}
	public String getVersion() {
		return version;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setVersion(String version) {
		this.version = version;
	}

}
