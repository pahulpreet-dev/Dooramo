package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CheckRequests extends AppCompatActivity {

    //private ArrayList<String> requests, ids, aptNos, contacts, emails, services, dateTime, statuses, names;

    private ArrayList<RequestDetailsHelper> requestsHelperArrayList;

    ListView requestList;
    TextView norequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_requests);
        initialize();
        setTitle("View Requests");
        getData();
        populateRequestHelperArrayList();

        requestList.setAdapter(new Adapter(CheckRequests.this, R.layout.request_layout_inflater,
                requestsHelperArrayList));
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestDetailsHelper dataItem = requestsHelperArrayList.get(position);
                startActivity(new Intent(CheckRequests.this, RequestDetails.class)
                        .putExtra("request", dataItem.getRequest())
                        .putExtra("aptNo", dataItem.getAptNo())
                        .putExtra("contact", dataItem.getContact())
                        .putExtra("email", dataItem.getEmail())
                        .putExtra("service", dataItem.getService())
                        .putExtra("dateTime", dataItem.getDateTime())
                        .putExtra("status", dataItem.getStatus())
                        .putExtra("name", dataItem.getName())
                        .putExtra("id", dataItem.getId()));
            }
        });
    }

    private void populateRequestHelperArrayList() {
//        for(int i=0; i < requests.size(); i++) {
//            requestsHelperArrayList.add(new RequestDetailsHelper(requests.get(i),
//                    ids.get(i),aptNos.get(i),contacts.get(i),emails.get(i),services.get(i)
//                    ,dateTime.get(i),statuses.get(i),names.get(i)));
//        }
        for(RequestDetailsHelper item : requestsHelperArrayList) {
            Log.d("asdf3", item.getRequest());
            Log.d("asdf3", item.getAptNo());
            Log.d("asdf3", item.getId());

        }
    }

    private void getData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("requests");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    RequestDetailsHelper data = null;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                        ids.add(ds.getKey());
