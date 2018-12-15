package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProviderHome extends AppCompatActivity {
    private ArrayList<String> requests, ids, aptNos, contacts, emails, services, dateTime, statuses, names;

    ListView requestList;
    TextView norequest;
    private String serviceTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);
        initialize();
        setTitle("View Requests");
        serviceTitle = getIntent().getStringExtra("service");
        getData();


        requestList.setAdapter(new Adapter(ProviderHome.this));
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ProviderHome.this, RequestDetails.class)
                        .putExtra("request", requests.get(position))
                        .putExtra("aptNo", aptNos.get(position))
                        .putExtra("contact", contacts.get(position))
                        .putExtra("email", emails.get(position))
                        .putExtra("service", services.get(position))
                        .putExtra("dateTime", dateTime.get(position))
                        .putExtra("status", statuses.get(position))
                        .putExtra("name", names.get(position))
                        .putExtra("id", ids.get(position)));
            }
        });
    }

    private void getData() {
        DBHelper dbHelper = new DBHelper(ProviderHome.this);
        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();

        String query = "select * from requests";

        Cursor next = dbcall.rawQuery(query, null);
        if(next.moveToNext()) {
            do {
                if(serviceTitle.equalsIgnoreCase(next.getString(next.getColumnIndex("service")))) {
                    requests.add(next.getString(next.getColumnIndex("request")));
                    dateTime.add(next.getString(next.getColumnIndex("dateTime")));
                    ids.add(next.getString(next.getColumnIndex("srno")));
                    statuses.add(next.getString(next.getColumnIndex("status")));
                    names.add(next.getString(next.getColumnIndex("name")));
                    emails.add(next.getString(next.getColumnIndex("email")));
                    contacts.add(next.getString(next.getColumnIndex("contact")));
                    aptNos.add(next.getString(next.getColumnIndex("aptNo")));
                    services.add(next.getString(next.getColumnIndex("service")));
                }
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

            return v;
        }
    }

    private void initialize() {
        requestList = findViewById(R.id.requestListPH);
        ids = new ArrayList<>();
        requests = new ArrayList<>();
        aptNos = new ArrayList<>();
        contacts = new ArrayList<>();
        emails = new ArrayList<>();
        services = new ArrayList<>();
        dateTime = new ArrayList<>();
        statuses = new ArrayList<>();
        names = new ArrayList<>();
        norequest = findViewById(R.id.norequestPH);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.check_pending, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.pendingMenu) {
            for(int i = 0; i < statuses.size(); i++) {
                if(!statuses.get(i).equalsIgnoreCase("pending")) {
                    requests.remove(i);
                    services.remove(i);
                    aptNos.remove(i);
                    statuses.remove(i);
                    dateTime.remove(i);
                    ids.remove(i);
                    contacts.remove(i);
                    emails.remove(i);
                    names.remove(i);
                }
            }
            requestList.setAdapter(new Adapter(ProviderHome.this));
        }
        return super.onOptionsItemSelected(item);
    }
}
