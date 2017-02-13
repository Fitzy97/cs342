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

public class DinnerActivity extends MainActivity implements ASyncInterface {

    private TextView dinnerText;
    private TextView dinnerText2;
    private TextView dinnerText3;
    private TextView dinnerText4;
    private TextView dinnerText5;
    private TextView dinnerText6;
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

        dinnerText = (TextView) findViewById(R.id.dinner_text);
        dinnerText2 = (TextView) findViewById(R.id.dinner_text2);
        dinnerText3 = (TextView) findViewById(R.id.dinner_text3);
        dinnerText4 = (TextView) findViewById(R.id.dinner_text4);
        dinnerText5 = (TextView) findViewById(R.id.dinner_text5);
        dinnerText6 = (TextView) findViewById(R.id.dinner_text6);

        displayDinner.execute(url);
    }

    @Override
    public void processFinish(ArrayList<String> output) {
        dinnerText.setText(output.get(0));
        dinnerText2.setText(output.get(1));
        dinnerText3.setText(output.get(2));
        dinnerText4.setText(output.get(3));
        dinnerText5.setText(output.get(4));
        dinnerText6.setText(output.get(5));
    }

    private class DisplayDinner extends AsyncTask<URL, Integer, ArrayList<String>> {

        public ASyncInterface delegate = null;

        public ArrayList<String> doInBackground(URL... urls) {
            return parseForDinner(urls[0].toString());
        }

        public void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        @Override
        public void onPostExecute(ArrayList<String> result) {
            delegate.processFinish(result);
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
                int day = cal.get(Calendar.DAY_OF_MONTH) + 2;
                String sDay = Integer.toString(day);
                if (day < 10) {
                    sDay = "0" + sDay;
                }

                String tagId = "dining-menu-" + year + "-" + sMonth + "-" + sDay + "-Dinner-menu-listing";
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