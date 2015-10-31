package com.griffiths.hugh.ReflectiveBeanTools.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.griffiths.hugh.ReflectiveBeanTools.primitives.PrimitiveFactory;

/**
 * Instantiates a bean and recursively populates its fields and references using
 * template values.
 * 
 * Assumes the class follows the JavaBean standard, that all fields are
 * primitives or are themselves beans. All fields must be writeable. To avoid
 * infinite loops, only one instance of each class is instantiated, and will be
 * reused wherever that class is required. This results in a minimal object
 * graph.
 * 
 * Not thread-safe.
 * 
 * @author hugh
 *
 */
public class BeanInstantiator {
	private final PrimitiveFactory primitiveFactory;
	private final Map<Class<?>, Object> constructedObjects = new HashMap<Class<?>, Object>();

	public BeanInstantiator(PrimitiveFactory primitiveFactory) {
		this.primitiveFactory = primitiveFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> T instantiate(Class<T> beanClass)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		// If we already have one, use that.
		// This handles to problem of cyclic dependencies.
		if (constructedObjects.containsKey(beanClass)) {
			return (T) constructedObjects.get(beanClass);
		}

		// Make an instance.
		T bean = (T) beanClass.newInstance();
		constructedObjects.put(beanClass, bean);

		// List fields
		PropertyDescriptor[] propDescArr = PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor propDesc : propDescArr) {
			// Get an appropriate value for the field
			Object value;
			Class<?> propType = propDesc.getPropertyType();
			if (propType.equals(Class.class)) {
				// Do nothing - this is the Object.getClass() method.
				continue;
			} else if (propType.isAssignableFrom(String.class)) {
				value = primitiveFactory.getString();
			} else if (propType.isAssignableFrom(Integer.class) || propType.equals(int.class)
					|| propType.equals(long.class) || propType.equals(short.class) || propType.equals(byte.class)) {
				value = primitiveFactory.getShort();
			} else if (propType.isAssignableFrom(Float.class) || propType.equals(float.class)
					|| propType.equals(double.class)) {
				value = primitiveFactory.getFloat();
			} else {
				value = instantiate(propDesc.getPropertyType());
			}

			// Set the field
			BeanUtils.setProperty(bean, propDesc.getName(), value);
		}

		return bean;
	}
}
