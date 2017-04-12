package tea_manager.com.example.honza.tea_manager.Fragments;


import android.Manifest;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import tea_manager.com.example.honza.tea_manager.Activities.ShopDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBshopCRUD;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailFragment extends Fragment{
    private final int moveCamera = 0;
    private final int addMarker = 1;

    private EditText nameEdit;
    private TextView openFromText;
    private TextView openToText;
    private LinearLayout openFromColumn;
    private LinearLayout openToColumn;
    private Button submitButton;
    private Button myLocationButton;
    private Button clearLocationButton;
    private ProgressBar progressBar;

    private GoogleMap mMap;
    private int mMode;
    private FragmentListener mFragmentListener;
    private Shop mShop;
    private SupportMapFragment mSupportMapFragment;
    private int onLocationUpdated;


    public ShopDetailFragment() {
        // Required empty public constructor
    }

    public static ShopDetailFragment newInstance(String keyMode, int mode) {

        Bundle args = new Bundle();
        args.putInt(keyMode, mode);

        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShopDetailFragment newInstance(String keyMode, int mode, String keyShop, Shop shop) {

        Bundle args = new Bundle();
        args.putInt(keyMode, mode);
        args.putSerializable(keyShop, shop);

        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof FragmentListener)) throw new AssertionError();
        mFragmentListener = (FragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_detail, container, false);

        //get all widgets
        submitButton = (Button) view.findViewById(R.id.submitButton);
        nameEdit = (EditText) view.findViewById(R.id.shopNameEdit);
        openFromText = (TextView) view.findViewById(R.id.shopOpenFromText);
        openToText = (TextView) view.findViewById(R.id.shopOpenToText);
        openFromColumn = (LinearLayout) view.findViewById(R.id.shopOpenFromColumn);
        openToColumn = (LinearLayout) view.findViewById(R.id.shopOpenToColumn);
        myLocationButton = (Button) view.findViewById(R.id.currentLocationButton);
        clearLocationButton = (Button) view.findViewById(R.id.clearMarkerButton);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //get the mode and set text and other stuff accordingly
        Bundle args = getArguments();
        mMode = args.getInt(ShopDetailActivity.MODE);
        if(mMode == ShopDetailActivity.EDIT_MODE){
            submitButton.setText(R.string.saveChanges);
            mShop = (Shop) args.getSerializable(ShopDetailActivity.SHOP_TO_VIEW);
            fillShopInfo(mShop);
        }
        else {
            submitButton.setText(R.string.submit);
            mShop = new Shop();
        }

        //set opening time listener
        openFromColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the name, otherwise the changes wil be lost
                mShop.setName(nameEdit.getText().toString());
                TimePickerDialog  timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mShop.getOpeningHours().setFromHour(hourOfDay);
                                mShop.getOpeningHours().setFromMinute(minute);
                                fillShopInfo(mShop);
                            }
                },
                        mShop.getOpeningHours().getFromHour(),
                        mShop.getOpeningHours().getFromMinute(),
                        true);
                timePickerDialog.show();
            }
        });
        //set closing time listener
        openToColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the name, otherwise the changes wil be lost
                mShop.setName(nameEdit.getText().toString());
                TimePickerDialog  timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mShop.getOpeningHours().setToHour(hourOfDay);
                                mShop.getOpeningHours().setToMinute(minute);
                                fillShopInfo(mShop);
                            }
                        },
                        mShop.getOpeningHours().getToHour(),
                        mShop.getOpeningHours().getToMinute(),
                        true);
                timePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShop.setName(nameEdit.getText().toString());
                mShop.setOpeningHours(new Shop.OpeningHours(
                        mShop.getOpeningHours().getFromHour(),
                        mShop.getOpeningHours().getFromMinute(),
                        mShop.getOpeningHours().getToHour(),
                        mShop.getOpeningHours().getToMinute()));
                DBshopCRUD dBshopCRUD = new DBshopCRUD(getContext());

                if(mMode == ShopDetailActivity.ADD_MODE) {
                    dBshopCRUD.addShop(mShop);
                }else {
                    dBshopCRUD.updateShop(mShop);
                }

                if(mFragmentListener == null)
                    throw new AssertionError();
                mFragmentListener.onFragmentFinish();
            }
        });
        submitButton.requestFocus();

        //get fragment manager for a fragment and start google maps fragment
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.shopMap);
        //if you fail to find it then just create a new one
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.shopMap, mSupportMapFragment)
                    .commit();
        }
        //check again to make sure we have the manager and start the funny stuff
        if (mSupportMapFragment != null) {
            //get the map and when it is ready start the callback
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @SuppressWarnings("MissingPermission")//I took care of this in permissionGranted() method
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        //save the map
                        mMap = googleMap;

                        //add gesture, zoom etc.
                        mMap.getUiSettings().setRotateGesturesEnabled(true);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(true);

                        //try to add current location
                        if (permissionGranted()) {
                            mMap.setMyLocationEnabled(true);
                        }
                        //load the shop location if there is any
                        // (feel free to brake the app with a shop that has coordinates 0,0)
                        if(mShop.getLatitude() != 0 && mShop.getLongitude() != 0) {
                            LatLng shopLocation = new LatLng(mShop.getLatitude(), mShop.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(shopLocation)
                                    .title(mShop.getName()))
                                    .setDraggable(true);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 13));
                        }
                        //if the shop does not have a location, go to user's current location if possible
                        else if(permissionGranted()){
                            onLocationUpdated = moveCamera;
                            getCurrentLocation();
                            }
                        //long clicking will add a marker and save the location
                        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                            @Override
                            public void onMapLongClick(LatLng latLng) {
                                mMap.clear();
                                mShop.setLatitude(latLng.latitude);
                                mShop.setLongitude(latLng.longitude);
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title(mShop.getName())
                                        .draggable(true));
                            }
                        });
                        //you can drag the marker around
                        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                            @Override
                            public void onMarkerDragStart(Marker marker) {
                            }

                            @Override
                            public void onMarkerDrag(Marker marker) {
                            }

                            @Override
                            public void onMarkerDragEnd(Marker marker) {
                                mMap.clear();
                                mShop.setLatitude(marker.getPosition().latitude);
                                mShop.setLongitude(marker.getPosition().longitude);
                                mMap.addMarker(new MarkerOptions()
                                        .position(marker.getPosition())
                                        .title(mShop.getName())
                                        .draggable(true));
                            }
                        });
                    }
                }
            });
        }

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permissionGranted()) {
                    onLocationUpdated = addMarker;
                    getCurrentLocation();
                }
                else
                    Snackbar.make(v, "You have denied location tracking", 3000).show();
            }
        });

        clearLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                mShop.setLongitude(0);
                mShop.setLatitude(0);
            }
        });

        return view;
    }

    /**
     * Fills in the views containing name and opening hours of the shop.
     * @param shop attributes of this object will be used to fill the views
     */
    private void fillShopInfo(Shop shop){
        nameEdit.setText(shop.getName());
        openFromText.setText(shop.getOpeningHours().openFromToString());
        openToText.setText(shop.getOpeningHours().openToToString());
    }

    public interface FragmentListener{
        void onFragmentFinish();
    }

    /**
     * Asks the user for permission or returns true if permission has already been granted.
     * @return true if the app already has the permission, false otherwise
     */
    public Boolean permissionGranted(){
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //noinspection MissingPermission
            mMap.setMyLocationEnabled(true);
            onLocationUpdated = moveCamera;
            getCurrentLocation();
        }
    }

    /**
     * @return Last known location, in case of missing permission returns null.
     */
    private Location getCurrentLocation(){
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return null;
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        progressBar.setVisibility(View.VISIBLE);
        locationManager.requestSingleUpdate(criteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(onLocationUpdated == addMarker) {
                    mShop.setLatitude(location.getLatitude());
                    mShop.setLongitude(location.getLongitude());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .title(mShop.getName())
                            .draggable(true)
                            .position(latLng));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }, null);

        Snackbar.make(getView(), "Updating location...", Snackbar.LENGTH_SHORT).show();
        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        return myLocation;
    }
}
