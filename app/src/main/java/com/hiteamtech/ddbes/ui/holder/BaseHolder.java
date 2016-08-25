package com.hiteamtech.ddbes.ui.holder;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @ClassName: BaseHolder
 * @Description: TODO Holder基本类
 * @author lijun mingyangnc@163.com
 * @date 2015-8-21 上午9:37:25
 * @param <T>
 */
public abstract class BaseHolder<T> implements OnClickListener {
	protected Activity mActivity;
	protected View mRootView;
	protected int mPosition;
	protected T t;
	
	

	public BaseHolder(Activity activity, T t) {
		mActivity = activity;
		this.t = t;
		mRootView = initView();
		mRootView.setTag(this);
		setData(t);
	}

	public Activity getActivity() {
		return mActivity;
	}

	public View getRootView() {
		return mRootView;
	}

	public void setData(T t) {
		this.t = t;
		refreshView();
	}

	public T getData() {
		return this.t;
	}

	public void setPosition(int position) {
		mPosition = position;
	}

	public int getPosition() {
		return mPosition;
	}

	/** 子类必须覆盖用于实现UI初始化 */
	protected abstract View initView();

	/** 子类必须覆盖用于实现UI刷新 */
	protected abstract void refreshView();

	/** 用于回收 */
	public void recycle() {

	}

	@Override
	public void onClick(View v) {

	}
}
