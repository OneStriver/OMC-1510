package com.sunkaisens.omc.component.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * 
 *OperateLogAdvice 实体类
 *
 */
@Aspect
@Component
public class OperateLogAdvice{
	Logger log=LoggerFactory.getLogger(OperateLogAdvice.class);
	
	@Pointcut("execution(* com.sunkaisens.omc.controller..*.*(..))")
	public void operateLogPointcut() {}
	//无效
//	@Around("operateLogPointcut()")
//	public Object operateLog(ProceedingJoinPoint pjp){
//		Object obj=null;
//		try{
//			obj=pjp.proceed();
//			log.debug(obj==null?"null":obj.toString());
//		}catch(Throwable e){
//			e.printStackTrace();
//		}finally{
//			
//		}
//		return obj;
//		
//	}
}
