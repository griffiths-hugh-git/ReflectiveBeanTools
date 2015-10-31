package com.griffiths.hugh.ReflectiveBeanTools.primitives;

public class StaticPrimitiveFactory implements PrimitiveFactory{

	public short getShort() {
		return 4;
	}

	public float getFloat() {
		return 4.1f;
	}

	public String getString() {
		return "horse";
	}

}
