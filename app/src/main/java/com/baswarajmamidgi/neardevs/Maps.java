package com.baswarajmamidgi.neardevs;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Maps extends Fragment {
    public static Maps newInstance() {
        Maps fragment = new Maps();
        return fragment;
    }


    public Maps() {
        // Required empty public constructor
    }

    private GoogleMap mMap;
    private MapView mapView;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                mMap = map;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map

                // For zooming automatically to the location of the marker
                //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng()).zoom(12).build();
                //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        Button find = (Button) view.findViewById(R.id.findpeople);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mMap.addMarker(new MarkerOptions().position(new LatLng(17.450420, 78.381119)).title("Person 1").snippet("Raghu\n 9989478011"));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(17.452641, 78.380776)).title("Person 2").snippet("Bharat\n 9866391717"));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(17.451884, 78.378373)).title("Person 3").snippet("Priya\n 9441843735"));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(17.449053, 78.378347)).title("Person 4").snippet("Karthi\n 8328363868"));
        loadUserData();

            }
        });


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(12).build();
                            //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                    });
        } catch (Exception e) {
            Log.i("log", e.getLocalizedMessage());
        }


        return view;
    }

    void loadUserData() {


        final ArrayList<User> users = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Map<String, String> map = (Map) dataSnapshot.getValue();
                    User newuser = new User(map.get("Name"), map.get("address"));
                    users.add(newuser);
                    String[] coords=newuser.getAddress().split(",");
                    LatLng latLng=new LatLng(Double.parseDouble(coords[0]),Double.parseDouble(coords[1]));
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Person 4").snippet("Karthi\n 8328363868"));



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

        for (User user : users) ;
        {
            if (mMap != null) {

               // mMap.addMarker(new MarkerOptions().position(users.get(1)).title("Person 4").snippet("Karthi\n 8328363868"));


            }

        }
    }
}
