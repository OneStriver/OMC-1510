package com.sunkaisens.omc.thread.observerProcess;

/**
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 */
public interface ObserverInterface {
    //注册观察者对象
    public void registerObserver(ObserverObject observerObject);
    //移除观察者
    public void removeObserver(ObserverObject observerObject);
    //通知观察者
    public void notifyObserver(Object message);
    
}
