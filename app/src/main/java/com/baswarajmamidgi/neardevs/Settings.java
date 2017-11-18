package com.baswarajmamidgi.neardevs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }


    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings, container, false);

        ProfilePictureView profilePicture = (ProfilePictureView) view.findViewById(R.id.profile_photo);
        profilePicture.setProfileId(getActivity().getSharedPreferences("profile",0).getString("id",null));



        Button logout = (Button)view.findViewById(R.id.login_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(getContext(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });

        return view;
    }

}
