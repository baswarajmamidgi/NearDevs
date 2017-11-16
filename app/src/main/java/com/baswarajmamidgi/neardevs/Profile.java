package com.baswarajmamidgi.neardevs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        startActivity(new Intent(getContext(),UserDetails.class));


        return view;
    }

}
