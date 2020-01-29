package com.bat.qmall.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

	/**
	 * false 登录失败
	 * true 登录成功
	 * @return
	 */
    boolean value() default true;

}
