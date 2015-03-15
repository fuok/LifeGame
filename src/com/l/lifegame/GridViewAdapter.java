package com.l.lifegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	/*
	 * Inflater字面意思是膨胀，在Android中表示扩展， LayoutInflater作用类似于findViewById()方法，
	 * 不同之处是LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化。
	 * findViewById()是找xml中具体的控件
	 */
	private LayoutInflater mInflater;

	// 整型数组，图片源
	// private Integer[] mImageIds = { R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher, R.drawable.ic_launcher,
	// R.drawable.ic_launcher };

	public GridViewAdapter(Context c) {
		mContext = c;
		// 初始化布局关联对象
		mInflater = LayoutInflater.from(c);
	}

	// 获取图片个数
	@Override
	public int getCount() {
		return 100;//XXX
	}

	// 获取图片在库中的位置
	@Override
	public Object getItem(int position) {
		return position;
	}

	// 获取图片ID
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * getView(返回新的视图并显示在ListView上)
	 * 
	 * @param position
	 *            适配器数据的位置，可理解为索引
	 * @param convertView
	 *            重用旧视图
	 * @param parent
	 *            相关联的父视图
	 * @return 由适配器创建的视图（可为不同类型）
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 此方法体不能省略，否则第二项listView不能显示
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.grid_item, null);
			holder.items = (View) convertView.findViewById(R.id.items);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.items.setBackgroundColor(434343);

		return convertView;
	}

	// UI视图容器类
	final class ViewHolder {
		public View items;
	}

}