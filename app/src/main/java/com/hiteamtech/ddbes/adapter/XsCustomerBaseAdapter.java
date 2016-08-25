package com.hiteamtech.ddbes.adapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hiteamtech.ddbes.ui.holder.BaseHolder;

public class XsCustomerBaseAdapter<T> extends BaseAdapter {

	protected List<T> mList;
	protected Activity mContext;
	private Class<? extends BaseHolder<T>> clazz;

	public XsCustomerBaseAdapter(Activity mActivity, List<T> list,
			Class<? extends BaseHolder<T>> clazz) {
		this.mContext = mActivity;
		this.clazz = clazz;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}

	@SuppressWarnings({ "unchecked" })
	private View getItemView(int position, View convertView, ViewGroup parent) {
		BaseHolder<T> holder = null;
		if (convertView == null) {
			try {
				Constructor<? extends BaseHolder<T>> constructor = clazz
						.getConstructor(Activity.class, mList.get(position)
								.getClass());
				holder = constructor.newInstance(mContext, mList.get(position));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			holder = (BaseHolder<T>) convertView.getTag();
			holder.setData(mList.get(position));
		}
		return holder.getRootView();
	}

	public void setList(List<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public List<T> getList() {
		return mList;
	}

	public void cleanData() {
		if (null != mList)
			mList.clear();
		notifyDataSetChanged();
	}

	public void addMoreData(List<T> list) {
		if (mList == null) {
			this.mList = new ArrayList<T>();
			mList.addAll(list);
			notifyDataSetChanged();
		} else {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setList(T[] list) {
		List<T> List = new ArrayList<T>(list.length);
		for (T t : list) {
			List.add(t);
		}
		setList(List);
	}

}
