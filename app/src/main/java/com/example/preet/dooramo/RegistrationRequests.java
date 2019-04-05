package com.example.preet.dooramo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;
import java.util.Map;

public class RegistrationRequests extends AppCompatActivity {

    private ListView regRequestsList;
    private RegistrationRequestsHelper requestsHelper;
    private ArrayList<RegistrationRequestsHelper> requestsHelperList;
    private int reqCountFlag = 0;
    private TextView noRequestTv;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_requests);
        initComponents();
        getData();

        regRequestsList.setAdapter(new Adapter(RegistrationRequests.this,
                R.layout.request_layout_inflater, requestsHelperList));
        regRequestsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationRequests.this);
                final String requestFlagBy = requestsHelperList.get(position)
                        .getRequestByFlag();
                builder.setMessage("Accept Registration request?")
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                acceptRegistration(requestFlagBy, position);
                                getData();
                            }
                        })
                        .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                declineRegistration(requestFlagBy, position);
                                getData();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void declineRegistration(String requestFlagBy, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(requestFlagBy.equals("resident")) {
            DatabaseReference userRequestsReference = databaseReference.child("userInfo/usernames");
            String username = requestsHelperList.get(position).getUsername();
            userRequestsReference.child(username).removeValue();
            userRequestsReference = databaseReference.child("userAccount/usernames");
            userRequestsReference.child(username).removeValue();
        } else if(requestFlagBy.equals("provider")) {
            DatabaseReference userRequestsReference = databaseReference.child("serviceProviderInfo/usernames");
            String username = requestsHelperList.get(position).getUsername();
            userRequestsReference.child(username).removeValue();
            userRequestsReference = databaseReference.child("serviceProviderAccount/usernames");
            userRequestsReference.child(username).removeValue();
        }
    }

    private void acceptRegistration(String requestFlagBy, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if(requestFlagBy.equals("resident")) {
            DatabaseReference userRequestsReference = databaseReference.child("userInfo/usernames");
            String username = requestsHelperList.get(position).getUsername();
            userRequestsReference.child(username).child("verification").setValue("done");
            userRequestsReference = databaseReference.child("userAccount/usernames");
            userRequestsReference.child(username).child("verification").setValue("done");
        } else if(requestFlagBy.equals("provider")) {
            DatabaseReference userRequestsReference = databaseReference.child("serviceProviderInfo/usernames");
            String username = requestsHelperList.get(position).getUsername();
            userRequestsReference.child(username).child("verification").setValue("done");
            userRequestsReference = databaseReference.child("serviceProviderAccount/usernames");
            userRequestsReference.child(username).child("verification").setValue("done");
        }
    }

    private void getData() {
        requestsHelperList.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRequestsReference = databaseReference.child("userInfo/usernames");
        userRequestsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child("verification").getValue(String.class).equals("pending")) {
                            reqCountFlag = 1;
                            requestsHelper = new RegistrationRequestsHelper();
                            requestsHelper.setUsername(ds.getKey());
                            requestsHelper.setName(ds.child("name").getValue(String.class));
                            requestsHelper.setEmail(ds.child("email").getValue(String.class));
                            requestsHelper.setPhone(ds.child("number").getValue(String.class));
                            requestsHelper.setMetaData(ds.child("apartment number").getValue(String.class));
                            requestsHelper.setRequestByFlag("resident");
                            requestsHelperList.add(requestsHelper);
                        }
                    }
                } else reqCountFlag = 0;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference providerRequestsReference = databaseReference
                .child("serviceProviderInfo/usernames");
        providerRequestsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child("verification").getValue(String.class).equals("pending")) {
                            requestsHelper = new RegistrationRequestsHelper();
                            requestsHelper.setUsername(ds.getKey());
                            requestsHelper.setName(ds.child("name").getValue(String.class));
                            requestsHelper.setEmail(ds.child("email").getValue(String.class));
                            requestsHelper.setPhone(ds.child("phone").getValue(String.class));
                            requestsHelper.setMetaData(ds.child("service provided").getValue(String.class));
                            requestsHelper.setRequestByFlag("provider");
                            requestsHelperList.add(requestsHelper);
                        } else {
                            if(reqCountFlag == 0) {
                                regRequestsList.setVisibility(View.GONE);
                                noRequestTv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                regRequestsList.setAdapter(new Adapter(RegistrationRequests.this,
                        R.layout.request_layout_inflater, requestsHelperList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class Adapter extends ArrayAdapter<RegistrationRequestsHelper> {

        Context context;
        private ArrayList<RegistrationRequestsHelper> items = null;

        Adapter(Context context, int resource, ArrayList<RegistrationRequestsHelper> objects) {
            super(context, resource, objects);
            this.context = context;
            this.items = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.request_layout_inflater, null);

            TextView nameTv = v.findViewById(R.id.titletextView12RLI);
            nameTv.setText(items.get(position).getName());

            TextView metaDataTv = v.findViewById(R.id.sercvicetextView13RLi);
            metaDataTv.setText(items.get(position).getMetaData());

            TextView phoneTv = v.findViewById(R.id.aptnotextView14rli);
            phoneTv.setText(items.get(position).getPhone());

            TextView extraTv = v.findViewById(R.id.datetextView16rli);
            extraTv.setVisibility(View.GONE);

            TextView emailTv = v.findViewById(R.id.statustextView15rli);
            emailTv.setText(items.get(position).getEmail());

            return v;
        }
    }

    private void initComponents() {
        regRequestsList = findViewById(R.id.regRequestsList);
        requestsHelperList= new ArrayList<>();
        noRequestTv = findViewById(R.id.norequestRR);
    }
}
