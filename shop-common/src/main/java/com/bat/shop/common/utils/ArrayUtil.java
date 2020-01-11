package com.bat.shop.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhouR
 * @date: Create in 2019/12/12 - 15:27
 * @function: 集合工具类
 */
public class ArrayUtil {

	/**
	 * 将1,2,3,4,字符串变为数字集合
	 * @param ids
	 * @return
	 */
	public static List<Integer> StringToIntegerList(String ids){
		List<Integer> list = new ArrayList<>();
		String[] split = ids.split(",");
		for (String s : split) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}

	/**
	 * 将数字集合变为数字数组
	 * @param list
	 * @return
	 */
	public static int[] listToIntArr(List<Integer> list){
		int[] arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}





}
