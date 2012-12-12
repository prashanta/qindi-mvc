package com.qindi.mvc.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Map
{		
	boolean include() default false;
	String viewUrl() default "";
	boolean monitor() default false;
	String comment() default ""; //TODO use description from javadoc comments
}
