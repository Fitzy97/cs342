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

public class DinnerActivity extends MainActivity implements ASyncInterface {

    private TextView dinnerTextTitle;
    private TextView dinnerTextTitle2;
    private TextView dinnerTextTitle3;
    private TextView dinnerTextTitle4;
    private TextView dinnerTextTitle5;
    private TextView dinnerTextTitle6;
    private TextView dinnerTextTitle7;

    private TextView dinnerText;
    private TextView dinnerText2;
    private TextView dinnerText3;
    private TextView dinnerText4;
    private TextView dinnerText5;
    private TextView dinnerText6;
    private TextView dinnerText7;
    private DisplayDinner displayDinner = new DisplayDinner();
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        displayDinner.delegate = this;

        try {
            url = new URL("https://www.amherst.edu/campuslife/housing-dining/dining/menu");
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

        dinnerTextTitle = (TextView) findViewById(R.id.dinner_title_text);
        dinnerTextTitle2 = (TextView) findViewById(R.id.dinner_title_text2);
        dinnerTextTitle3 = (TextView) findViewById(R.id.dinner_title_text3);
        dinnerTextTitle4 = (TextView) findViewById(R.id.dinner_title_text4);
        dinnerTextTitle5 = (TextView) findViewById(R.id.dinner_title_text5);
        dinnerTextTitle6 = (TextView) findViewById(R.id.dinner_title_text6);
        dinnerTextTitle7 = (TextView) findViewById(R.id.dinner_title_text7);


        dinnerText = (TextView) findViewById(R.id.dinner_text);
        dinnerText2 = (TextView) findViewById(R.id.dinner_text2);
        dinnerText3 = (TextView) findViewById(R.id.dinner_text3);
        dinnerText4 = (TextView) findViewById(R.id.dinner_text4);
        dinnerText5 = (TextView) findViewById(R.id.dinner_text5);
        dinnerText6 = (TextView) findViewById(R.id.dinner_text6);
        dinnerText7 = (TextView) findViewById(R.id.dinner_text7);


        displayDinner.execute(url);
    }

    @Override
    public void processFinish(ArrayList<ArrayList<String>> output) {
        ArrayList<TextView> textViews = new ArrayList<TextView>();
        textViews.add(dinnerTextTitle);
        textViews.add(dinnerTextTitle2);
        textViews.add(dinnerTextTitle3);
        textViews.add(dinnerTextTitle4);
        textViews.add(dinnerTextTitle5);
        textViews.add(dinnerTextTitle6);
        textViews.add(dinnerTextTitle7);
        textViews.add(dinnerText);
        textViews.add(dinnerText2);
        textViews.add(dinnerText3);
        textViews.add(dinnerText4);
        textViews.add(dinnerText5);
        textViews.add(dinnerText6);
        textViews.add(dinnerText7);

        for (int i = 0; i < output.get(0).size(); i++)
            textViews.get(i).setText(output.get(0).get(i));

        for (int i = 0; i < output.get(1).size(); i++)
            textViews.get(i+7).setText(output.get(1).get(i));
    }

    private class DisplayDinner extends AsyncTask<URL, Integer, ArrayList<ArrayList<String>>> {

        public ASyncInterface delegate = null;

        public ArrayList<ArrayList<String>> doInBackground(URL... urls) {
            ArrayList<ArrayList<String>> a = new ArrayList<ArrayList<String>>();
            a.add(parseForDinnerTitles(urls[0].toString()));
            a.add(parseForDinner(urls[0].toString()));
            return a;
        }

        public void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        public void onPostExecute(ArrayList<ArrayList<String>> result) {
            delegate.processFinish(result);
        }

        public ArrayList<String> parseForDinnerTitles(String url) {
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

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Dinner-menu-listing";
                Element content = doc.getElementById(tagId);

                Elements contentTypes = content.getElementsByClass("dining-course-name");

                for (Element i:contentTypes)
                    retArr.add(i.text());

            }

            return retArr;
        }

        public ArrayList<String> parseForDinner(String url) {
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

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Dinner-menu-listing";
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