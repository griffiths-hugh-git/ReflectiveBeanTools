package com.griffiths.hugh.ReflectiveBeanTools.bean;

public class MyCircularBean {
	private InnerCircularBean inner;
	
	public InnerCircularBean getInner() {
		return inner;
	}

	public void setInner(InnerCircularBean inner) {
		this.inner = inner;
	}

	public static class InnerCircularBean{
		private MyCircularBean outer;

		public MyCircularBean getOuter() {
			return outer;
		}

		public void setOuter(MyCircularBean outer) {
			this.outer = outer;
		}
	}
}
