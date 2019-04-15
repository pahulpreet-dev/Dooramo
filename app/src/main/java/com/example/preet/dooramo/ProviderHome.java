package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * purpose: Home page for Service Providers
 * author: Pahulpreet Singh and team
 * date: Nov 9, 2018
 * ver: 2
 *
 */
public class ProviderHome extends AppCompatActivity {
    private ArrayList<String> requests, ids, aptNos, contacts, emails, services, dateTime,
            statuses, names;

    ListView requestList;
    TextView norequest;
    private String serviceTitle;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_home);
        initialize();
        setTitle("View Requests");
        serviceTitle = getIntent().getStringExtra("service");

        adapter = new Adapter(ProviderHome.this);

        getData();

        requestList.setAdapter(adapter);
        //open and pass values to View Request Details activity
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

    //fetch data for the requests for service providers
    private void getData() {
        clearLists();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("requests");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearLists();
                requestList.setAdapter(adapter);
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String serviceCheck = ds.child("service").getValue(String.class);
                        if (serviceCheck.equalsIgnoreCase(serviceTitle)) {
                            ids.add(ds.getKey());
                            requests.add(ds.child("request").getValue(String.class));
                            dateTime.add(ds.child("dateTime").getValue(String.class));
                            statuses.add(ds.child("status").getValue(String.class));
                            names.add(ds.child("name").getValue(String.class));
                            emails.add(ds.child("email").getValue(String.class));
                            contacts.add(ds.child("contact").getValue(String.class));
                            aptNos.add(ds.child("apartment number").getValue(String.class));
                            services.add(serviceCheck);
                        }
                    }
                    if (requests.size() > 0) {
                        requestList.setAdapter(adapter);
                    } else {
                        norequest.setVisibility(View.VISIBLE);
                        requestList.setVisibility(View.GONE);
                    }
                } else {
                    norequest.setVisibility(View.VISIBLE);
                    requestList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //clear the list view and re-initialize
    private void clearLists() {

        ids.clear();
        requests.clear();
        services.clear();
        statuses.clear();
        names.clear();
        emails.clear();
        contacts.clear();
        aptNos.clear();
        dateTime.clear();
        adapter.notifyDataSetChanged();
    }

    //inner adapter class for list view
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
            if (reqTemp.length() > 25) {
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

    //initialize the components
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

    //create three dot menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.check_pending, menu);
        return true;
    }

    //click listener for three dot menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.pendingMenu) {
            for (int i = 0; i < statuses.size(); i++) {
                if (!statuses.get(i).equalsIgnoreCase("pending")) {
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
