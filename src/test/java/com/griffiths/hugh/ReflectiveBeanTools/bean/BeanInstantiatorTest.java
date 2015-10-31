package com.griffiths.hugh.ReflectiveBeanTools.bean;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.griffiths.hugh.ReflectiveBeanTools.bean.BeanInstantiator;
import com.griffiths.hugh.ReflectiveBeanTools.primitives.StaticPrimitiveFactory;

public class BeanInstantiatorTest {

	@Test
	public void test() throws Exception {
		MyBean b = new BeanInstantiator(new StaticPrimitiveFactory()).instantiate(MyBean.class);
		
		assertNotNull(b);
		
		// Primitives
		assertNotNull(b.getStr());
		assertNotNull(b.getI());
		assertNotNull(b.getL());
		assertNotNull(b.getB());
		assertNotNull(b.getS());
		assertNotNull(b.getF());
		assertNotNull(b.getD());
		
		// Graph traversal
		assertNotNull(b.getInner());
	}

	@Test
	public void testCyclicDependencies() throws Exception {
		MyCircularBean mcb = new BeanInstantiator(new StaticPrimitiveFactory()).instantiate(MyCircularBean.class);
		
		assertNotNull(mcb);
		assertNotNull(mcb.getInner());
		assertNotNull(mcb.getInner().getOuter());
	}
}
