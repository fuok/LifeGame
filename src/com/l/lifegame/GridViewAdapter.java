package com.l.lifegame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	/*
	 * Inflater������˼�����ͣ���Android�б�ʾ��չ�� LayoutInflater����������findViewById()������
	 * ��֮ͬ����LayoutInflater��������layout�ļ����µ�xml�����ļ�������ʵ������
	 * findViewById()����xml�о���Ŀؼ�
	 */
	private LayoutInflater mInflater;

	// �������飬ͼƬԴ
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
		// ��ʼ�����ֹ�������
		mInflater = LayoutInflater.from(c);
	}

	// ��ȡͼƬ����
	@Override
	public int getCount() {
		return 100;//XXX
	}

	// ��ȡͼƬ�ڿ��е�λ��
	@Override
	public Object getItem(int position) {
		return position;
	}

	// ��ȡͼƬID
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * getView(�����µ���ͼ����ʾ��ListView��)
	 * 
	 * @param position
	 *            ���������ݵ�λ�ã������Ϊ����
	 * @param convertView
	 *            ���þ���ͼ
	 * @param parent
	 *            ������ĸ���ͼ
	 * @return ����������������ͼ����Ϊ��ͬ���ͣ�
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// �˷����岻��ʡ�ԣ�����ڶ���listView������ʾ
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

	// UI��ͼ������
	final class ViewHolder {
		public View items;
	}

}