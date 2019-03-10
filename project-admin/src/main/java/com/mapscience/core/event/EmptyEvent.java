package com.mapscience.core.event;

/**
 * <pre>
 * <b>空事件处理器.</b>
 * <b>Description:</b> 
 
 */
public class EmptyEvent extends CommonEvent {

	/** . */
	private static final long serialVersionUID = 1L;

	/**
	 * <pre>
	 * <b>.</b>
	 * <b>Description:</b> 
	 *     
	 * @param key
	 * @param source
	 * </pre>
	 */
	public EmptyEvent(String key) {
		super(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iyooc.common.event.CommonEventListenner#run()
	 */
	@Override
	public void run() {

	}

}
