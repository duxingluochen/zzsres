package com.mapscience.core.util;

import java.net.InetAddress;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 
 *说明：
 *<p>UUID 辅助工具. </p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
public abstract class UUIDUtil {

	/** ID后缀. */
	protected static final long SFIX;

	/** 服务器物理IP地址. */
	protected static final String IP;

	/** 当前Java虚拟机实例ID. */
	protected static final String JVM;

	/** 计数器. */
	protected static Short COUNTER = 0;

	/** 定义数据读写所锁. */
	protected static ReadWriteLock LOCK = new ReentrantReadWriteLock();
	
    static {
		// 获取系统环境变量的服务器后缀.
		String sid = /* System.getenv("SERVER_ID") */"10";
		long _sfix = Long.parseLong(sid);
		// 构造乘16位长度的数字.
		SFIX = _sfix * 100000000000000L;

		int ip = 0;
		try {
			// 获取当前Server的物理IP地址.
			InetAddress ia = InetAddress.getLocalHost();
			byte[] bytes = ia.getAddress();
			ip = toInt(bytes);
			for (int i = 0; i < 4; i++) {
				ip = (ip << 8) - Byte.MIN_VALUE + (int) bytes[i];
			}
		} catch (Exception e) {
			ip = 0;
		}
		String _ip = Integer.toHexString(ip);
		IP = (new StringBuffer("00000000")).replace(8 - _ip.length(), 8, _ip).toString();

		// 获取当前系统时间毫秒数作为jvm虚拟机实例id.
		int jvm = (int) (System.currentTimeMillis() >>> 8);
		String _jvm = Integer.toHexString(jvm);
		JVM = (new StringBuffer("00000000")).replace(8 - _jvm.length(), 8, _jvm).toString();
	}

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected UUIDUtil() {
        super();
    }

    /**
     * 二进制转换为Int值.
     * 
     * @param bytes
     * @return int
     */
    public static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected static short getCount() {
        synchronized (LOCK) {
            if (COUNTER < 0) {
                COUNTER = 0;
            }
            return COUNTER++;
        }
    }
    /**
	 * 获取唯一ID.
	 * 
	 * @return long
	 */
	public static long id() {
		return ids(1)[0];
	}

	/**
	 * 获取自定指定个数的唯一ID清单.
	 * 
	 * @param len
	 *            个数.
	 * @return long[]
	 */
	public static long[] ids(int len) {
		long[] ids = new long[len];
		LOCK.writeLock().lock();
		long times = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			ids[i] = SFIX + (times++);
			if (1 != len) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		LOCK.writeLock().unlock();
		return ids;
	}

    /**
     * 生成数据库记录PK(模拟Hibernate中的UUID策略).
     * 
     * @return String
     */
    public static String getUUID() {
		StringBuffer sb = new StringBuffer(36);
		sb.append(IP).append(JVM);

		// 获取小时部分.
		short hitime = (short) (System.currentTimeMillis() >>> 32);
		String _hitime = Integer.toHexString(hitime);
		StringBuffer hitimeSb = (new StringBuffer("0000")).replace(4 - _hitime.length(), 4, _hitime);
		sb.append(hitimeSb);

		// 获取毫秒部分.
		int lotime = (int) System.currentTimeMillis();
		String _lotime = Integer.toHexString(lotime);
		StringBuffer lotimeSb = (new StringBuffer("00000000")).replace(8 - _lotime.length(), 8, _lotime);
		sb.append(lotimeSb);

		// 获取累计计数部分.
		int count = getCount();
		String _count = Integer.toHexString(count);
		StringBuffer countSb = (new StringBuffer("0000")).replace(4 - _count.length(), 4, _count);
		sb.append(countSb);

		return sb.toString();
	}
    
    /**
	 * 获取自定指定个数的唯一UUID清单.
	 * 
	 * @param len
	 *            个数.
	 * @return long[]
	 */
	public static String[] getUUID(int len) {
		String[] uuids = new String[len];
		for (int i = 0; i < len; i++) {
			uuids[i] = getUUID();
		}
		return uuids;
	}

	/**
     * 获取系统流水号
     * @param length   指定流水号长度
     * @param toNumber 指定流水号是否全由数字组成
     */
	public static String getSerial(int length, boolean isNumber) {
		// replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
		String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		if (uuid.length() > length) {
			uuid = uuid.substring(0, length);
		} else if (uuid.length() < length) {
			for (int i = 0; i < length - uuid.length(); i++) {
				uuid = uuid + Math.round(Math.random() * 9);
			}
		}
		if (isNumber) {
			return uuid.replaceAll("a", "1").replaceAll("b", "2").replaceAll("c", "3").replaceAll("d", "4").replaceAll("e", "5").replaceAll("f", "6");
		} else {
			return uuid;
		}
	}
}
