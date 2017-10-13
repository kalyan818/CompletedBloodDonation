package com.example.kalyansai.blooddonation1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FinalActivity extends AppCompatActivity {
    int IMAGES[] = {R.drawable.background1,R.drawable.background1};
    String Names[] = {"jasjjdas","jdfjkfd"};
    String Description[] = {"jdsjsfj","sdjjfjds"};
    ListView listview;
    private ArrayList<String> mUsernames = new ArrayList<>();
    private ArrayList<String> mUsernames1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        //loadData();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Doners/");
        listview = (ListView)findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUsernames);
        CustomAdapter customAdapter = new CustomAdapter();
        listview.setAdapter(customAdapter);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String child = dataSnapshot.getKey().toString();
                String values = dataSnapshot.getValue().toString();
                //Toast.makeText(AddDataToList.this,values,Toast.LENGTH_LONG).show();
                mUsernames.add(child);
                mUsernames1.add(values);
                anbc();
                arrayAdapter.notifyDataSetChanged();
            }

            private void anbc() {

                String array[] = new String[mUsernames1.size()];
                for(int j =0;j<mUsernames1.size();j++){
                    array[j] = mUsernames1.get(j);
                }
                Toast.makeText(getApplicationContext(),array[1],Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*private void loadData() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://blooddonation1-3dcaa.firebaseio.com/Doners/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String number = map.get("MobileNumber");
                String bloodGroup = map.get("BloodGroup");
                //String bloodGroup = dataSnapshot.child("BloodGroup").getValue(String.class);
                //String mobileNumber = dataSnapshot.child("MobileNumber").getValue(String.class);
                Toast.makeText(getApplicationContext(),number,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),bloodGroup,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
   /* private void collectPhoneNumbers(Map<String,Object> users) {

        ArrayList<Long> phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((Long) singleUser.get("phone"));
        }

        System.out.println(phoneNumbers.toString());
    }*/

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
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
            convertView = getLayoutInflater().inflate(R.layout.list,null);
            ImageView imageView  = (ImageView)convertView.findViewById(R.id.imageView2);
            TextView top = (TextView)convertView.findViewById(R.id.top);
            TextView bottom = (TextView)convertView.findViewById(R.id.bottom);
            imageView.setImageResource(IMAGES[position]);
            top.setText(mUsernames.get(position).toString());
            bottom.setText(mUsernames1.get(position).toString());
            return convertView;
        }
    }
}
