package com.griffiths.hugh.ReflectiveBeanTools.bean;

public class MyBean {
	private String str;
	private int i;
	private long l;
	private short s;
	private byte b;
	private float f;
	private double d;
	private InnerBean inner;
	
	public InnerBean getInner() {
		return inner;
	}

	public void setInner(InnerBean inner) {
		this.inner = inner;
	}

	public long getL() {
		return l;
	}

	public void setL(long l) {
		this.l = l;
	}

	public short getS() {
		return s;
	}

	public void setS(short s) {
		this.s = s;
	}

	public byte getB() {
		return b;
	}

	public void setB(byte b) {
		this.b = b;
	}

	public float getF() {
		return f;
	}

	public void setF(float f) {
		this.f = f;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public static class InnerBean {
		private String str;

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
	}
}
