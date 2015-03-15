package com.l.lifegame;

import java.util.Arrays;
import java.util.Random;

public class GetRandomNumber {
	int[] intRet = new int[6];
	int intRd = 0; // 存放随机数
	int count = 0; // 记录生成的随机数个数
	int flag = 0;// 是否已经生成过标志

	public int[] getNumber() {

		while (count < 6) {
			Random rdm = new Random(System.currentTimeMillis());
			intRd = Math.abs(rdm.nextInt()) % 32 + 1;
			for (int i = 0; i < count; i++) {
				if (intRet[i] == intRd) {
					flag = 1;
					break;
				} else {
					flag = 0;
				}
			}
			if (flag == 0) {
				intRet[count] = intRd;
				count++;
			}
		}
		for (int t = 0; t < 6; t++) {
			System.out.println(t + "->" + intRet[t]);
		}
		return intRet;
	}
	
	/**获取随机数数组（不重复）
	 * @param count 需要获取的随机数数量*/
	public int[] getNumber2(final int count,final int Max,final int Min){//  Math.round(Math.random()*(Max-Min)+Min)
		int[] randomArray=new int[count];
//		for (int i = 0; i < randomArray.length; i++) {
//			long num=Math.round(Math.random()*(Max-Min)+Min);
//			int x=Arrays.binarySearch(randomArray, (int)num);
//			if(x==-1){//判断是不是重复的数
//				randomArray[]
//			}
//		}
		int i=0;
		do{
			long num=Math.round(Math.random()*(Max-Min)+Min);
			int x=Arrays.binarySearch(randomArray, (int)num);
			if(x<0){//判断是不是重复的数
				randomArray[i]=(int)num;
				i+=1;
//				Log.w("liuy", "随机数"+i+"="+(int)num);
			}else{
//				Log.w("liuy", "随机数重复!数值为="+(int)num+",位置是:"+x);//这里有时会返回提示重复的数，但从日志来看是没有重复的，我不太懂
			}
		}while(i< randomArray.length);
		
		
		return randomArray;
		
	}

}
