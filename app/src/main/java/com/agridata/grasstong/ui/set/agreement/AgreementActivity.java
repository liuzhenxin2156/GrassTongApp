package com.agridata.grasstong.ui.set.agreement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agridata.grasstong.R;
import com.agridata.grasstong.base.BaseActivity;
import com.agridata.grasstong.base.BasePresenter;


/**
 * 我的页面 服务协议 隐私政策
 */
public class AgreementActivity extends BaseActivity {
    private ImageView mBackIv;
    private WebView webView;
    private TextView mTitleTv;
    private int mType;
    private ProgressBar progressBar;


    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int type) {
        Intent intent = new Intent(context, AgreementActivity.class);
        Bundle data = new Bundle();
        data.putInt("type", type);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_agreement;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        mBackIv = findViewById(R.id.back_iv);
        webView = findViewById(R.id.webView);
        mTitleTv = findViewById(R.id.title_tv);
        progressBar = findViewById(R.id.progressBar);
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(true); //垂直不显示
        mBackIv.setOnClickListener(v -> finish());
        getArguments();
    }


    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        mType = bundle.getInt("type");

    }

    @Override
    protected void initDatas() {
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // 忽略SSL验证AS
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitleTv.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
        if (mType == 0) {
            webView.loadUrl("file:///android_asset/html/service.html");
        } else if (mType==1){
            webView.loadUrl("file:///android_asset/html/privacy.html");
        }else {
            webView.loadUrl("file:///android_asset/html/register_service.html");
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
                webView.goBack();
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.webView.destroy();
    }
}
