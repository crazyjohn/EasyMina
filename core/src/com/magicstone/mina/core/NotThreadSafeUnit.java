package com.magicstone.mina.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marked the unit which is not thread safe;
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface NotThreadSafeUnit {
	/**
	 * Describe how to use this class;
	 * 
	 * @return
	 */
	String desc() default "";
}
