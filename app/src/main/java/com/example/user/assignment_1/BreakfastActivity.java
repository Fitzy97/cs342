package com.example.user.assignment_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

public class BreakfastActivity extends MainActivity implements ASyncInterface {

    private TextView breakfastTextTitle;
    private TextView breakfastTextTitle2;
    private TextView breakfastText;
    private TextView breakfastText2;
    private DisplayBreakfast displayBreakfast = new DisplayBreakfast();
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        displayBreakfast.delegate = this;

        try {
            url = new URL("https://www.amherst.edu/campuslife/housing-dining/dining/menu");
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        breakfastTextTitle = (TextView) findViewById(R.id.breakfast_title_text);
        breakfastTextTitle2 = (TextView) findViewById(R.id.breakfast_title_text2);
        breakfastText = (TextView) findViewById(R.id.breakfast_text);
        breakfastText2 = (TextView) findViewById(R.id.breakfast_text2);

        displayBreakfast.execute(url);
    }

    @Override
    public void processFinish(ArrayList<ArrayList<String>> output) {
        ArrayList<TextView> textViews = new ArrayList<TextView>();
        textViews.add(breakfastTextTitle);
        textViews.add(breakfastTextTitle2);
        textViews.add(breakfastText);
        textViews.add(breakfastText2);

        for (int i = 0; i < output.get(0).size(); i++)
            textViews.get(i).setText(output.get(0).get(i));

        for (int i = 0; i < output.get(1).size(); i++)
            textViews.get(i+2).setText(output.get(1).get(i));
    }

    private class DisplayBreakfast extends AsyncTask<URL, Integer, ArrayList<ArrayList<String>>> {

        public ASyncInterface delegate = null;

        public ArrayList<ArrayList<String>> doInBackground(URL... urls) {
            ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
            a.add(parseForBreakfastTitles(urls[0].toString()));
            a.add(parseForBreakfast(urls[0].toString()));
            return a;
        }

        public void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        public void onPostExecute(ArrayList<ArrayList<String>> result) {
            delegate.processFinish(result);
        }

        public ArrayList<String> parseForBreakfastTitles(String url) {
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

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Breakfast-menu-listing";
                Element content = doc.getElementById(tagId);

                Elements contentTypes = content.getElementsByClass("dining-course-name");

                for (Element i:contentTypes)
                    retArr.add(i.text());

            }

            return retArr;
        }

        public ArrayList<String> parseForBreakfast(String url) {
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

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Breakfast-menu-listing";
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