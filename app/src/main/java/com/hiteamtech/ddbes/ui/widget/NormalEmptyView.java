package com.hiteamtech.ddbes.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiteamtech.ddbes.R;


/**
 * 
 * @ClassName: NormalEmptyView
 * @Description: TODO 默认页面显示
 * @author lijun Lee mingyangnc@163.com
 * @date 2014-6-10 下午12:29:53
 */
public class NormalEmptyView extends RelativeLayout {
	public static final int EMPTY_TYPE_LOADING = 1;
	public static final int EMPTY_TYPE_ERROR = 2;
	public static final int EMPTY_TYPE_NOCONTENT = 3;
	public static final int EMPTY_TYPE_LOADING_CACHE = 4;

	public TextView tv_empty_text;
	private ImageView pb_empty_loading;
	private ImageView pb_empty_fail;
	private ImageView iv_empty_nocontent;

	private int mEmptyRes = R.string.content_empty;
	private int mLoadingRes = R.string.loading;
	private int mErrorRes = R.string.network_error;
	private int mEmptyType;

	public NormalEmptyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.normal_empty, this);
		tv_empty_text = (TextView) findViewById(R.id.tv_empty_text);
		pb_empty_loading = (ImageView) findViewById(R.id.pb_empty_loading);
		pb_empty_fail = (ImageView) findViewById(R.id.pb_empty_fail);
		iv_empty_nocontent = (ImageView) findViewById(R.id.iv_empty_nocontent);

		setBackgroundResource(R.color.system_bg);

		setEmptyRes(R.string.content_empty);

		setEmptyType(EMPTY_TYPE_LOADING);
	}

	public void setEmptyType(int type) {
		switch (type) {
		case EMPTY_TYPE_ERROR:
			tv_empty_text.setText(getErrorRes());
			pb_empty_fail.setVisibility(View.VISIBLE);
			pb_empty_loading.setVisibility(View.GONE);
			iv_empty_nocontent.setVisibility(View.GONE);
			setClickable(true);
			break;
		case EMPTY_TYPE_LOADING:
			tv_empty_text.setText(getLoadingRes());
			pb_empty_loading.setVisibility(View.VISIBLE);
			pb_empty_fail.setVisibility(View.GONE);
			iv_empty_nocontent.setVisibility(View.GONE);
			setClickable(false);
			break;
		case EMPTY_TYPE_NOCONTENT:
			tv_empty_text.setText(getEmptyRes());
			iv_empty_nocontent.setVisibility(View.VISIBLE);
			pb_empty_fail.setVisibility(View.GONE);
			pb_empty_loading.setVisibility(View.GONE);
			setClickable(true);
			break;
		case EMPTY_TYPE_LOADING_CACHE:
			this.setVisibility(View.INVISIBLE);
			setClickable(false);
			break;
		}
		mEmptyType = type;
	}

	public int getEmptyType() {
		return mEmptyType;
	}

	public void setEmptyRes(int res) {
		mEmptyRes = res;
	}

	public int getEmptyRes() {
		return mEmptyRes;
	}

	public int getLoadingRes() {
		return mLoadingRes;
	}

	public void setLoadingRes(int loadingRes) {
		this.mLoadingRes = loadingRes;
	}

	public int getErrorRes() {
		return mErrorRes;
	}

	public void setErrorRes(int errorRes) {
		this.mErrorRes = errorRes;
	}

}
