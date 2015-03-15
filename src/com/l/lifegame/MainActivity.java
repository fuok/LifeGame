package com.l.lifegame;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	//控件
	private GridView gridView;
	private Button btn_inputxy,btn_check,btn_random,btn_clean;
	private ToggleButton btn_toggle;
	private SimpleAdapter adp;
	private EditText input_random;
	private LinearLayout progressbarContainer;
	
	private final static int TOTAL=2520;//gridView中全部的item数
	private ColorTool tool;//颜色变换工具

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tool=new ColorTool();
		//用异步任务的方式加载View,数据源,最后设置adapter和监听器
		new GridViewTask().execute();
	}

	private void findViews() {
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setNumColumns(45);// 设置列数，这样就不用xml设置了//不知为什么,这个最大值只能设置为45列,再多的话会显示异常,可能是Android的Bug
		input_random=(EditText) findViewById(R.id.input_random);
		btn_inputxy = (Button) findViewById(R.id.btn_inputxy);
		btn_check=(Button) findViewById(R.id.btn_check);
		btn_random=(Button) findViewById(R.id.btn_random);
		btn_toggle=(ToggleButton) findViewById(R.id.btn_toggle);
		btn_clean=(Button) findViewById(R.id.btn_clean);
		progressbarContainer=(LinearLayout) findViewById(R.id.progressbarContainer);
	}

	
	private class GridViewTask extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			findViews();
		}
		
		@Override
		protected String doInBackground(String... params) {//数据源
			ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < TOTAL; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("items", R.drawable.dead);// 添加图像资源的ID
				map.put("state", "dead");
				lstImageItem.add(map);
			}
			adp = new SimpleAdapter(MainActivity.this, lstImageItem, R.layout.grid_item, new String[] { "items" }, new int[] { R.id.items });
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			gridView.setAdapter(adp);
			progressbarContainer.setVisibility(View.GONE);
			setListerner();
		}
		
	}

	private void setListerner() {
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(MainActivity.this, "你选择了" + (position + 1) + " 号图片", Toast.LENGTH_SHORT).show();
//
//			}
//		});

		//生命坐标
		btn_inputxy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

//				Log.w("liuy", "str1="+input_x.getText().toString()+",str2="+input_y.getText().toString());
//				String str1=input_x.getText().toString();
//				String str2 = input_y.getText().toString();
//				int x = Util.StringToInt(str1, 0);
//				int y = Util.StringToInt(str2, 0);
//				int columns = gridView.getNumColumns();
//				int position = XYtoPosition(x, y, columns);
//				Toast.makeText(MainActivity.this, "位置是：" + position, Toast.LENGTH_SHORT).show();
////				Toast.makeText(MainActivity.this, "列数是：" + columns, Toast.LENGTH_SHORT).show();
//				
//				//////////////////////////////////////////////////////////
//				// Log.d("liuy", "" + gridView.getItemAtPosition(1).getClass());//输出结果是HashMap
//				int color=tool.getDrawable();
//				changeLifeState(position,color);
			}
		});
		
		//生命检查
		btn_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//轮询全部item，找出状态需要改变的点
				ArrayList<Integer> changeList=new ArrayList<Integer>();
				for (int i = 0; i < TOTAL; i++) {
					if(getNextState(i)){
						Log.w("liuy", "发现生命改变点，position="+i);
						changeList.add(i);
					}
					
				}
				int color=tool.getDrawable();
				for (int j = 0; j < changeList.size(); j++) {
					changeLifeState(changeList.get(j),color);
				}
				
			}
		});
		
		//随机生命
		btn_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num=Util.StringToInt(input_random.getText().toString(), 0);//获取玩家输入的每次随机生命数量
				int num2=num>0?num:100;//如果玩家没有手动输入,就默认100个
				//随机数计算
				GetRandomNumber getRandomNumber=new GetRandomNumber();
				int[] randomList=	getRandomNumber.getNumber2(num2, TOTAL-1, 0);
				int color=tool.getDrawable();
				for (int i = 0; i < randomList.length; i++) {
					changeLifeState(randomList[i],color);
				}
				
				btn_random.setTextColor(getResources().getColor(R.color.black_303_item_text_title));
