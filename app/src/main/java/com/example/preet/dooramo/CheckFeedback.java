package com.example.preet.dooramo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * purpose: For management to check the all feedback submitted by the residents for the completed jobs
 * author: Pahulpreet Singh and team
 * date: Nov 25, 2018
 * ver: 1
 */

public class CheckFeedback extends AppCompatActivity {

    ListView feedbackListView;
    ArrayList<HashMap<String, String>> feedbackList;
    ArrayList<HashMap<String, String>> requestDetailsList;
    TextView noRequests;
    HashMap<String, String> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_feedback);
        initComponents();
        getData();
        setTitle("View Feedback");

        feedbackListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CheckFeedback.this, FeedbackDetails.class)
                        .putExtra("feedback", feedbackList.get(position))
                        .putExtra("requestDetails", requestDetailsList.get(position)));
            }
        });
    }

    //get feedback from the database
    private void getData() {
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference feedbackReference = db.child("requestFeedback");
        feedbackReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackList.clear();
                if(dataSnapshot.hasChildren()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        HashMap<String, String> values = new HashMap<>();
                        values.put("requestId", ds.getKey());
                        values.put("rating", ds.child("rating").getValue(String.class));
                        values.put("comment", ds.child("comment").getValue(String.class));
                        feedbackList.add(values);
                    }
                    getRequestDetails();
                } else {
                    feedbackListView.setVisibility(View.GONE);
                    noRequests.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //get details of a particular feedback
    private void getRequestDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("requests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestDetailsList.clear();
                for(HashMap<String, String> map : feedbackList) {
                    HashMap<String, String> values = new HashMap<>();
                    values.put("aptNo", dataSnapshot.child(map.get("requestId"))
                            .child("apartment number").getValue(String.class));
                    values.put("contact", dataSnapshot.child(map.get("requestId"))
                            .child("contact").getValue(String.class));
                    values.put("dateTime", dataSnapshot.child(map.get("requestId"))
                            .child("dateTime").getValue(String.class));
                    values.put("email", dataSnapshot.child(map.get("requestId"))
                            .child("email").getValue(String.class));
                    values.put("name", dataSnapshot.child(map.get("requestId"))
                            .child("name").getValue(String.class));
                    values.put("request", dataSnapshot.child(map.get("requestId"))
                            .child("request").getValue(String.class));
                    values.put("service", dataSnapshot.child(map.get("requestId"))
                            .child("service").getValue(String.class));
                    requestDetailsList.add(values);
                }
                feedbackListView.setAdapter(new Adapter(CheckFeedback.this, R.layout.request_layout_inflater,
                        feedbackList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //initialize components
    private void initComponents() {
        feedbackListView = findViewById(R.id.checkFeedbackListView);
        feedbackList = new ArrayList<>();
        noRequests = findViewById(R.id.norequestCF);
        requestDetailsList = new ArrayList<>();
    }

    //adapter for the list view
    private class Adapter extends ArrayAdapter<HashMap<String, String>> {

        private ArrayList<HashMap<String, String>> data;

        public Adapter(@NonNull Context context, int resource, ArrayList<HashMap<String, String>> objects) {
            super(context, resource, objects);
            this.data = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) CheckFeedback.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.request_layout_inflater, null);

            TextView requestTv = v.findViewById(R.id.titletextView12RLI);
            requestTv.setText(requestDetailsList.get(position).get("request"));
            TextView ratingTv = v.findViewById(R.id.sercvicetextView13RLi);
            ratingTv.setText("Rating: " + data.get(position).get("rating"));
            TextView commenttV = v.findViewById(R.id.aptnotextView14rli);
            commenttV.setText("Comments: " + data.get(position).get("comment"));
            TextView serviceTv = v.findViewById(R.id.statustextView15rli);
            serviceTv.setText("Service: " + requestDetailsList.get(position).get("service"));
            TextView date = v.findViewById(R.id.datetextView16rli);
            date.setText(requestDetailsList.get(position).get("dateTime"));

            return v;
        }
    }
}
