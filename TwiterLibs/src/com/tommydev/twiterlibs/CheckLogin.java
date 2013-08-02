package com.tommydev.twiterlibs;

import twitter4j.Twitter;

/**
 * Created by TomMy on 8/1/13.
 */
  interface  Login{
    public  String[]            OnTwitterTwitterReadKEY();
    public  Twitter             CreateTwitter();
    public  void     setURLLogin(String URL);
    public  String     getURLLogin();


}
