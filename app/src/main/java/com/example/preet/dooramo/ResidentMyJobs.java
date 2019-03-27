package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResidentMyJobs extends AppCompatActivity {

    ArrayList<RequestDetailsHelper> requestsList;
    ListView requestListView;
    TextView noRequest;
    private String residentName;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_my_jobs);
        setTitle("My Jobs");
        initComponents();

        residentName = getResidentNameFromPreferences();

        getData();

        requestListView.setAdapter(adapter);
        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ResidentMyJobs.this, RequestDetails.class)
                        .putExtra("request", requestsList.get(position).getRequest())
                        .putExtra("aptNo", requestsList.get(position).getAptNo())
                        .putExtra("contact", requestsList.get(position).getContact())
                        .putExtra("email", requestsList.get(position).getEmail())
                        .putExtra("service", requestsList.get(position).getService())
                        .putExtra("dateTime", requestsList.get(position).getDateTime())
                        .putExtra("status", requestsList.get(position).getStatus())
                        .putExtra("name", requestsList.get(position).getName())
                        .putExtra("residentFlag", "resident")
                        .putExtra("id", requestsList.get(position).getId()));
            }
        });

    }

    //fetch the data from firebase
    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("requests");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clearLists();
                requestListView.setAdapter(adapter);
                if(dataSnapshot.hasChildren()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String residentCheck = ds.child("name").getValue(String.class);
                        if(residentCheck.equalsIgnoreCase(residentName))
                        {
                            RequestDetailsHelper request = new RequestDetailsHelper();
                            request.setId(ds.getKey());
                            request.setRequest(ds.child("request").getValue(String.class));
                            request.setDateTime(ds.child("dateTime").getValue(String.class));
                            request.setStatus(ds.child("status").getValue(String.class));
                            request.setName(ds.child("name").getValue(String.class));
                            request.setEmail(ds.child("email").getValue(String.class));
                            request.setContact(ds.child("contact").getValue(String.class));
                            request.setAptNo(ds.child("apartment number").getValue(String.class));
                            request.setService(ds.child("service").getValue(String.class));
                            requestsList.add(request);
                        }
                    }
                    if(requestsList.size() > 0) {
                        requestListView.setAdapter(adapter);
                    } else {
                        noRequest.setVisibility(View.VISIBLE);
                        requestListView.setVisibility(View.GONE);
                    }
                } else {
                    noRequest.setVisibility(View.VISIBLE);
                    requestListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //fetch resident name
    private String getResidentNameFromPreferences() {
        SharedPreferences sharedPref = ResidentMyJobs.this.getSharedPreferences("ForLogin",
                Context.MODE_PRIVATE);
        return sharedPref.getString("name", "n");
    }

    //initiliaze the components
    private void initComponents() {
        requestsList = new ArrayList<>();
        requestListView = findViewById(R.id.myJobsList);
        noRequest = findViewById(R.id.norequestRMJ);
        adapter = new Adapter(ResidentMyJobs.this);
    }

    //inner class for list view adapter
    private class Adapter extends BaseAdapter {
        Context context;
        public Adapter(Context _context) {
            context = _context;
        }
        @Override
        public int getCount() {
            return requestsList.size();
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
            String reqTemp = requestsList.get(position).getRequest();
            if(reqTemp.length() > 25) {
                for (int i = 0; i < reqTemp.length(); i += 25) {
                    reqTemp = reqTemp.substring(i, Math.min(i + 25, reqTemp.length()));
                }
                reqTemp = reqTemp + "...";
            }
            requestTv.setText(reqTemp);
            TextView serviceTv = v.findViewById(R.id.sercvicetextView13RLi);
            serviceTv.setText(serviceTv.getText().toString() + requestsList.get(position).getService());
            TextView aptTv = v.findViewById(R.id.aptnotextView14rli);
            aptTv.setText(aptTv.getText().toString() + requestsList.get(position).getAptNo());
            TextView status = v.findViewById(R.id.statustextView15rli);
            status.setText(status.getText().toString() + requestsList.get(position).getStatus());
            TextView date = v.findViewById(R.id.datetextView16rli);
            date.setText(requestsList.get(position).getDateTime());

            return v;
        }
    }
}
