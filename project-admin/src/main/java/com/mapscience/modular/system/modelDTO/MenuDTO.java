/**
 * 
 */
package com.mapscience.modular.system.modelDTO;

import java.util.List;

import com.mapscience.modular.system.model.Menu;

/**
 *说明：
 *<p> </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月28日
 *
 */
public class MenuDTO extends Menu{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7854886027231106651L;

	protected List<MenuDTO> children;

	public List<MenuDTO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDTO> children) {
		this.children = children;
	}
	
}
