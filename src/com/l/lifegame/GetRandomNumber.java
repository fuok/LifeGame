package com.l.lifegame;

import java.util.Arrays;
import java.util.Random;

public class GetRandomNumber {
	int[] intRet = new int[6];
	int intRd = 0; // ��������
	int count = 0; // ��¼���ɵ����������
	int flag = 0;// �Ƿ��Ѿ����ɹ���־

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
	
	/**��ȡ��������飨���ظ���
	 * @param count ��Ҫ��ȡ�����������*/
	public int[] getNumber2(final int count,final int Max,final int Min){//  Math.round(Math.random()*(Max-Min)+Min)
		int[] randomArray=new int[count];
//		for (int i = 0; i < randomArray.length; i++) {
//			long num=Math.round(Math.random()*(Max-Min)+Min);
//			int x=Arrays.binarySearch(randomArray, (int)num);
//			if(x==-1){//�ж��ǲ����ظ�����
//				randomArray[]
//			}
//		}
		int i=0;
		do{
			long num=Math.round(Math.random()*(Max-Min)+Min);
			int x=Arrays.binarySearch(randomArray, (int)num);
			if(x<0){//�ж��ǲ����ظ�����
				randomArray[i]=(int)num;
				i+=1;
//				Log.w("liuy", "�����"+i+"="+(int)num);
			}else{
//				Log.w("liuy", "������ظ�!��ֵΪ="+(int)num+",λ����:"+x);//������ʱ�᷵����ʾ�ظ�������������־������û���ظ��ģ��Ҳ�̫��
			}
		}while(i< randomArray.length);
		
		
		return randomArray;
		
	}

}
