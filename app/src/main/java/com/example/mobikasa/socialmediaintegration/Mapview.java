/*
package com.example.mobikasa.socialmediaintegration;

import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Tow.application.MainActivity;
import com.Tow.application.R;
import com.Tow.application.activity.LoginActivity;
import com.Tow.application.model.DashBoardModel;
import com.Tow.application.utils.Commonparams;
import com.Tow.application.utils.GpsTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.Tow.application.basepackage.base.BaseFragment;
import com.Tow.application.basepackage.listner.FragmentResumeListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

*/
/**
 * Created by p on 09-03-2019.
 *//*


public class TireServices extends Fragment implements
         OnMapReadyCallback,
         GoogleMap.OnInfoWindowClickListener {
    private int RQS_GooglePlayServices=1;
    //private DashBoardModel loginModel;
    private LatLng val;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frafment_tireservices, container, false);
       // bindView(rootView);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(TireServices.this);
         onFragmentResume();
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnClickListener(null);
    }

//    @Override
//    public void onFragmentResume() {
////        ((MainActivity) getActivity()).setAppHeaderTitle(  getArguments().
////                getString("Value"));
////        loginModel = new Gson().fromJson(getArguments().getString("response"), DashBoardModel.class);
//
//    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng cl = new LatLng(new GpsTracker(getActivity()).getLatitude(),
                new GpsTracker(getActivity()).getLongitude());
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions().position(sydney)
//                .title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        int i=0;


        }
       // LatLng delhi2 = new LatLng(43.724330436, -79.60549);


        MarkerOptions markerOptions = new MarkerOptions().position(cl).title("You are Here");
      //  googleMap.addMarker(markerOptions);


//        googleMap.addMarker(new MarkerOptions().position(cl)
//                .title("Canada").icon(BitmapDescriptorFactory.fromResource(R.mipmap.miniall)));
       */
/* googleMap.addMarker(new MarkerOptions().position(delhi2)
                .title("Canada").icon(BitmapDescriptorFactory.fromResource(
                        getArguments().getString("Value").equals(Commonparams.Tyre)?
                                R.mipmap.miniwheelicon
                                : getArguments().getString("Value").equals(Commonparams.Truck)
                                ?R.mipmap.minitruckicon
                                :getArguments().getString("Value").equals(Commonparams.Mobile)?
                                R.mipmap.minimobile:R.mipmap.miniall)));
        googleMap.addMarker(new MarkerOptions().position(delhi3)
                .title("Canada").icon(BitmapDescriptorFactory.fromResource(
                        getArguments().getString("Value").equals(Commonparams.Tyre)?
                                R.mipmap.miniwheelicon
                                : getArguments().getString("Value").equals(Commonparams.Truck)
                                ?R.mipmap.minitruckicon
                                :getArguments().getString("Value").equals(Commonparams.Mobile)?
                                R.mipmap.minimobile:R.mipmap.miniall)));*//*

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cl,5));
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
//        myMap.getUiSettings().setZoomControlsEnabled(true);
//        myMap.getUiSettings().setCompassEnabled(true);
//        myMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        myMap.getUiSettings().setRotateGesturesEnabled(true);
//        myMap.getUiSettings().setScrollGesturesEnabled(true);
//        myMap.getUiSettings().setTiltGesturesEnabled(true);
//        myMap.getUiSettings().setZoomGesturesEnabled(true);
//        //or myMap.getUiSettings().setAllGesturesEnabled(true);
//
//        myMap.setTrafficEnabled(true);

        //myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

       */
/* googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(point);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
               // markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
                Toast.makeText(getContext(),
                        point.latitude + ", " + point.longitude,
                        Toast.LENGTH_SHORT).show();
                getAddress(point);
            }
        });*//*

    }

    private void getAddress(LatLng point) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            showShortToast(address+" "+city+"  "+state+"  "+country);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode == ConnectionResult.SUCCESS){

        }else{
            GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), RQS_GooglePlayServices);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(), "Info window clicked",
                Toast.LENGTH_SHORT).show();
    }
    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.custommarkerwindow, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
            tvTitle.setText(marker.getTitle());
            ImageView tvSnippet = ((ImageView)myContentsView.findViewById(R.id.snippet));
            //tvSnippet.setText(marker.getSnippet());

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }
}*/
