package com.tommydev.twiterlibs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by TomMy on 8/1/13.
 */
public class WebTwitter extends Activity {
    WebView WebView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twitter_browser);
        WebView=((WebView)findViewById(R.id.webView));
        if(getIntent().getExtras()!=null){
            PrintLog.print(this,"AuthorizationURL="+getIntent().getExtras().getString("AuthorizationURL"));
            WebView.getSettings().setJavaScriptEnabled(true);
            WebView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
            WebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    WebView.loadUrl("javascript:window.HtmlViewer.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                }
            });

            WebView.loadUrl(getIntent().getExtras().getString("AuthorizationURL"));

        }
    }
    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
                int index=html.indexOf("<code>");
            String c="";
            for(int i=0;i<20;i++){
                c+=html.charAt(2709+i);
            }
        if(index!=-1){
            Log.e("WEB",index+"|"+c.substring(6,13));
            Intent in=new Intent();
            in.putExtra("PIN",c.substring(6,13));
            setResult(RESULT_OK,in);
            finish();
        }
        }
    }
}