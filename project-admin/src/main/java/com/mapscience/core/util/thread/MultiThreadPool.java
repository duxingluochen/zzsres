package com.mapscience.core.util.thread;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mapscience.core.exception.ThreadException;
import com.mapscience.core.handler.LogUtil;

/**
 * 
 *说明：
 *<p> 多线程线程池.</p>
 * 书写者 @author  WCF
 * 创建时间 2019年2月22日
 *
 */
@Component
public class MultiThreadPool {

	// 日志记录器.
	public static final Logger LOGGER = Logger.getLogger("common.thread");

	/** 空闲线程的最少数量, 默认: 8. */
	protected Integer minPoolSize = 64;

	/** 空闲线程的最大数量, 默认: 64. */
	protected Integer maxPoolSize = 256;

	/** 线程可空闲时间, 默认: 60. */
	protected Long keepAliveTime = 60L;
	
	/** 线程可空闲时间, 默认: SECONDS. */
	public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

	/** 缓冲队列, 默认：maxPoolSize. */
	protected Integer queueCapacity = this.maxPoolSize;

	// 可批量执行的线程池.
	protected ThreadPoolExecutor poolExecutor = null;

	// 线程池对拒绝任务的处理策略
	// protected BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(1024);
	// protected LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(1024);
	protected BlockingQueue<Runnable> workQueue;

	// ExecutionHandler有四个选择：
	// ThreadPoolExecutor.AbortPolicy(): 一旦线程不能处理，则抛出异常。
	// ThreadPoolExecutor.CallerRunsPolicy(): 一旦线程不能处理，则将任务返回给提交任务的线程处理。
	// ThreadPoolExecutor.DiscardOldestPolicy(): 一旦线程不能处理，丢弃掉队列中最老的任务。
	// ThreadPoolExecutor.DiscardPolicy(): 一旦线程不能处理，则丢弃任务。
	protected RejectedExecutionHandler executionHandler;

	/** 定义数据读写所锁. */
	protected ReadWriteLock LOCK = new ReentrantReadWriteLock();
	
	/**
	 *<pre>
	 *<b>.</b>
	 *<b>Description:</b> 
	 *    
	 *<b>Date:</b> 2016-4-29 上午11:14:26
	 *</pre>
	 */
	public MultiThreadPool() {
	}

	/**
	 * 初始化线程池.
	 */
	@PostConstruct
	public void init() {
		LOGGER.info(LogUtil.LINE);
		LOGGER.info(LogUtil.PREFIX2 + this.getClass().getSimpleName() + " init ...");
		LOGGER.info(LogUtil.PREFIX3 + LogUtil.debugInfo("thread.minPoolSize", this.minPoolSize, 25));
		LOGGER.info(LogUtil.PREFIX3 + LogUtil.debugInfo("thread.maxPoolSize", this.maxPoolSize, 25));
		LOGGER.info(LogUtil.PREFIX3 + LogUtil.debugInfo("thread.keepAliveTime", this.keepAliveTime, 25));
		LOGGER.info(LogUtil.PREFIX3 + LogUtil.debugInfo("thread.queueCapacity", this.queueCapacity, 25));
		LOGGER.info(LogUtil.PREFIX3 + LogUtil.debugInfo("thread.unit", TIME_UNIT.name(), 25));
		// this.workQueue = new ArrayBlockingQueue<Runnable>(this.queueCapacity);
		this.workQueue = new ArrayBlockingQueue<Runnable>(this.queueCapacity);

		// 实例化executionHandler
		this.executionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

		// 实例化线程池
		this.poolExecutor = new ThreadPoolExecutor(this.minPoolSize, this.maxPoolSize, this.keepAliveTime, TIME_UNIT,
				this.workQueue, this.executionHandler);

	}

	/**
	 * 释放当前线程资源.
	 */
	public void release() {
		if (null != this.poolExecutor) {
			this.poolExecutor.shutdown();
			this.workQueue.clear();
		}
	}

	/**
	 * 清理线程池中被取消的无效线程.
	 */
	protected void clean() {
		if (null != this.poolExecutor) {
			this.poolExecutor.purge();
			this.workQueue.clear();
		}
	}

