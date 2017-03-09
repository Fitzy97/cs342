package com.example.user.midterm1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.graphics.Paint;
import android.graphics.Color;
import android.content.Context;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button searchButton, clearButton;
    private EditText searchBar;
    private CustomAdapter<String> mAdapter;
    private CustomAdapter<String> mAdapter2;
    private ArrayList<String> data;
    private ListView listView;
    private TextView textView;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater in=getLayoutInflater();
        View  v=in.inflate(R.layout.activity_listview, null);

        searchButton = (Button)findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        clearButton = (Button)findViewById(R.id.clear_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        data = new ArrayList<String>();
        data.add("Chemistry Exam:Study for two hours:Time-permitting");
        data.add("Philosophy Paper:Create outline:Important");
        data.add("Go to the gym:Leg day:Beneficial");
        data.add("Math Problem Set:Section 2.4:Important");
        data.add("Call Mom:Discuss travel plans:Urgent");
        data.add("Meeting with advisor:Think about schedule:Time-permitting");
        data.add("Buy a Yacht:We need this:Urgent");

        mAdapter = new CustomAdapter<String>(this, R.layout.activity_listview, R.id.title, data);
        mAdapter2 = new CustomAdapter<String>(this, R.layout.activity_listview, R.id.description, data);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);

        searchBar = (EditText)findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mAdapter2.getFilter().filter(cs);
                mAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //ctv = (CheckedTextView) findViewById(R.id.data_point);

        checkBox = (CheckBox) v.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton button, boolean checked) {
                System.out.println("CHECKED");
                textView = (TextView) findViewById(R.id.title);
                if (textView != null) {
                    if (checked)
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    else
                        textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("CLICK");
                textView = (TextView) view.findViewById(R.id.title);

                if (textView != null) {
                    System.out.println("FIRST");
                    if (listView.isItemChecked(position)) {
                        System.out.println("CHECK1");
                        listView.setItemChecked(position, true);
                        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {
                        System.out.println("CHECK");
                        listView.setItemChecked(position, false);
                        textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }

        });

    }



    public void search() {

    }

    public void clear() {
        searchBar.setText("");
    }

    private class CustomAdapter<E> extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int resource, int id, ArrayList<String> data) {
            super(context, resource, id, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            String s = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView priority = (TextView) convertView.findViewById(R.id.priority);
            // Populate the data into the template view using the data object
            title.setText(s.split(":")[0]);
            description.setText(s.split(":")[1]);
            priority.setText(s.split(":")[2]);

            String a = s.split(":")[2];
            if (a.equals("Urgent"))
                priority.setTextColor(Color.RED);
            else if (a.equals("Important"))
                priority.setTextColor(Color.CYAN);
            else if (a.equals("Time-permitting"))
                priority.setTextColor(Color.GREEN);
            else
                priority.setTextColor(Color.BLUE);

            // Return the completed view to render on screen
            return convertView;
        }
    }

}