//                        requests.add(ds.child("request").getValue(String.class));
//                        dateTime.add(ds.child("dateTime").getValue(String.class));
//                        statuses.add(ds.child("status").getValue(String.class));
//                        names.add(ds.child("name").getValue(String.class));
//                        emails.add(ds.child("email").getValue(String.class));
//                        contacts.add(ds.child("contact").getValue(String.class));
//                        aptNos.add(ds.child("apartment number").getValue(String.class));
//                        services.add(ds.child("service").getValue(String.class));

                        data = new RequestDetailsHelper();
                        data.setId(ds.getKey());
                        Log.d("asdf", ds.getKey());
                        Log.d("asdf2", data.getId());
                        data.setRequest(ds.child("request").getValue(String.class));
                        data.setDateTime(ds.child("dateTime").getValue(String.class));
                        data.setStatus(ds.child("status").getValue(String.class));
                        data.setName(ds.child("name").getValue(String.class));
                        data.setEmail(ds.child("email").getValue(String.class));
                        data.setContact(ds.child("contact").getValue(String.class));
                        data.setAptNo(ds.child("apartment number").getValue(String.class));
                        data.setService(ds.child("service").getValue(String.class));

                        requestsHelperArrayList.add(data);
                    }
                    requestList.setAdapter(new Adapter(CheckRequests.this,
                            R.layout.request_layout_inflater,
                            requestsHelperArrayList));
                    populateRequestHelperArrayList();
                } else {
                    norequest.setVisibility(View.VISIBLE);
                    requestList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        DBHelper dbHelper = new DBHelper(CheckRequests.this);
//        SQLiteDatabase dbcall = dbHelper.getReadableDatabase();
//
//        String query = "select * from requests";
//
//        Cursor next = dbcall.rawQuery(query, null);
//        if(next.moveToNext()) {
//            do {
//                requests.add(next.getString(next.getColumnIndex("request")));
//                dateTime.add(next.getString(next.getColumnIndex("dateTime")));
//                ids.add(next.getString(next.getColumnIndex("srno")));
//                statuses.add(next.getString(next.getColumnIndex("status")));
//                names.add(next.getString(next.getColumnIndex("name")));
//                emails.add(next.getString(next.getColumnIndex("email")));
//                contacts.add(next.getString(next.getColumnIndex("contact")));
//                aptNos.add(next.getString(next.getColumnIndex("aptNo")));
//                services.add(next.getString(next.getColumnIndex("service")));
//            } while (next.moveToNext());
//        } else
//        {
//            norequest.setVisibility(View.VISIBLE);
//            requestList.setVisibility(View.GONE);
//        }
    }

    class Adapter extends ArrayAdapter<RequestDetailsHelper> {

        Context context;
        private ArrayList<RequestDetailsHelper> items = null;
        int layoutResourceId;

        Adapter(Context context, int resource, ArrayList<RequestDetailsHelper> objects) {
            super(context, resource, objects);

            this.layoutResourceId = resource;
            this.context= context;
            this.items=objects;
        }
//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.request_layout_inflater, null);
            TextView requestTv = v.findViewById(R.id.titletextView12RLI);

            RequestDetailsHelper dataItem = items.get(position);

            String reqTemp = dataItem.getRequest();
            if(reqTemp.length() > 25) {
                for (int i = 0; i < reqTemp.length(); i += 25) {
                    reqTemp = reqTemp.substring(i, Math.min(i + 25, reqTemp.length()));
                }
                reqTemp = reqTemp + "...";
            }
            requestTv.setText(reqTemp);
            TextView serviceTv = v.findViewById(R.id.sercvicetextView13RLi);
            serviceTv.setText(serviceTv.getText().toString() + dataItem.getService());
            TextView aptTv = v.findViewById(R.id.aptnotextView14rli);
            aptTv.setText(aptTv.getText().toString() + dataItem.getAptNo());
            TextView status = v.findViewById(R.id.statustextView15rli);
            status.setText(status.getText().toString() + dataItem.getStatus());
            TextView date = v.findViewById(R.id.datetextView16rli);
            date.setText(dataItem.getDateTime());

//            String reqTemp = requests.get(position);
//            if(reqTemp.length() > 25) {
//                for (int i = 0; i < reqTemp.length(); i += 25) {
//                    reqTemp = reqTemp.substring(i, Math.min(i + 25, reqTemp.length()));
//                }
//                reqTemp = reqTemp + "...";
//            }
//            requestTv.setText(reqTemp);
//            TextView serviceTv = v.findViewById(R.id.sercvicetextView13RLi);
//            serviceTv.setText(serviceTv.getText().toString() + services.get(position));
//            TextView aptTv = v.findViewById(R.id.aptnotextView14rli);
//            aptTv.setText(aptTv.getText().toString() + aptNos.get(position));
//            TextView status = v.findViewById(R.id.statustextView15rli);
//            status.setText(status.getText().toString() + statuses.get(position));
//            TextView date = v.findViewById(R.id.datetextView16rli);
//            date.setText(dateTime.get(position));

            return v;
        }
    }

    private void initialize() {
        requestList = findViewById(R.id.requestList);
//        ids = new ArrayList<>();
//        requests = new ArrayList<>();
//        aptNos = new ArrayList<>();
//        contacts = new ArrayList<>();
//        emails = new ArrayList<>();
//        services = new ArrayList<>();
//        dateTime = new ArrayList<>();
//        statuses = new ArrayList<>();
//        names = new ArrayList<>();
        norequest = findViewById(R.id.norequestCR);
        requestsHelperArrayList = new ArrayList<>();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.check_pending, menu);

        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query, searchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText, searchView);
                return true;
            }
        });


        return true;
    }

    private void filterData(String query, SearchView searchView) {
        ArrayList<RequestDetailsHelper> filteredOutput = new ArrayList<>();

        ArrayList<RequestDetailsHelper> output = new ArrayList<>(requestsHelperArrayList);

        if(searchView != null && !query.equalsIgnoreCase("")) {
            for(RequestDetailsHelper item : output) {
                if(item.getRequest().toLowerCase().startsWith(query.toLowerCase())) {
                    filteredOutput.add(item);
                }
            }
        } else {
            filteredOutput = output;
        }

        requestList.setAdapter(new Adapter(CheckRequests.this,R.layout.request_layout_inflater
                , filteredOutput));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.pendingMenu) {
            ArrayList<RequestDetailsHelper> filteredOutput = new ArrayList<>();
            for(RequestDetailsHelper data: requestsHelperArrayList) {
                if(data.getStatus().equalsIgnoreCase("pending")) {
                    filteredOutput.add(data);
                }
            }
            requestList.setAdapter(new Adapter(CheckRequests.this,R.layout.request_layout_inflater,
                    filteredOutput));
//            for(int i = 0; i < statuses.size(); i++) {
//                if(!statuses.get(i).equalsIgnoreCase("pending")) {
//                    requests.remove(i);
//                    services.remove(i);
//                    aptNos.remove(i);
//                    statuses.remove(i);
//                    dateTime.remove(i);
//                    ids.remove(i);
//                    contacts.remove(i);
//                    emails.remove(i);
//                    names.remove(i);
//                }
//            }
//            requestList.setAdapter(new Adapter(CheckRequests.this));
        }
        return super.onOptionsItemSelected(item);
    }
}

