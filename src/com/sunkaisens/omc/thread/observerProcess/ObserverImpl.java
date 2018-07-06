package com.sunkaisens.omc.thread.observerProcess;

import java.util.ArrayList;
import java.util.List;

public class ObserverImpl implements ObserverInterface {

	// 创建对象(单例模式)
	private ObserverImpl() {
		list = new ArrayList<ObserverObject>();
	}
	public static ObserverImpl getInstance() {
		return ObserverImplInstance.instance;
	}
	private static class ObserverImplInstance {
		private static ObserverImpl instance = new ObserverImpl();
	}
	
	//List集合的泛型参数为ObserverObject接口
    private List<ObserverObject> list;
    
	@Override
	public void registerObserver(ObserverObject observerObject) {
		list.add(observerObject);
	}

	@Override
	public void removeObserver(ObserverObject observerObject) {
		if(!list.isEmpty()){
			list.remove(observerObject);
		}
	}

	@Override
	public void notifyObserver(Object message) {
		for(int i = 0; i < list.size(); i++) {
			ObserverObject observerObject = list.get(i);
			observerObject.processCncpMsg(message);
        }
	}
	
	public void receivedMsgAndSendObserver(Object msg) {
        System.err.println(">>>>>>>>>>>>通知每一个观察者:"+msg);
        //消息更新，通知所有观察者
        notifyObserver(msg);
    }

}
