package com.vteba.test;

import com.vteba.utils.date.DateUtils;

public class TestJsonObject {

	public static void main(String[] args) {
//		User user = new User();
//		user.setStart(2);
//		user.setAge(22);
//		user.setName("yinlei");
//		String json = JSON.toJSONString(user);
//		
//		JSONObject object = JSON.parseObject(json);
		
		String id = getSerialNumber(33, 234567);
		long d = System.currentTimeMillis();
		id = getSerialNumber(33, 234567);
		System.out.println(System.currentTimeMillis() - d);
		System.out.println(id);
		d = System.currentTimeMillis();
		System.out.println(String.format("%02d", 44));
		System.out.println(System.currentTimeMillis() - d);
	}
	
	public static String getSerialNumber(Integer itradeType, Integer id) {
        StringBuffer sb = new StringBuffer();
        //sb.append(String.format("%02d", itradeType));
        sb.append(DateUtils.toDateString(DateUtils.YMDHMS));
        //sb.append(String.format("%0" + 10 + "d", id));
        return sb.toString();
    }

}
