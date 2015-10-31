package com.griffiths.hugh.ReflectiveBeanTools.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.griffiths.hugh.ReflectiveBeanTools.mock.BeanMocker;
import com.griffiths.hugh.ReflectiveBeanTools.primitives.StaticPrimitiveFactory;

public class BeanMockerTest {

	@Test
	public void test() throws Exception {
		MyBean b = new BeanMocker(new StaticPrimitiveFactory()).mockBean(MyBean.class);
		
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
		MyCircularBean mcb = new BeanMocker(new StaticPrimitiveFactory()).mockBean(MyCircularBean.class);
		
		assertNotNull(mcb);
		assertNotNull(mcb.getInner());
		assertNotNull(mcb.getInner().getOuter());
	}
	
	@Test
	public void testOverridingValues() throws Exception {
		MyBean mb = new BeanMocker(new StaticPrimitiveFactory()).mockBean(MyBean.class);
		
		assertEquals("horse", mb.getStr());
		when(mb.getStr()).thenReturn("zebra");
		assertEquals("zebra", mb.getStr());
	}
}
