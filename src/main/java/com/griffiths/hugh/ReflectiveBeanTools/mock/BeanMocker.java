package com.griffiths.hugh.ReflectiveBeanTools.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.griffiths.hugh.ReflectiveBeanTools.primitives.PrimitiveFactory;

/**
 * Similar to BeanInstantiator, this class creates a populated Mockito mock for
 * beans, which can serve as a basis for mocking specific fields.
 * 
 * There's almost certainly a better way of doing this.
 * 
 * @author hugh
 *
 */
public class BeanMocker {
	private final PrimitiveFactory primitiveFactory;
	private final Map<Class<?>, Object> mockedObjects = new HashMap<Class<?>, Object>();

	public BeanMocker(PrimitiveFactory primitiveFactory) {
		super();
		this.primitiveFactory = primitiveFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> T mockBean(Class<T> beanClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// If we already have one, use that.
		// This eliminates the risk of cyclic dependencies.
		if (mockedObjects.containsKey(beanClass)) {
			return (T) mockedObjects.get(beanClass);
		}

		// Create the mock
		T mockBean = mock(beanClass);
		mockedObjects.put(beanClass, mockBean);

		// List fields
		PropertyDescriptor[] propDescArr = PropertyUtils.getPropertyDescriptors(mockBean);
		for (PropertyDescriptor propDesc : propDescArr) {
			Object value;
			Class<?> propType = propDesc.getPropertyType();

			// These aren't genuine fields
			if (propDesc.getReadMethod() == null || Class.class.equals(propType)
					|| "[Lorg.mockito.cglib.proxy.Callback;".equals(propType.getName())) {
				continue;
			}

			// Select an appropriate value.
			// TODO: if/else rather than switch on string.
			switch (propType.getName()) {
			case "java.lang.String":
				value = primitiveFactory.getString();
				break;
			case "java.lang.Integer":
			case "int":
				value = (int) (primitiveFactory.getShort());
				break;
			case "java.lang.Long":
			case "long":
				value = (long) (primitiveFactory.getShort());
				break;
			case "java.lang.Short":
			case "short":
				value = (short) (primitiveFactory.getShort());
				break;
			case "java.lang.Byte":
			case "byte":
				value = (byte) (primitiveFactory.getShort());
				break;
			case "java.lang.Float":
			case "float":
				value = (float) (primitiveFactory.getFloat());
				break;
			case "java.lang.Double":
			case "double":
				value = (double) (primitiveFactory.getFloat());
				break;
			default:
				value = mockBean(propType);
			}

			// Set the value on the mock.
			when(propDesc.getReadMethod().invoke(mockBean)).thenReturn(value);
		}

		return mockBean;
	}

}
