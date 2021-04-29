package org.zk.simplespring.core.type.filter;

import org.zk.simplespring.BeanDefinition;

import java.lang.annotation.Annotation;

public class AnnotationTypeFilter implements TypeFilter{

	private final Class<? extends Annotation> annotationType;

	public AnnotationTypeFilter(Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
	}

	@Override
	public boolean match(BeanDefinition beanDefinition) {
		try {
			Class<?> clz = beanDefinition.resolveBeanClass();
			if (isAnnotationWithComponent(clz))
				return true;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	/**
	 * 判断class上是否有指定注解，包含组合注解的情况
	 * @param clz
	 * @return
	 */
	private boolean isAnnotationWithComponent(Class<?> clz) {
		Annotation[] annotations = clz.getAnnotations();
		for (Annotation annotation : annotations) {
			Class annotationType = annotation.annotationType();
			if (annotationType.equals(annotationType))
				return true;
			// 组合注解的情况，例如@Service包含注解@Component
			Annotation[] metaAnnotations = annotationType.getAnnotations();
			for (Annotation metaAnnotation : metaAnnotations) {
				if (metaAnnotation.annotationType().equals(annotationType)) {
					return true;
				}
			}
		}
		return false;
	}

}
