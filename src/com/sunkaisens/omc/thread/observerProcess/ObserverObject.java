package com.sunkaisens.omc.thread.observerProcess;

/**
 * 抽象观察者
 * 定义了一个update()方法，当被观察者调用notifyObserver()方法时，观察者的processCncpMsg()方法会被回调。
 */
public interface ObserverObject {
	//
	public void processCncpMsg(Object message);
}
