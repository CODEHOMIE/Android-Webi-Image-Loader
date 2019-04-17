package com.johnwebi.webiimageloaderexample.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.johnwebi.webiimageloaderexample.R;

public class WebViewActivity extends AppCompatActivity {

    String ad_url;
    String ad_title;
    private ImageView backBtnIv;
    private TextView toolbarTv;
    private WebView webView;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        BindView();
        initWebView();
        ChangeNotyBarColor();
        initBackBtn();
        initToolBarTv();
    }


    private void BindView()
    {
        ad_url = getIntent().getStringExtra("ad_url");
        ad_title = getIntent().getStringExtra("ad_title");
        webView = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        backBtnIv = (ImageView) findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);
        toolbarTv = findViewById(R.id.toolbarTv);
    }

    private void initWebView()
    {
        progressBar.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                view.loadUrl(ad_url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {

                progressBar.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(ad_url);
    }

    private void ChangeNotyBarColor()
    {
        Window window = WebViewActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.setStatusBarColor(ContextCompat.getColor( WebViewActivity.this,R.color.grey_line));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

    }

    private void initBackBtn()
    {
        backBtnIv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void initToolBarTv()
    {
        toolbarTv.setText(ad_title);
    }

}

