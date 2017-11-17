package com.baswarajmamidgi.neardevs;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserDetails extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText mobile;
    private TextView address;
    private Place place;
    private ImageButton get_address;
    private Button submit;
    int PLACE_PICKER_REQUEST = 1;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String> domains=new ArrayList<>();

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        address=findViewById(R.id.address);
        mobile=findViewById(R.id.mobile_data);
        get_address=findViewById(R.id.my_location);
        get_address.setOnClickListener(this);
        submit=findViewById(R.id.submit_data);
        submit.setOnClickListener(this);

        preferences=getSharedPreferences("profile",0);
        editor=preferences.edit();
        database=FirebaseDatabase.getInstance();
        getuserdata();



    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.my_location)
        {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(UserDetails.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
        if(view.getId()==R.id.submit_data)
        {
            submitDetails();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.android:
                if (checked)
                    domains.add("android");
            else
                    domains.remove("android");
                break;
            case R.id.ios:
                if (checked)
                    domains.add("IOS");
                else
                    domains.remove("IOS");
                break;
            case R.id.web:
                if (checked)
                    domains.add("Web Development");
                else
                    domains.remove("Web Development");
                break;
            case R.id.machinelearning:
                if (checked)
                    domains.add("Machine Learning");
                else
                    domains.remove("Machine Learning");
                break;
            case R.id.iot:
                if (checked)
                    domains.add("IOT");
                else
                    domains.remove("IOT");
                break;
            case R.id.bigdata:
                if (checked)
                    domains.add("Big Data");
                else
                    domains.remove("Big Data");
                break;

            case R.id.python:
                if (checked)
                    domains.add("Python");
                else
                    domains.remove("Python");
                break;
            case R.id.java:
                if (checked)
                    domains.add("Java");
                else
                    domains.remove("Java");
                break;
            case R.id.javascript:
                if (checked)
                    domains.add("Java Script");
                else
                    domains.remove("Java Script");
                break;
        }
    }






    void submitDetails(){

        String occupation = null;
        RadioButton radioButton = findViewById(R.id.student);

        if(radioButton.isChecked()){
            occupation="Student";
        }

        RadioButton radioButton2 = findViewById(R.id.employee);

        if(radioButton2.isChecked()){
            occupation="Employee";
        }






        //Editable mobile_data=mobile.getText();

        LatLng address=  place.getLatLng();
        String location=address.latitude+","+address.longitude;
        if(TextUtils.isEmpty(location)){
            Toast.makeText(this, "Select address", Toast.LENGTH_SHORT).show();
            return;
        }

        User user=new User(preferences.getString("name",null),preferences.getString("email",null),"9989478011",occupation,domains.toString(),location);

        DatabaseReference reference=database.getReference("users").child(preferences.getString("id",null));
        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserDetails.this, "Profile Updated Seccessfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        editor.putString("occupation",occupation);
        editor.putString("domain",domains.toString());
        editor.putString("address",place.getAddress().toString());

        editor.commit();
        finish();





    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("log",String.valueOf(resultCode));
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                address.setText(toastMsg);
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getuserdata() {



        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.v("log", response.toString());

                        try {
                            //and fill them here like so.
                            String id = object.getString("id");
                            String email = object.getString("email");
                            String name = object.getString("name");
                            String imageurl="https://graph.facebook.com/" + id+ "/picture?type=large";
                            editor.putString("id",id);

                            editor.putString("email",email);
                            editor.putString("name",name);
                            editor.putString("imageurl",imageurl);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }



}
