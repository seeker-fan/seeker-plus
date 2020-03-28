package com.wf.seeker.mytest_cases.other;

import java.math.BigDecimal;

/**
 * 
 * @author Fan.W
 * @since 1.8
 */
public class TestBigdecimal {
	public static void main(String[] args) {

		BigDecimal a = new BigDecimal(1.11);
		BigDecimal b = new BigDecimal("1.11");

		System.out.println(a);// 精度丢失
		System.out.println(b);// 精度不丢失
		System.out.println(a.add(b));// 精度丢失

		BigDecimal c = new BigDecimal(Double.toString(1.1));
		BigDecimal d = new BigDecimal(Double.toString(2.2));
		System.out.println(c.add(d).doubleValue());// 精度不丢失

		BigDecimal bg1 = new BigDecimal("1.11");
		BigDecimal bg2 = new BigDecimal("2.22");
		System.out.println(bg1.add(bg2));// 精度不丢失

		// 1.1100000000000000976996261670137755572795867919921875
		// 1.11
		// 2.2200000000000000976996261670137755572795867919921875
		// 3.3
		// 3.33
	}
}
