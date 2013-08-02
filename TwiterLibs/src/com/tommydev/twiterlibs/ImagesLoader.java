package com.tommydev.twiterlibs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by TomMy on 8/2/13.
 */
public class ImagesLoader extends ImageView {
    public ImagesLoader(Context context) {
        super(context);
    }

    public ImagesLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagesLoader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setImagesUrl(String Url){
        new DownloadImageTask(this).execute(Url);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImagesLoader imagesLoader;
        public DownloadImageTask(ImagesLoader imagesLoader) {
            this.imagesLoader=imagesLoader;
        }

        protected Bitmap doInBackground(String... urls) {
            Log.e("this.imagesLoader",urls[0]);
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPreExecute() {
            this.imagesLoader.setImageResource(R.drawable.l0);
        }

        protected void onPostExecute(Bitmap result) {
            this.imagesLoader.setImageBitmap(result);
        }



    }


}
