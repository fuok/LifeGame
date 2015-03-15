/**
 * 
 */
package com.l.lifegame;

/**
 * @author Administrator
 * 
 */
public class ColorTool {

	private int alive1 = R.drawable.alive1;
	private int alive2 = R.drawable.alive2;
	private int alive3 = R.drawable.alive3;
	private int alive4 = R.drawable.alive4;
	private int alive5 = R.drawable.alive5;
	private int alive6 = R.drawable.alive6;
	private int alive7 = R.drawable.alive7;

	private int[] colors = new int[]{alive1, alive2, alive3, alive4, alive5,
			alive6, alive7};

	private int Flag;
	public ColorTool() {
		Flag = 1;
	}
	public int getDrawable() {
		int x = 0;
		switch (Flag) {
			case 1 :
				x = alive1;
				Flag += 1;
				break;

			case 2 :
				x = alive2;
				Flag += 1;
				break;
			case 3 :
				x = alive3;
				Flag += 1;
				break;
			case 4 :
				x = alive4;
				Flag += 1;
				break;
			case 5 :
				x = alive5;
				Flag += 1;
				break;
			case 6 :
				x = alive6;
				Flag += 1;
				break;
			case 7 :
				x = alive7;
				Flag = 1;
				break;
			default :
				break;
		}

		return x;

	}

}
