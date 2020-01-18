package com.demo.location_2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final String TAG = "Location_2";

    private LocationManager locationManager;

    private TextView tv;

    private Geocoder gc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);    //地區:台灣

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (!hasPermissions(this, PERMISSIONS)) {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
        tv = findViewById(R.id.textView1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume(), locationManager = " + locationManager);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e(TAG, "requestCode: " + requestCode);
        for (int i = 0; i < permissions.length; i++) {
            Log.e(TAG, i + ", permissions: " + permissions[i]);
        }

        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        String address = getAddressByLocation(location);
        Log.i(TAG, address);
        tv.setText("以Geocoder將經緯度 (" + location.getLongitude() + ", "+
                location.getLatitude() + ") 找到的地址為\n\n" + address);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    public String getAddressByLocation(Location location) {
        String sAddress = "";
        try {
            if (location != null) {
                Double geoLongitude = location.getLongitude();	//取得經度
                Double geoLatitude = location.getLatitude();	//取得緯度

                //自經緯度取得地址
                List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);
                sAddress = lstAddress.get(0).getAddressLine(0);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return sAddress;
    }

    public String getLocationByAddress(String addr) {
        List<Address> addresses = null;
        Address address = null;
        double geoLatitude, geoLongitude;
        try {
            addresses = gc.getFromLocationName(addr, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses == null || addresses.isEmpty()){
            return "找不到該地址";
        }else{
            address = addresses.get(0);
            geoLatitude = address.getLatitude() * 1E6;
            geoLongitude = address.getLongitude() * 1E6;
        }
        return geoLatitude + ", " + geoLongitude;
    }
}
