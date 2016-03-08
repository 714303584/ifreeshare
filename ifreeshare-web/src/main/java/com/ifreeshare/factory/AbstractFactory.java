package com.ifreeshare.factory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ifreeshare.tools.PdfToSwfFactory;

public abstract class AbstractFactory <T extends Runnable>{
	ThreadPoolExecutor workerPool = null;
	private int corePoolSize;
	private int maximumPoolSize;
	private int keepAliveTime;
	private   TimeUnit unit;
	private int queueSize;
	BlockingQueue<Runnable> workQueue = null;
	Logger log = null;
	

	public AbstractFactory(int corePoolSize, int maximumPoolSize, int keepAliveTime,
			TimeUnit unit, int queueSize) {
		super();
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
		this.unit = unit;
		this.queueSize = queueSize;
		workQueue = new ArrayBlockingQueue<Runnable>(this.maximumPoolSize);
		workerPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		
		 log = LogManager.getLogger(PdfToSwfFactory.class);
	}
	
	public void execute(Runnable runnable){
		workerPool.execute(runnable);
	}
	
	
}
