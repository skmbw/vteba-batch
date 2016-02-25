package com.vteba.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = null;
		Map<String, String> map = Maps.newHashMap();
		System.out.println(map.get(a));
		
		//System.out.println(6.5 * Math.pow(1.1, 10));
		List<String> list = Lists.newArrayList();
		list.add("1");
		list.add("2");
		
		System.out.println(Arrays.toString(list.toArray()));
		Object[] aObjects = new Object[3];
		aObjects[0] = 1;
		aObjects[1] = "2a";
		aObjects[2] = "3";
		
		Object[] objs = list.toArray();
		test1(2, objs);
		test1(0, aObjects);
	}

	public static void test1(int i, Object...objects) {
		System.out.println(Arrays.toString(objects));
		System.out.println("yinlei");
	}
}
