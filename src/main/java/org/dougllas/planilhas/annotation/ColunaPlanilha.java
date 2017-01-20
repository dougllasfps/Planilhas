package org.dougllas.planilhas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dougllas.planilhas.format.Alignment;

/**
 * @author dougllas.sousa
 * @since 17-08-2016
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ColunaPlanilha {

	String cabecalho() default "";
	
	int tamanho() default 10;
	
	CellTextAlignment cellTextAlignment() default CellTextAlignment.LEFT;
	
	Alignment columnHeaderTextAlignment() default Alignment.LEFT;
	
	int columnPosition() default -1;
	
}