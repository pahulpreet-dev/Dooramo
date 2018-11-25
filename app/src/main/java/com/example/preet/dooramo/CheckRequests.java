package com.example.preet.dooramo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CheckRequests extends AppCompatActivity {

    private ArrayList<String> requests, usernames, aptNos, contacts, emails, services, dateTime, statuses, names;

    ListView requestList;
    TextView norequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requests);
        initialize();

        getData();

        requestList.setAdapter(new Adapter(CheckRequests.this));
    }

    private void getData() {
        DBHelper dbHelper = new DBHelper(CheckRequests.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from requests";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            do {
                requests.add(next.getString(next.getColumnIndex("request")));
                dateTime.add(next.getString(next.getColumnIndex("dateTime")));
                usernames.add(next.getString(next.getColumnIndex("username")));
                statuses.add(next.getString(next.getColumnIndex("status")));
                names.add(next.getString(next.getColumnIndex("name")));
                emails.add(next.getString(next.getColumnIndex("email")));
                contacts.add(next.getString(next.getColumnIndex("contact")));
                aptNos.add(next.getString(next.getColumnIndex("aptNo")));
                services.add(next.getString(next.getColumnIndex("service")));
            } while (next.moveToNext());
        } else
        {
            norequest.setVisibility(View.VISIBLE);
            requestList.setVisibility(View.GONE);
        }
    }

    class Adapter extends BaseAdapter {

        Context context;
        public Adapter(Context _context) {
            context = _context;
        }
        @Override
        public int getCount() {
            return requests.size();
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
            TextView requestTv = v.findViewById(R.id.titletextView12RLI);
            String reqTemp = requests.get(position);
            if(reqTemp.length() > 25) {
                for (int i = 0; i < reqTemp.length(); i += 25) {
                    reqTemp = reqTemp.substring(i, Math.min(i + 25, reqTemp.length()));
                }
                reqTemp = reqTemp + "...";
            }
            requestTv.setText(reqTemp);
            TextView serviceTv = v.findViewById(R.id.sercvicetextView13RLi);
            serviceTv.setText(serviceTv.getText().toString() + services.get(position));
            TextView aptTv = v.findViewById(R.id.aptnotextView14rli);
            aptTv.setText(aptTv.getText().toString() + aptNos.get(position));
            TextView status = v.findViewById(R.id.statustextView15rli);
            status.setText(status.getText().toString() + statuses.get(position));
            TextView date = v.findViewById(R.id.datetextView16rli);
            date.setText(dateTime.get(position));
//            ImageView imageView = v.findViewById(R.id.serviceIV);
//            TextView textView = v.findViewById(R.id.serviceTV);
//
//            imageView.setImageResource(images[position]);
//            textView.setText(imageNames[position]);

            return v;
        }
    }

    private void initialize() {
        requestList = findViewById(R.id.requestList);
        usernames = new ArrayList<>();
        requests = new ArrayList<>();
        aptNos = new ArrayList<>();
        contacts = new ArrayList<>();
        emails = new ArrayList<>();
        services = new ArrayList<>();
        dateTime = new ArrayList<>();
        statuses = new ArrayList<>();
        names = new ArrayList<>();
        norequest = findViewById(R.id.norequestCR);
    }
}
