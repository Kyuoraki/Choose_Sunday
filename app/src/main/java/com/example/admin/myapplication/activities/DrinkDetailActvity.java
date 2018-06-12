package com.example.admin.myapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DrinkDetailActvity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_detail_actvity);
        TextView textViewNameDetail = (TextView) findViewById(R.id.textViewNameDetail);
        TextView textViewPriceDetail = (TextView) findViewById(R.id.textViewPriceDetail);
        String name = getIntent().getExtras().getString("name");
        String price = getIntent().getExtras().getString("price");
        textViewNameDetail.setText(name);
        textViewPriceDetail.setText((price));
        new DownloadImagesTask().execute();

    }


    public Bitmap DownloadImageFromPath() {
        InputStream in = null;
        Bitmap bmp = null;
        int responseCode = -1;
        try {

            URL url = new URL("https://mtdata.ru/u22/photo645D/20992151046-0/original.jpg");//"http://192.xx.xx.xx/mypath/img1.jpg
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //download
                in = con.getInputStream();
                bmp = BitmapFactory.decodeStream(in);
                in.close();

            }

        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
        return bmp;
    }

    public class DownloadImagesTask extends AsyncTask<Void, Void, Bitmap> {

        ImageView imageView = (ImageView) findViewById(R.id.img1);



        @Override
        protected Bitmap doInBackground(Void... voids) {
            return DownloadImageFromPath();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
