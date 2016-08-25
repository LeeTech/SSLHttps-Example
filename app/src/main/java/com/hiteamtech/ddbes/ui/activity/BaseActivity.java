package com.hiteamtech.ddbes.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.hiteamtech.ddbes.R;
import com.hiteamtech.ddbes.base.HiTeamTechApplication;
import com.hiteamtech.ddbes.ui.widget.NormalEmptyView;
import com.hiteamtech.ddbes.util.SharePreUtil;
import com.hiteamtech.ddbes.util.VolleyUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * ClassName: BaseActivity
 * Description: 架构(BaseActivity)
 * author junli Lee mingyangnc@163.com
 * date 2015/11/10 10:57
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {

    /**
     * request
     */
    protected RequestQueue mQueue;

    /**
     * context
     */
    protected Activity mContext = this;

    /**
     * progressDialog
     */
    protected volatile ProgressDialog mDialog;

    /**
     * application
     */
    protected HiTeamTechApplication mApplication;

    /**
     * resources
     */
    protected Resources mResources;

    /**
     * actionBar
     */
    protected ActionBar mActionBar;

    /**
     * LayoutInflater
     */
    protected LayoutInflater mInflater;

    /**
     * exitTime to onBackPressed()
     */
    private long exitTime;

    /**
     * supportBackPressed
     */
    protected boolean supportBackPressed = true;

    /**
     * content Layout params
     */
    protected RelativeLayout.LayoutParams layoutParamsRl;

    /**
     * root Layout
     */
    protected RelativeLayout rootViewRl;

    /**
     * NormalEmptyView is a no content View
     */
    protected NormalEmptyView mEmptyView;

    /**
     * content layout
     */
    protected View contentLayout;

    /**
     * SharedPreferences
     */
    protected SharePreUtil sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
        initBaseView();
        initCustomView(savedInstanceState);
        initCustomData();
    }

    public void initConfig() {
        mQueue = VolleyUtil.getVolleyRequestQueue(this);
        mApplication = (HiTeamTechApplication) getApplication();
        mResources = getResources();
        mActionBar = getSupportActionBar();
        mInflater = LayoutInflater.from(this);
        sp = new SharePreUtil(this);
    }

    public void initBaseView() {
        layoutParamsRl = new RelativeLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.activity_base);
        rootViewRl = (RelativeLayout) findViewById(R.id.rootViewRl);
        contentLayout = findViewById(R.id.contentRl);
        mEmptyView = (NormalEmptyView) findViewById(R.id.mobile_empty_view);
        settingInfo();
    }

    /**
     * @Title: settingTitle
     * @Description: 设定标题属性
     * @return: void 返回类型
     * @date: 2013-8-5 下午5:28:28
     */
    protected abstract void settingInfo();

    /**
     * @Title: initView
     * @Description: (初始化布局文件的控件)
     * @param: savedInstanceState
     * @return: void 返回类型
     * @date: 2013-8-8 上午10:13:40
     */
    protected abstract void initCustomView(Bundle savedInstanceState);

    /**
     * @Title: initData
     * @Description: (给控件赋值)
     * @param: 设定文件
     * @return: void 返回类型
     * @date: 2013-8-8 上午10:13:55
     */
    protected abstract void initCustomData();


    /**
     * 描述：用指定的View填充主界面.
     *
     * @param view
     */
    protected void setHtContentView(View view) {
        rootViewRl.removeViewAt(0);
        rootViewRl.addView(view, 0, layoutParamsRl);
        contentLayout = view;
    }

    /**
     * 描述：用指定资源ID表示的View填充主界面.
     *
     * @param resId 指定的View的资源ID
     */
    protected void setHtContentView(int resId) {
        rootViewRl.removeViewAt(0);
        View view = mInflater.inflate(resId, null);
        rootViewRl.addView(view, 0, layoutParamsRl);
        contentLayout = view;
    }

    /**
     * set show normal view
     *
     * @param isShow
     */
    protected void setShowNormalView(boolean isShow) {
        if (mEmptyView != null) {
            if (isShow) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * click normal view refreshData
     */
    protected void refreshData() {
        setShowNormalView(true);
        mEmptyView
                .setEmptyType(NormalEmptyView.EMPTY_TYPE_LOADING);
    }


    /**
     * @return
     * @Description: 显示进度条
     */
    protected ProgressDialog getProgressDialog() {
        if (mDialog == null) {
            String mMessage = mContext.getString(R.string.load_msg);

            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle(null);
            mDialog.setMessage(mMessage);
            mDialog.setIndeterminate(true);
        }
        return mDialog;
    }

    /**
     * @Description: 关闭进度条
     */
    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mobile_empty_view:
                refreshData();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!supportBackPressed) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } else {
            finish();
        }
    }
}
