package com.example.user.assignment_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LunchActivity extends MainActivity implements ASyncInterface {

    private TextView lunchText;
    private TextView lunchText2;
    private TextView lunchText3;
    private TextView lunchText4;
    private TextView lunchText5;
    private DisplayLunch displayLunch = new DisplayLunch();
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        displayLunch.delegate = this;

        try {
            url = new URL("https://www.amherst.edu/campuslife/housing-dining/dining/menu");
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        lunchText = (TextView) findViewById(R.id.lunch_text);
        lunchText2 = (TextView) findViewById(R.id.lunch_text2);
        lunchText3 = (TextView) findViewById(R.id.lunch_text3);
        lunchText4 = (TextView) findViewById(R.id.lunch_text4);
        lunchText5 = (TextView) findViewById(R.id.lunch_text5);

        displayLunch.execute(url);
    }

    @Override
    public void processFinish(ArrayList<String> output) {
        lunchText.setText(output.get(0));
        lunchText2.setText(output.get(1));
        lunchText3.setText(output.get(2));
        lunchText4.setText(output.get(3));
        lunchText5.setText(output.get(4));
    }

    private class DisplayLunch extends AsyncTask<URL, Integer, ArrayList<String>> {

        public ASyncInterface delegate = null;

        public ArrayList<String> doInBackground(URL... urls) {
            return parseForLunch(urls[0].toString());
        }

        public void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        public void onPostExecute(ArrayList<String> result) {
            delegate.processFinish(result);
        }

        public ArrayList<String> parseForLunch(String url) {
            ArrayList<String> retArr = new ArrayList<String>();

            Document doc = null;

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                Log.d(TAG, e.toString());
            }

            if (doc != null) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                String sMonth = Integer.toString(month);
                if (month < 10) {
                    sMonth = "0" + sMonth;
                }
                int day = cal.get(Calendar.DAY_OF_MONTH) + 2;
                String sDay = Integer.toString(day);
                if (day < 10) {
                    sDay = "0" + sDay;
                }

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Lunch-menu-listing";
                System.out.println("LOOK AT ME IM MR MEESEEKS: " + tagId);
                Element content = doc.getElementById(tagId);

                Elements contentTypes = content.getElementsByTag("p");

                for (Element i:contentTypes)
                    retArr.add(i.text());

                //retString += contentTypes.get(0).text();
            }

            return retArr;
        }
    }

}