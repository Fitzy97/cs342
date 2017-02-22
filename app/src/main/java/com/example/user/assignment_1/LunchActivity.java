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
import org.w3c.dom.Text;

public class LunchActivity extends MainActivity implements ASyncInterface {

    private TextView lunchTextTitle;
    private TextView lunchTextTitle2;
    private TextView lunchTextTitle3;
    private TextView lunchTextTitle4;
    private TextView lunchTextTitle5;
    private TextView lunchTextTitle6;
    private TextView lunchTextTitle7;

    private TextView lunchText;
    private TextView lunchText2;
    private TextView lunchText3;
    private TextView lunchText4;
    private TextView lunchText5;
    private TextView lunchText6;
    private TextView lunchText7;
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

        lunchTextTitle = (TextView) findViewById(R.id.lunch_title_text);
        lunchTextTitle2 = (TextView) findViewById(R.id.lunch_title_text2);
        lunchTextTitle3 = (TextView) findViewById(R.id.lunch_title_text3);
        lunchTextTitle4 = (TextView) findViewById(R.id.lunch_title_text4);
        lunchTextTitle5 = (TextView) findViewById(R.id.lunch_title_text5);
        lunchTextTitle6 = (TextView) findViewById(R.id.lunch_title_text6);
        lunchTextTitle7 = (TextView) findViewById(R.id.lunch_title_text7);

        lunchText = (TextView) findViewById(R.id.lunch_text);
        lunchText2 = (TextView) findViewById(R.id.lunch_text2);
        lunchText3 = (TextView) findViewById(R.id.lunch_text3);
        lunchText4 = (TextView) findViewById(R.id.lunch_text4);
        lunchText5 = (TextView) findViewById(R.id.lunch_text5);
        lunchText6 = (TextView) findViewById(R.id.lunch_text6);
        lunchText7 = (TextView) findViewById(R.id.lunch_text7);

        displayLunch.execute(url);
    }

    @Override
    public void processFinish(ArrayList<ArrayList<String>> output) {
        ArrayList<TextView> textViews = new ArrayList<TextView>();
        textViews.add(lunchTextTitle);
        textViews.add(lunchTextTitle2);
        textViews.add(lunchTextTitle3);
        textViews.add(lunchTextTitle4);
        textViews.add(lunchTextTitle5);
        textViews.add(lunchTextTitle6);
        textViews.add(lunchTextTitle7);
        textViews.add(lunchText);
        textViews.add(lunchText2);
        textViews.add(lunchText3);
        textViews.add(lunchText4);
        textViews.add(lunchText5);
        textViews.add(lunchText6);
        textViews.add(lunchText7);

        for (int i = 0; i < output.get(0).size(); i++)
            textViews.get(i).setText(output.get(0).get(i));

        for (int i = 0; i < output.get(1).size(); i++)
            textViews.get(i+7).setText(output.get(1).get(i));
    }

    private class DisplayLunch extends AsyncTask<URL, Integer, ArrayList<ArrayList<String>>> {

        public ASyncInterface delegate = null;

        public ArrayList<ArrayList<String>> doInBackground(URL... urls) {
            ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
            a.add(parseForLunchTitles(urls[0].toString()));
            a.add(parseForLunch(urls[0].toString()));
            return a;
        }

        public void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        public void onPostExecute(ArrayList<ArrayList<String>> result) {
            delegate.processFinish(result);
        }

        public ArrayList<String> parseForLunchTitles(String url) {
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
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String sDay = Integer.toString(day);
                if (day < 10) {
                    sDay = "0" + sDay;
                }

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Lunch-menu-listing";
                Element content = doc.getElementById(tagId);

                Elements titleTypes = content.getElementsByClass("dining-course-name");

                for (Element i:titleTypes)
                    retArr.add(i.text());
            }
            return retArr;
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
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String sDay = Integer.toString(day);
                if (day < 10) {
                    sDay = "0" + sDay;
                }

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Lunch-menu-listing";

                Element content = doc.getElementById(tagId);

                Elements contentTypes = content.getElementsByTag("p");

                for (Element i:contentTypes)
                    retArr.add(i.text());

            }

            return retArr;
        }
    }

}