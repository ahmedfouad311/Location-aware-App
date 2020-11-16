package com.example.locationdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnFindLocation;
    LocationManager locationManager;
    MyLocationListener myLocationListener;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    public static final int _requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener(); // doing object from the MyLocationListener class
        btnFindLocation = findViewById(R.id.find_Location);
        btnFindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // el if condition de bt3ml check 3ala el permission hoa valid walla la
                // byt2aked 2n el user 3amal allow walla la le el ACCESS_FINE_LOCATION w el ACCESS_COARSE_LOCATION
                // law m4 3aml allow fa lazem n3ml el steps el fe el if condition , else ya3ny law 3amal allow
                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // *** here to request the missing permissions, and then overriding

                    // de bta5od el context bta3ti w array of string feha el permissions 3l4an keda 3amalna create el el array fo2 w m7tag bardo el request code
                    ActivityCompat.requestPermissions(MainActivity.this, permissions,_requestCode);
                    // el satr el fo2 dah byantog 3ano 2no bytla3 le el user pop up btotlob mno 2no y3ml allow le el permissions 2w y3ml deny

                    //  *** public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults)
                    //  *** to handle the case where the user grants the permission. See the documentation
                    //  *** for Activity#requestPermissions for more details.
                }
                else{
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, myLocationListener);
                }
            }
        });
    }

    class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(MainActivity.this, "Longitude" +location.getLongitude()+"\n" + "Latitude"
                    + location.getLatitude(), Toast.LENGTH_LONG).show();
            locationManager.removeUpdates(myLocationListener); // dah 3l4an el toast bta3i mayzhar4 2ktar mn mara
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    // w de 3l4an 23raf hoa el user 3amal allow walla deny le el permissions w 27na 5adna el satr dah copy paste mn el comment el hoa feh el
    // suggestions fo2 el hoa 3amalo create automatic lma 3amal check permissions
    // fa 2wel 7aga ha3ml switch w case 3l4an y3ml check 3ala el requestCode  mn hna ha2dar 2sta5dem el if condition el ma3mola fo2 tany el hya hal
    // el user 3amal allow walla la2
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case _requestCode:
                if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // fe 7alt hna 2no ma3aml4 allow w el if hya el 2t7a2a2t ha3ed mn tany el talab le el permissions mn tany
                    ActivityCompat.requestPermissions(MainActivity.this, permissions,_requestCode);
                }
                // el else law tel3 2n el user 3amal allow le el permissions bta3ti
                else {
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, myLocationListener);
                }
                break;
        }

    }
}
