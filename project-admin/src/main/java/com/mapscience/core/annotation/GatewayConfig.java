/**
 * 
 */
package com.mapscience.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.mapscience.core.enu.DoingMethodEnum;
import com.mapscience.core.getway.CommConst;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2018年12月3日
 *
 */
public @interface GatewayConfig {
	/**获取业务方法设置*/
	public DoingMethodEnum value();
	/**版本号*/
	public String[] version() default CommConst.VERSION_1_0_2;
}
