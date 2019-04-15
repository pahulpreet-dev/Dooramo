package com.example.preet.dooramo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * purpose: for management to see the available service providers
 * author: Pahulpreet Singh and team
 * date: Apr 7, 2019
 * ver: 1
 *
 */
public class SelectProviders extends AppCompatActivity {

    private Button carpenter, electrician, plumber;
    private ListView nameNumberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_providers);
        setTitle("Service Providers");
        initComponents();
        nameNumberList.setVisibility(View.GONE);
        final String[] carpenterNames = {"Bob", "Dolph", "John", "Jinder", "Roman"};
        final String[] carpenterNumbers = {"647 809 7281", "416 120 3839", "416 431 5544", "416 901 8878",
                "647 121 3131"};
        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameNumberList.setAdapter(new Adapter(SelectProviders.this, carpenterNames,
                        carpenterNumbers));
                nameNumberList.setVisibility(View.VISIBLE);
                electrician.setVisibility(View.GONE);
                plumber.setVisibility(View.GONE);
            }
        });
        final String[] electricianName = {"Bobby", "Seth", "Daniel", "Peter"};
        final String[] electricianNumbers = {"647 809 7281", "416 120 3839", "416 431 5544", "416 901 8878"};
        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameNumberList.setAdapter(new Adapter(SelectProviders.this, electricianName,
                        electricianNumbers));
                nameNumberList.setVisibility(View.VISIBLE);
                carpenter.setVisibility(View.GONE);
                plumber.setVisibility(View.GONE);
            }
        });

        final String[] plumberNames = {"Amrit", "Shawn", "Mike", "Randy", "Roddy", "Rey"};
        final String[] plumberNumbers = {"647 809 7281", "416 120 3839", "416 431 5544", "416 901 8878",
                "647 121 3131", "416 289 3636"};
        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameNumberList.setAdapter(new Adapter(SelectProviders.this, plumberNames,
                        plumberNumbers));
                nameNumberList.setVisibility(View.VISIBLE);
                carpenter.setVisibility(View.GONE);
                electrician.setVisibility(View.GONE);
            }
        });

    }
    //inner class for list view adapter
    class Adapter extends BaseAdapter {

        Context context;
        private String[] names;
        private String[] numbers;
        public Adapter(Context _context, String[] _names, String[] _numbers) {
            context = _context;
            names = _names;
            numbers = _numbers;
        }
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.request_layout_inflater, null);

            TextView name = v.findViewById(R.id.titletextView12RLI);
            name.setText(names[position]);

            TextView number = v.findViewById(R.id.sercvicetextView13RLi);
            number.setText(numbers[position]);

            TextView aptTv = v.findViewById(R.id.aptnotextView14rli);
            aptTv.setVisibility(View.GONE);
            TextView status = v.findViewById(R.id.statustextView15rli);
            status.setVisibility(View.GONE);
            TextView date = v.findViewById(R.id.datetextView16rli);
            date.setVisibility(View.GONE);


            return v;
        }
    }
    //initialize the components
    private void initComponents() {
        carpenter = findViewById(R.id.carpenterbuttonsp);
        electrician = findViewById(R.id.electricianbutton3SP);
        plumber = findViewById(R.id.plumberbutton4SP);
        nameNumberList =findViewById(R.id.nameNumberListSP);
    }
}
