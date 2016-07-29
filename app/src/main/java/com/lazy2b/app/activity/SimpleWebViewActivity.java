package com.lazy2b.app.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lazy2b.app.R;
import com.lazy2b.libs.app.BaseActivity;

import java.util.Map;

import butterknife.BindView;

/**
 * 类名: WebViewActivity <br/>
 * 描述: TODO. <br/>
 * 功能: <br/>
 * 1 {@link #handPageUrl(WebView, String)} 页面中URL跳转处理 <br/>
 * 2 {@link #getJsInterMap()} 与JS交互<br/>
 * 3 {@link #getTitleArgs()} 标题栏文字<br/>
 * 4 {@link #getUrl()} 页面URL<br/>
 * 
 * @author E-mail:Administrator
 * @version $Id: SimpleWebViewActivity.java 5026 2016-06-03 03:08:36Z huangw $
 */
@SuppressLint("SetJavaScriptEnabled")
public class SimpleWebViewActivity extends BaseActivity {

	// public String baseUrl = "http://a.royaleu.com/service/";

	public String url = "";
	public String title = "";

	public boolean urlGoBrowser = false;

	public String getTitleArgs() {
		return "";

	}
	
	@Override
	public void initData() {
		super.initData();
	}

	public String getUrl() {
		return "";
	}

	/**
	 * 处理页面中的URL跳转，默认跳转浏览器
	 * 
	 * @param view
	 * @param url
	 * @return
	 */
	public boolean handPageUrl(WebView view, String url) {

		if (url.startsWith("tel:")) {
			mCxt.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-654-9200")));
			return true;
		} else {
			// if (urlGoBrowser) {
			// Utils.openBrowser(mCxt, url);
			// } else {
			// mHolder.loadUrl(url);
			// }
		}
		return true;

	}

	/**
	 * 获取与JS交互的接口数据key为JS中调用的名字，value为
	 * 
	 * @return
	 */
	public Map<String, Object> getJsInterMap() {
		return null;
	}

	protected WebViewClient wvc = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return handPageUrl(view, url);
		}

		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		};

		public void onReceivedSslError(WebView view, android.webkit.SslErrorHandler handler,
				android.net.http.SslError error) {
		};

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
//			mUIHandler.sendEmptyMessage(MSG_WHAT_PAGE_FINISHED);
		}
	};

	public static final int MSG_WHAT_PAGE_FINISHED = 1;
	public static final int MSG_WHAT_PAGE_REQ_URL = 2;

	boolean isFail = false;

	@Override
	public void finish() {
		try {
			ViewGroup view = (ViewGroup) getWindow().getDecorView();
			view.removeAllViews();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.finish();
	}

	@BindView(R.id.web_view)
	WebView wv;
	
	@Override
	public void initView() {
		super.initView();
		
		wv.setWebViewClient(wvc);
		wv.loadUrl("http://tms.359e.com/FeedBack/SubmitFeedBack?code=kl8&guId=867031025810224&deviceId=140fe1da9eabb102b63");
		
	}

	void doBack() {

		finish();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			doBack();

			return true;

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected int getContentResId() {
		return com.lazy2b.app.R.layout.more_about_hk_layout;
	}

}