//				int s=tool.getDrawable();
//				Log.w("liuy", "获取到的颜色="+s);
			}
			
			
		});
		
		//游戏开始/停止
		btn_toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(btn_toggle.isChecked()){
					//开始
					if(isThereAlive()){
						handler.postDelayed(runnable, 10);//这里的时间是距第一次开始执行的时间
					}else{
						Util.showToast(MainActivity.this, "请先创造生命", Toast.LENGTH_SHORT);
						btn_random.setTextColor(getResources().getColor(R.color.pink));
						btn_toggle.setChecked(false);
					}
				}else{
					//停止
					handler.removeCallbacks(runnable);
				}
			}
		});
		
		//清空世界
		btn_clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handler.removeCallbacks(runnable);

				for (int i = 0; i < TOTAL; i++) {
					try {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(i);
							map.put("items", R.drawable.dead);// 也就是说这样就把HashMap覆盖了，起到了修改数据的作用
							map.put("state", "dead");
					} catch (IndexOutOfBoundsException e) {
//						e.printStackTrace();
//						Log.w("liuy", "数组越界");
					}
				}
				adp.notifyDataSetChanged();
				Util.showToast(MainActivity.this, "你的世界重生了", Toast.LENGTH_SHORT);
				btn_toggle.setChecked(false);
			}
			
		});
	}
	
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//轮询全部item，找出状态需要改变的点
			ArrayList<Integer> changeList=new ArrayList<Integer>();
			ArrayList<Integer> unChangeList=new ArrayList<Integer>();//注意,这个用来保存轮询过程中不再改变状态的点,当全部点都不再改变状态时,世界已死
			for (int i = 0; i < TOTAL; i++) {
				if(getNextState(i)){
//					Log.w("liuy", "发现生命改变点，position="+i);
					changeList.add(i);
				}else{
					unChangeList.add(i);
				}
				
			}
			int color=tool.getDrawable();
			for (int j = 0; j < changeList.size(); j++) {
				changeLifeState(changeList.get(j),color);
			}
			handler.postDelayed(this, 700);
			//在Runnable的最后检查一下是不是已经满足停止循环的条件了,切记不能把判断放到postDelayed()前面,否则又开始循环了
			if(unChangeList.size()>=TOTAL){
				Log.w("liuy", "世界已死"+unChangeList.size());
				handler.removeCallbacks(runnable);
				Util.showToast(MainActivity.this, "你的世界消亡了", Toast.LENGTH_SHORT);
				btn_toggle.setChecked(false);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** 将行列数转化为gridView可用的position */
	private int XYtoPosition(final int x, final int y, final int columns) {
		return (y - 1) * columns + x-1;
	}

	/** 将gridView可用的position转化为行列数 */
	private int[] PositonToXY(final int position, final int columns) {
		int y=(position+1)%columns==0?(position+1)/columns:(position+1)/columns+1;
		int x=(position+1)%columns==0?columns:(position+1)%columns;
		return new int[]{x,y};

	}
	
	/**根据position计算出一个点的生命状态以及其周围的生命状态，再根据这个结果返回该点下一时段的生命状态
	 * @param position 生命点的位置，如果输入的是点在gridview中的位置，使用时需要+1*/
	private boolean getNextState(final int position){
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(position);
		String state=(String) map.get("state");//首先获取到当前点的生命状态
		//再获取当前点的XY坐标
		int columns = gridView.getNumColumns();
		int[] XY_0=PositonToXY(position,columns);
		//确定周围8个点的坐标,注意这样算出来的点肯定包含有不存在的越界点
		int[] XY_1=new int[]{XY_0[0]-1,XY_0[1]-1};
		int[] XY_2=new int[]{XY_0[0],XY_0[1]-1};
		int[] XY_3=new int[]{XY_0[0]+1,XY_0[1]-1};
		int[] XY_4=new int[]{XY_0[0]-1,XY_0[1]};
		int[] XY_5=new int[]{XY_0[0]+1,XY_0[1]};
		int[] XY_6=new int[]{XY_0[0]-1,XY_0[1]+1};
		int[] XY_7=new int[]{XY_0[0],XY_0[1]+1};
		int[] XY_8=new int[]{XY_0[0]+1,XY_0[1]+1};
		int[][] data=new int[][]{XY_1,XY_2,XY_3,XY_4,XY_5,XY_6,XY_7,XY_8};
		int sum=0;//8个点中活点个数
//		Log.w("liuy", "position="+position);
		for (int i = 0; i < data.length; i++) {//轮询，确认全部8个点的生命状态
			int[] point=data[i];
			int position_point=XYtoPosition(point[0], point[1], columns);
			try {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map_point = (HashMap<String, Object>) gridView.getItemAtPosition(position_point);
				if(map_point==null){
//					Log.w("liuy", "超出边界，获取到空点了");
				}
				if(map_point!=null){
					String state_point=(String) map_point.get("state");//首先获取到当前点的生命状态
					if(state_point.equals("alive")){
						sum+=1;
					}
				}
			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
//				Log.w("liuy", "崩溃警报，越界数组i="+position_point+",坐标值："+point[0]+"/"+point[1]);
			}
		}
//		Log.w("liuy", "周围活点总数是："+sum);
		
//		当前细胞为死亡状态时，当周围有3个存活细胞时，该细胞变成存活状态。 （模拟繁殖）
//		当前细胞为存活状态时，当周围低于2个（不包含2个）存活细胞时， 该细胞变成死亡状态。（模拟生命数量稀少）
//		当前细胞为存活状态时，当周围有2个或3个存活细胞时， 该细胞保持原样。
//		当前细胞为存活状态时，当周围有3个以上的存活细胞时，该细胞变成死亡状态。（模拟生命数量过多）
		String nextState="dead";
		if(state.equals("alive")){
			if(sum<2){
				nextState="dead";
			}else if(sum>3){
				nextState="dead";
			}else{
				nextState="alive";
			}
		}else if(state.equals("dead")){
			if(sum==3){
				nextState="alive";
			}
		}
//		Log.w("liuy", "state="+state+",nextState="+nextState);
		return !nextState.equals(state);
		
	}
	
	/**改变点的生死状态，非生即死
	 *@param
	 *@param*/
	private void changeLifeState(final int position,final int color){
		
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(position);
			String state=(String) map.get("state");//首先获取到当前点的生命状态
			if(state.equals("alive")){
				map.put("items", R.drawable.dead);// 也就是说这样就把HashMap覆盖了，起到了修改数据的作用
				map.put("state", "dead");
			}else if(state.equals("dead")){
				map.put("items", color);// 也就是说这样就把HashMap覆盖了，起到了修改数据的作用
				map.put("state", "alive");
			}
		
		} catch (IndexOutOfBoundsException e) {
//			e.printStackTrace();
//			Toast.makeText(MainActivity.this, "数组越界", Toast.LENGTH_SHORT).show();
		}
		adp.notifyDataSetChanged();
	}
	
	/**检查世界中是否还有生存的点,用于开局和游戏结束时*/
	private boolean isThereAlive(){
		boolean someOneAlive=false;
		for (int i = 0; i <TOTAL; i++) {//轮询，这里i就等于position
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(i);	
		String state=(String) map.get("state");
		if(state.equals("alive")){
			someOneAlive=true;
		}
	}
		return someOneAlive;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		handler.removeCallbacks(runnable);
		btn_toggle.setChecked(false);
	}
	
	
	/**
	 * 点两次返回键退出程序 
	 * */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 3000) {
					Util.showToast(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT);
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					 System.exit(0);
				}
				return true;
			}

			break;

//		case KeyEvent.KEYCODE_MENU:
//
//			if (menu.isShowing()) {
//				menu.dismiss();
//			} else {
//				menu.showAtLocation(findViewById(R.id.main_container),
//						Gravity.BOTTOM, 0, 0);
//			}
//
//			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
