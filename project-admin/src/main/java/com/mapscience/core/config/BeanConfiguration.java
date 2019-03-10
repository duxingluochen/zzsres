/**
 * 
 */
package com.mapscience.core.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.mapscience.core.getway.GateWayConfCache;
import com.mapscience.core.util.common.IOCutil;

/**
 *说明：
 *<p> bean配置</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
@PropertySource({"classpath:pro.properties"})
@Configuration
public class BeanConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	/** 扫描配置包名称，多个以逗号分隔 */
	@Value("${packageNames}")
	private String packageNames;

	public String getPackageNames() {
		return packageNames;
	}

	public void setPackageNames(String packageNames) {
		this.packageNames = packageNames;
	}
	
	@Bean(initMethod="load", name="gateWayConfCache")
	public GateWayConfCache gateWayConfCache() {
		GateWayConfCache gateWayConfCache = new GateWayConfCache();
		gateWayConfCache.setPackageNames(packageNames);
		return gateWayConfCache;
	}
	
	@Bean(initMethod="init")
	public IOCutil ioCutil() {
		IOCutil ioCutil = new IOCutil();
		ioCutil.setApplicationContext(applicationContext);
		return ioCutil;
	}
	
}