	/**
	 * 销毁当前线程池.
	 */
	public void destroy() {
		if (null != this.poolExecutor) {
			this.LOCK.writeLock().lock();
			this.poolExecutor.shutdown();
			this.workQueue.clear();
			this.poolExecutor = null;
			this.workQueue = null;
			this.LOCK.writeLock().unlock();
		}
	}

	/**
	 * 重写GC回收逻辑.
	 */
	@Override
	public void finalize() throws Throwable {
		this.destroy();
		super.finalize();
	}

	/**
	 * 获取当前线程池的运行状态.
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> status() {
		// 构建Map, 将连接池中的相关状态数据进行持有.
		Map<String, Object> status = new HashMap<String, Object>(4);
		if (null != this.poolExecutor) {
			status.put("corePoolSize", poolExecutor.getCorePoolSize());
			status.put("maxPoolSize", poolExecutor.getMaximumPoolSize());
			status.put("largestPoolSize", poolExecutor.getLargestPoolSize());
			status.put("keepAliveTime", poolExecutor.getKeepAliveTime(TIME_UNIT));
			status.put("workQueueSize", workQueue.size());
			status.put("poolSize", poolExecutor.getPoolSize());
			status.put("taskCount", poolExecutor.getTaskCount());
			status.put("activeCount", poolExecutor.getActiveCount());
			status.put("completedTaskCount", poolExecutor.getCompletedTaskCount());
		}
		// 返回当前状态数据.
		return status;
	}

	/**
	 * 执行可执行任务.
	 * 
	 * @param task
	 */
	public void exec(Runnable task) {
		this.poolExecutor.execute(task);
	}

	/**
	 * 添加可执行任务.
	 * 
	 * @param <T>
	 * @param task
	 * @return
	 */
	public <T> Future<T> call(Callable<T> task) {
		return this.poolExecutor.submit(task);
	}

	/**
	 * 批量执行多线程任务，并获取响应输出结果.
	 * 
	 * @param tasks
	 * @return
	 * @throws ThreadException
	 */
	public <T> List<Future<T>> call(Collection<? extends Callable<T>> tasks) throws ThreadException {
		try {
			return this.poolExecutor.invokeAll(tasks);
		} catch (InterruptedException e) {
			throw new ThreadException(ThreadException.PRIFIX + "INVOKEALL_ERROR", e);
		}
	}

	/**
	 * 批量执行多线程任务，并获取响应输出结果.
	 * 
	 * @param tasks
	 * @param timeOut
	 * @return
	 * @throws ThreadException
	 */
	public <T> List<Future<T>> call(Collection<? extends Callable<T>> tasks, long timeOut) throws ThreadException {
		try {
			return this.poolExecutor.invokeAll(tasks, timeOut, TIME_UNIT);
		} catch (InterruptedException e) {
			throw new ThreadException(ThreadException.PRIFIX + "INVOKEALL_ERROR", e);
		}
	}

	/**
	 * 设置 空闲线程的最少数量默认:8.
	 * 
	 * @param minPoolSize
	 *            空闲线程的最少数量默认:8.
	 */
	public void setMinPoolSize(Integer minPoolSize) {
		if (null != minPoolSize) {
			this.minPoolSize = minPoolSize;
		}
	}

	/**
	 * 设置 空闲线程的最大数量默认:64.
	 * 
	 * @param maxPoolSize
	 *            空闲线程的最大数量默认:64.
	 */
	public void setMaxPoolSize(Integer maxPoolSize) {
		if (null != maxPoolSize) {
			this.maxPoolSize = maxPoolSize;
		}
	}

	/**
	 * 设置 线程可空闲时间默认:60.
	 * 
	 * @param keepAliveTime
	 *            线程可空闲时间默认:60.
	 */
	public void setKeepAliveTime(Long keepAliveTime) {
		if (null != keepAliveTime) {
			this.keepAliveTime = keepAliveTime;
		}
	}

	/**
	 * 设置 缓冲队列默认：maxPoolSize.
	 * 
	 * @param queueCapacity
	 *            缓冲队列默认：maxPoolSize.
	 */
	public void setQueueCapacity(Integer queueCapacity) {
		if (null != queueCapacity) {
			this.queueCapacity = queueCapacity;
		}
	}

}
