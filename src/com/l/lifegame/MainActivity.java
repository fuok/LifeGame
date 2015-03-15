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
	//�ؼ�
	private GridView gridView;
	private Button btn_inputxy,btn_check,btn_random,btn_clean;
	private ToggleButton btn_toggle;
	private SimpleAdapter adp;
	private EditText input_random;
	private LinearLayout progressbarContainer;
	
	private final static int TOTAL=2520;//gridView��ȫ����item��
	private ColorTool tool;//��ɫ�任����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tool=new ColorTool();
		//���첽����ķ�ʽ����View,����Դ,�������adapter�ͼ�����
		new GridViewTask().execute();
	}

	private void findViews() {
		gridView = (GridView) findViewById(R.id.gridView);
		gridView.setNumColumns(45);// ���������������Ͳ���xml������//��֪Ϊʲô,������ֵֻ������Ϊ45��,�ٶ�Ļ�����ʾ�쳣,������Android��Bug
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
		protected String doInBackground(String... params) {//����Դ
			ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < TOTAL; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("items", R.drawable.dead);// ���ͼ����Դ��ID
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
//				Toast.makeText(MainActivity.this, "��ѡ����" + (position + 1) + " ��ͼƬ", Toast.LENGTH_SHORT).show();
//
//			}
//		});

		//��������
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
//				Toast.makeText(MainActivity.this, "λ���ǣ�" + position, Toast.LENGTH_SHORT).show();
////				Toast.makeText(MainActivity.this, "�����ǣ�" + columns, Toast.LENGTH_SHORT).show();
//				
//				//////////////////////////////////////////////////////////
//				// Log.d("liuy", "" + gridView.getItemAtPosition(1).getClass());//��������HashMap
//				int color=tool.getDrawable();
//				changeLifeState(position,color);
			}
		});
		
		//�������
		btn_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//��ѯȫ��item���ҳ�״̬��Ҫ�ı�ĵ�
				ArrayList<Integer> changeList=new ArrayList<Integer>();
				for (int i = 0; i < TOTAL; i++) {
					if(getNextState(i)){
						Log.w("liuy", "���������ı�㣬position="+i);
						changeList.add(i);
					}
					
				}
				int color=tool.getDrawable();
				for (int j = 0; j < changeList.size(); j++) {
					changeLifeState(changeList.get(j),color);
				}
				
			}
		});
		
		//�������
		btn_random.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int num=Util.StringToInt(input_random.getText().toString(), 0);//��ȡ��������ÿ�������������
				int num2=num>0?num:100;//������û���ֶ�����,��Ĭ��100��
				//���������
				GetRandomNumber getRandomNumber=new GetRandomNumber();
				int[] randomList=	getRandomNumber.getNumber2(num2, TOTAL-1, 0);
				int color=tool.getDrawable();
				for (int i = 0; i < randomList.length; i++) {
					changeLifeState(randomList[i],color);
				}
				
				btn_random.setTextColor(getResources().getColor(R.color.black_303_item_text_title));
