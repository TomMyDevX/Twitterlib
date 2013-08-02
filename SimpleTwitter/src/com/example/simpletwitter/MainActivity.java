package com.example.simpletwitter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import com.tommydev.twiterlibs.ImagesLoader;
import com.tommydev.twiterlibs.TwitterLogin;
import com.tommydev.twiterlibs.WebTwitter;

import twitter4j.Twitter;

public class MainActivity extends Activity {
    int START_BROWSER=10005;
    TwitterLogin twitterLogin;
    Twitter twitter;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        twitterLogin=new TwitterLogin(this);
        if(!twitterLogin.checkAccessTokenData()){
            twitter=twitterLogin.getTwitter();
            Intent browser=new Intent(this,WebTwitter.class);
            browser.putExtra("AuthorizationURL", twitterLogin.getURLLogin());
            startActivityForResult(browser,START_BROWSER);
        }else{
            Twitter twitter1 = twitterLogin.CreateTwitterWithAuthComplete();
            twitterLogin.setImagesProfile2ImagesView(twitter1,((ImagesLoader)findViewById(R.id.imageView)));
        }
    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_BROWSER) {
            if(resultCode==RESULT_OK){
                twitterLogin.setAccessToken(twitter,twitterLogin.getRequestToken(),data.getExtras().getString("PIN"));
                finish();
                Intent in=getIntent();
                startActivity(in);
            }
        }
    }
}
