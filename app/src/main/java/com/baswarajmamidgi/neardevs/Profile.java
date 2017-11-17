package com.baswarajmamidgi.neardevs;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }


    public Profile() {
        // Required empty public constructor
    }

    SharedPreferences preferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        preferences=getActivity().getSharedPreferences("profile",0);
        TextView name=view.findViewById(R.id.user_name);
        TextView email=view.findViewById(R.id.user_email);
        TextView occupation=view.findViewById(R.id.user_occupation);
        TextView domain=view.findViewById(R.id.user_domain);
        TextView address=view.findViewById(R.id.user_address);
        ImageButton profile_image=view.findViewById(R.id.profile_photo);

        name.setText("Name: "+preferences.getString("name",null));
        email.setText("Email: "+preferences.getString("email",null));
        occupation.setText("Occupation: "+preferences.getString("occupation",null));
        domain.setText("Domain: "+preferences.getString("domain",null));
        address.setText("Address: "+preferences.getString("address",null));

        Glide.with(getContext())
                .load(preferences.getString("imageurl",null))
                .into(profile_image);

        FloatingActionButton editprofile=view.findViewById(R.id.edit_profile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),UserDetails.class));

            }
        });



        return view;
    }

}
