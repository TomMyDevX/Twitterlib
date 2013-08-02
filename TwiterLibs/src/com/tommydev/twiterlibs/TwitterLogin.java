package com.tommydev.twiterlibs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by TomMy on 8/1/13.
 */
public class TwitterLogin implements Login {
    Context context;
    String Login_URL = "";
    RequestToken requestToken;
    AccessToken accessToken;
    Twitter twitter;
    public TwitterLogin(Context context) {
        this.context = context;
    }
public boolean checkAccessTokenData(){
    boolean state=false;
    if(OnTwitterTwitterReadKEY()[0].equals("")||OnTwitterTwitterReadKEY()[1].equals("")){
        state=false;
        setTwitter(CreateTwitter());
    }else{
        state=true;
    }

    return state;
}

    @Override
    public String[] OnTwitterTwitterReadKEY() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String accessToken = preferences.getString("accessToken", "");
        String accessTokenSecret = preferences.getString("accessTokenSecret", "");
        return new String[]{accessToken, accessTokenSecret};
    }

    @Override
    public Twitter CreateTwitter() {
        Twitter twitter = null;
        final String[] strings = OnTwitterTwitterReadKEY();
       // if (strings[0].equals("") && strings[1].equals("")) {
            twitter = new TwitterFactory().getInstance();
            twitter.setOAuthConsumer(context.getResources().getString(R.string.CONSUMER_KEY), context.getResources().getString(R.string.CONSUMER_KEY_SECRET));
            try {
                setRequestToken(twitter.getOAuthRequestToken());
                setURLLogin(getRequestToken().getAuthorizationURL());

            } catch (TwitterException e) {
                e.printStackTrace();
            }

      //  }
        return twitter;
    }

    @Override
    public String getURLLogin() {
        return this.Login_URL;
    }

    @Override
    public void setURLLogin(String Login_URL) {
        this.Login_URL = Login_URL;
    }


    public boolean saveAccessToken(AccessToken AccessToken) {
        Log.e("Dave",AccessToken.getToken()+"|"+AccessToken.getTokenSecret());
        SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accessToken", AccessToken.getToken());
        editor.putString("accessTokenSecret", AccessToken.getTokenSecret());
        editor.commit();
        return true;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(RequestToken requestToken) {

        this.requestToken = requestToken;
    }



    public void setAccessToken(Twitter twitter,RequestToken requestToken,String pin) {
        try {
            saveAccessToken(twitter.getOAuthAccessToken(requestToken, pin));
        } catch (TwitterException e) {
           Log.e("xxx",""+e.getMessage());
        }
        ;
    }

    public Twitter CreateTwitterWithAuthComplete(){
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(context.getResources().getString(R.string.CONSUMER_KEY), context.getResources().getString(R.string.CONSUMER_KEY_SECRET));
        String accessToken =OnTwitterTwitterReadKEY()[0];
        String accessTokenSecret = OnTwitterTwitterReadKEY()[1];
        AccessToken oathAccessToken = new AccessToken(accessToken,accessTokenSecret);
        twitter.setOAuthAccessToken(oathAccessToken);
        return twitter;
    }
    public void setImagesProfile2ImagesView(Twitter twitter,ImagesLoader imagesLoader){
        try {
            User showUser = twitter.showUser(twitter.getId());
            String profileImageURL = showUser.getOriginalProfileImageURL();
            Log.e("profileImageURL",profileImageURL);
            imagesLoader.setImagesUrl(profileImageURL);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
