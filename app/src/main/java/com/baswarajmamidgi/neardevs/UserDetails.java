package com.baswarajmamidgi.neardevs;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class UserDetails extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText email;
    private TextInputEditText mobile;
    private TextView address;
    private Place place;
    private ImageButton get_address;
    private Button submit;
    int PLACE_PICKER_REQUEST = 1;

    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        address=findViewById(R.id.address);
        email=findViewById(R.id.email);
        mobile=findViewById(R.id.mobile_data);
        get_address=findViewById(R.id.my_location);
        get_address.setOnClickListener(this);
        submit=findViewById(R.id.submit_data);
        submit.setOnClickListener(this);
        database=FirebaseDatabase.getInstance();



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






    void submitDetails(){
    try {
        RadioGroup occupation = findViewById(R.id.occupation);
        int selectedid = occupation.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedid);
        String occ = (String) radioButton.getText();

        RadioGroup domain = findViewById(R.id.domain);
        selectedid = domain.getCheckedRadioButtonId();
        radioButton = findViewById(selectedid);
        String dom = (String) radioButton.getText();
    }catch (Exception e){
        Log.i("log",e.getLocalizedMessage());
    }


        Editable email_data=email.getText();

        Editable mobile_data=mobile.getText();

        String address= (String) place.getAddress();

        DatabaseReference reference=database.getReference("user1");
        reference.setValue(address);




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



}