//				int s=tool.getDrawable();
//				Log.w("liuy", "��ȡ������ɫ="+s);
			}
			
			
		});
		
		//��Ϸ��ʼ/ֹͣ
		btn_toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(btn_toggle.isChecked()){
					//��ʼ
					if(isThereAlive()){
						handler.postDelayed(runnable, 10);//�����ʱ���Ǿ��һ�ο�ʼִ�е�ʱ��
					}else{
						Util.showToast(MainActivity.this, "���ȴ�������", Toast.LENGTH_SHORT);
						btn_random.setTextColor(getResources().getColor(R.color.pink));
						btn_toggle.setChecked(false);
					}
				}else{
					//ֹͣ
					handler.removeCallbacks(runnable);
				}
			}
		});
		
		//�������
		btn_clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handler.removeCallbacks(runnable);

				for (int i = 0; i < TOTAL; i++) {
					try {
						@SuppressWarnings("unchecked")
						HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(i);
							map.put("items", R.drawable.dead);// Ҳ����˵�����Ͱ�HashMap�����ˣ������޸����ݵ�����
							map.put("state", "dead");
					} catch (IndexOutOfBoundsException e) {
//						e.printStackTrace();
//						Log.w("liuy", "����Խ��");
					}
				}
				adp.notifyDataSetChanged();
				Util.showToast(MainActivity.this, "�������������", Toast.LENGTH_SHORT);
				btn_toggle.setChecked(false);
			}
			
		});
	}
	
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//��ѯȫ��item���ҳ�״̬��Ҫ�ı�ĵ�
			ArrayList<Integer> changeList=new ArrayList<Integer>();
			ArrayList<Integer> unChangeList=new ArrayList<Integer>();//ע��,�������������ѯ�����в��ٸı�״̬�ĵ�,��ȫ���㶼���ٸı�״̬ʱ,��������
			for (int i = 0; i < TOTAL; i++) {
				if(getNextState(i)){
//					Log.w("liuy", "���������ı�㣬position="+i);
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
			//��Runnable�������һ���ǲ����Ѿ�����ֹͣѭ����������,�мǲ��ܰ��жϷŵ�postDelayed()ǰ��,�����ֿ�ʼѭ����
			if(unChangeList.size()>=TOTAL){
				Log.w("liuy", "��������"+unChangeList.size());
				handler.removeCallbacks(runnable);
				Util.showToast(MainActivity.this, "�������������", Toast.LENGTH_SHORT);
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

	/** ��������ת��ΪgridView���õ�position */
	private int XYtoPosition(final int x, final int y, final int columns) {
		return (y - 1) * columns + x-1;
	}

	/** ��gridView���õ�positionת��Ϊ������ */
	private int[] PositonToXY(final int position, final int columns) {
		int y=(position+1)%columns==0?(position+1)/columns:(position+1)/columns+1;
		int x=(position+1)%columns==0?columns:(position+1)%columns;
		return new int[]{x,y};

	}
	
	/**����position�����һ���������״̬�Լ�����Χ������״̬���ٸ������������ظõ���һʱ�ε�����״̬
	 * @param position �������λ�ã����������ǵ���gridview�е�λ�ã�ʹ��ʱ��Ҫ+1*/
	private boolean getNextState(final int position){
		
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(position);
		String state=(String) map.get("state");//���Ȼ�ȡ����ǰ�������״̬
		//�ٻ�ȡ��ǰ���XY����
		int columns = gridView.getNumColumns();
		int[] XY_0=PositonToXY(position,columns);
		//ȷ����Χ8���������,ע������������ĵ�϶������в����ڵ�Խ���
		int[] XY_1=new int[]{XY_0[0]-1,XY_0[1]-1};
		int[] XY_2=new int[]{XY_0[0],XY_0[1]-1};
		int[] XY_3=new int[]{XY_0[0]+1,XY_0[1]-1};
		int[] XY_4=new int[]{XY_0[0]-1,XY_0[1]};
		int[] XY_5=new int[]{XY_0[0]+1,XY_0[1]};
		int[] XY_6=new int[]{XY_0[0]-1,XY_0[1]+1};
		int[] XY_7=new int[]{XY_0[0],XY_0[1]+1};
		int[] XY_8=new int[]{XY_0[0]+1,XY_0[1]+1};
		int[][] data=new int[][]{XY_1,XY_2,XY_3,XY_4,XY_5,XY_6,XY_7,XY_8};
		int sum=0;//8�����л�����
//		Log.w("liuy", "position="+position);
		for (int i = 0; i < data.length; i++) {//��ѯ��ȷ��ȫ��8���������״̬
			int[] point=data[i];
			int position_point=XYtoPosition(point[0], point[1], columns);
			try {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map_point = (HashMap<String, Object>) gridView.getItemAtPosition(position_point);
				if(map_point==null){
//					Log.w("liuy", "�����߽磬��ȡ���յ���");
				}
				if(map_point!=null){
					String state_point=(String) map_point.get("state");//���Ȼ�ȡ����ǰ�������״̬
					if(state_point.equals("alive")){
						sum+=1;
					}
				}
			} catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
//				Log.w("liuy", "����������Խ������i="+position_point+",����ֵ��"+point[0]+"/"+point[1]);
			}
		}
//		Log.w("liuy", "��Χ��������ǣ�"+sum);
		
//		��ǰϸ��Ϊ����״̬ʱ������Χ��3�����ϸ��ʱ����ϸ����ɴ��״̬�� ��ģ�ֳⷱ��
//		��ǰϸ��Ϊ���״̬ʱ������Χ����2����������2�������ϸ��ʱ�� ��ϸ���������״̬����ģ����������ϡ�٣�
//		��ǰϸ��Ϊ���״̬ʱ������Χ��2����3�����ϸ��ʱ�� ��ϸ������ԭ����
//		��ǰϸ��Ϊ���״̬ʱ������Χ��3�����ϵĴ��ϸ��ʱ����ϸ���������״̬����ģ�������������ࣩ
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
	
	/**�ı�������״̬����������
	 *@param
	 *@param*/
	private void changeLifeState(final int position,final int color){
		
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) gridView.getItemAtPosition(position);
			String state=(String) map.get("state");//���Ȼ�ȡ����ǰ�������״̬
			if(state.equals("alive")){
				map.put("items", R.drawable.dead);// Ҳ����˵�����Ͱ�HashMap�����ˣ������޸����ݵ�����
				map.put("state", "dead");
			}else if(state.equals("dead")){
				map.put("items", color);// Ҳ����˵�����Ͱ�HashMap�����ˣ������޸����ݵ�����
				map.put("state", "alive");
			}
		
		} catch (IndexOutOfBoundsException e) {
//			e.printStackTrace();
//			Toast.makeText(MainActivity.this, "����Խ��", Toast.LENGTH_SHORT).show();
		}
		adp.notifyDataSetChanged();
	}
	
	/**����������Ƿ�������ĵ�,���ڿ��ֺ���Ϸ����ʱ*/
	private boolean isThereAlive(){
		boolean someOneAlive=false;
		for (int i = 0; i <TOTAL; i++) {//��ѯ������i�͵���position
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
	 * �����η��ؼ��˳����� 
	 * */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 3000) {
					Util.showToast(getApplicationContext(), "�ٰ�һ���˳�����",
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
