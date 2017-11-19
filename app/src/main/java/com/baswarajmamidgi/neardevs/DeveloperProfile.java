package com.baswarajmamidgi.neardevs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by baswarajmamidgi on 18/11/17.
 */

public class DeveloperProfile extends BottomSheetDialogFragment {

    private Bundle args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         args = getArguments();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.developer_dialogfragment, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        final ImageButton profile_photo=dialog.findViewById(R.id.developer_photo);
        final TextView name=dialog.findViewById(R.id.developer_name);
        final TextView email=dialog.findViewById(R.id.developer_email);
        final TextView mobile=dialog.findViewById(R.id.developer_mobile);
        final TextView domain=dialog.findViewById(R.id.developer_domain);
        final TextView occupation=dialog.findViewById(R.id.developer_occupation);
        final TextView address=dialog.findViewById(R.id.developer_address);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(args.getString("id"));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map) dataSnapshot.getValue();


                name.setText(map.get("userName"));
                try {
                    email.setText("Email :" + map.get("email"));
                    mobile.setText("Mobile :" + map.get("mobile"));
                }catch (Exception e){
                    Log.i("DeveloperProfile",e.getLocalizedMessage());
                }
                domain.setText("Domain :"+map.get("domain"));
                occupation.setText("Occupation :"+map.get("occupation"));
                //address.setText("Address :"+map.get("address"));

                String imageurl="https://graph.facebook.com/" + args.get("id")+ "/picture?type=large";


                Glide.with(getContext())
                        .load(imageurl)
                        .into(profile_photo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
