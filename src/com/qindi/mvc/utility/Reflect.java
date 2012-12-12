package com.qindi.mvc.utility;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Reflect {	
	// Try to get the declared method from class, if cannot find , try to find alt method in super class 
	public static Method getClassMethod(Class<?> handler , String method , String altMethod){
		altMethod = altMethod == null? method : altMethod;
		Method m = null;
		try {
			m 	= 	handler.getDeclaredMethod( method , new Class[]{ServletContext.class,HttpServletRequest.class,HttpServletResponse.class} );
		} catch (SecurityException e) {			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("ERROR : Sorry method not found - " + e.getMessage());
			try {
				m 	= 	handler.getSuperclass().getDeclaredMethod( altMethod , new Class[]{ServletContext.class,HttpServletRequest.class,HttpServletResponse.class} );
			} catch (SecurityException e1) {				
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}			
		}
		
		return m;
	}
}
