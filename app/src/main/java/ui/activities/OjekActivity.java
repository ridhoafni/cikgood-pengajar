package ui.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.models.ListLocationModel;
import com.example.anonymous.CikgoodPengajar.models.LocationModel;
import com.example.anonymous.CikgoodPengajar.network.ApiClient;
import com.example.anonymous.CikgoodPengajar.network.ApiServices;
import com.example.anonymous.CikgoodPengajar.network.InitLibrary;
import com.example.anonymous.CikgoodPengajar.response.Distance;
import com.example.anonymous.CikgoodPengajar.response.Duration;
import com.example.anonymous.CikgoodPengajar.response.LegsItem;
import com.example.anonymous.CikgoodPengajar.response.ResponseRoute;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
//import com.khilman.www.CikgoodPengajar.network.ApiServices;
//import com.khilman.www.CikgoodPengajar.network.InitLibrary;
//import com.khilman.www.CikgoodPengajar.response.Distance;
//import com.khilman.www.CikgoodPengajar.response.Duration;
//import com.khilman.www.CikgoodPengajar.response.LegsItem;
//import com.khilman.www.CikgoodPengajar.response.ResponseRoute;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OjekActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<LocationModel> mListMarker = new ArrayList<>();

    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";

    public LatLng pickUpLatLng = null;
    public LatLng locationLatLng = null;

    private TextView tvStartAddress, tvEndAddress;
    private TextView tvPrice, tvDistance;
    private Button btnNext;
    private LinearLayout infoPanel;
    private TextView tvPickUpFrom, tvDestLocation;
//    private ActionBar toolbar;

    // Deklarasi variable
    public static final int PICK_UP = 0;
    public static final int DEST_LOC = 1;
    private static int REQUEST_CODE = 0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ojek);
//        getSupportActionBar().setTitle("Ojek Batam");
//        toolbar = (Toolbar) findViewById(R.id.idtoolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        // Inisialisasi Widget
        wigetInit();
        infoPanel.setVisibility(View.GONE);
        // Event OnClick
        tvPickUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jalankan Method untuk menampilkan Place Auto Complete
                // Bawa constant PICK_UP
                showPlaceAutoComplete(PICK_UP);
            }
        });
        // Event OnClick
        tvDestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jalankan Method untuk menampilkan Place Auto Complete
                // Bawa constant DEST_LOC
                showPlaceAutoComplete(DEST_LOC);
            }
        });


    }

    // Method untuk Inisilisasi Widget agar lebih rapih
    private void wigetInit() {
        // Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        infoPanel = findViewById(R.id.infoPanel);
        // Widget
        tvPickUpFrom = findViewById(R.id.tvPickUpFrom);
        tvDestLocation = findViewById(R.id.tvDestLocation);

        tvPrice = findViewById(R.id.tvPrice);
        tvDistance = findViewById(R.id.tvDistance);
        btnNext = findViewById(R.id.btnNext);
    }

    // Method menampilkan input Place Auto Complete
    private void showPlaceAutoComplete(int typeLocation) {
        // isi RESUT_CODE tergantung tipe lokasi yg dipilih.
        // titik jmput atau tujuan
        REQUEST_CODE = typeLocation;

        // Filter hanya tmpat yg ada di Indonesia
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            // Intent untuk mengirim Implisit Intent
            Intent mIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(this);
            // jalankan intent impilist
            startActivityForResult(mIntent, REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace(); // cetak error
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace(); // cetak error
            // Display Toast
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // Tampung Data tempat ke variable
            Place placeData = PlaceAutocomplete.getPlace(this, data);


            if (placeData.isDataValid()) {
                // Show in Log Cat
                Log.d("autoCompletePlace Data", placeData.toString());

                // Dapatkan Detail
                String placeAddress = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
                String placeName = placeData.getName().toString();
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                // Cek user milih titik jemput atau titik tujuan
                switch (REQUEST_CODE) {
                    case PICK_UP:
                        // Set ke widget lokasi asal
                        tvPickUpFrom.setText(placeAddress);
                        pickUpLatLng = placeData.getLatLng();
                        break;
                    case DEST_LOC:
                        // Set ke widget lokasi tujuan
                        tvDestLocation.setText(placeAddress);
                        locationLatLng = placeData.getLatLng();
                        break;
                }
                // Jalankan Action Route
                if (pickUpLatLng != null && locationLatLng != null) {
                    actionRoute(placeLatLng, REQUEST_CODE);
                }

            } else {
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(10, 180, 10, 10);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        getAllDataLocationLatLng();
    }

    private void getAllDataLocationLatLng() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Menampilkan data marker...");
//        dialog.setCancelable(false);
        dialog.show();

        ApiServices apiServices = ApiClient.getClient().create(ApiServices.class);
        Call<ListLocationModel> call = apiServices.getAllLocation();
        call.enqueue(new Callback<ListLocationModel>() {
            @Override
            public void onResponse(Call<ListLocationModel> call, Response<ListLocationModel> response) {
                dialog.dismiss();
                mListMarker = response.body().getmData();
                initMarker(mListMarker);
            }

            @Override
            public void onFailure(Call<ListLocationModel> call, Throwable t) {

            }
        });
    }

    private void initMarker(List<LocationModel> listData){
    // iterasi semua data dan tampilkan markernya
        for (int i = 0; i<mListMarker.size(); i++ ){
            // set latLng nya
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatutide()), Double.parseDouble(mListMarker.get(i).getLongitude()));
            // tambahkan marker
            mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getImageLocationName()));
            // set latlng index ke 0
            LatLng latlng = new LatLng(Double.parseDouble(mListMarker.get(0).getLatutide()), Double.parseDouble(mListMarker.get(0).getLongitude()));
            // lalu arahkan zooming ke marker index ke 0
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlng.latitude, latlng.longitude), 11.0f));
        }
    }


    private void actionRoute(LatLng placeLatLng, int requestCode) {
        String lokasiAwal = pickUpLatLng.latitude + "," + pickUpLatLng.longitude;
        String lokasiAkhir = locationLatLng.latitude + "," + locationLatLng.longitude;

        // Clear dulu Map nya
        mMap.clear();
        // Panggil Retrofit
        ApiServices api = InitLibrary.getInstance();
        // Siapkan request
        Call<ResponseRoute> routeRequest = api.request_route(lokasiAwal, lokasiAkhir, API_KEY);
        // kirim request
        routeRequest.enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(Call<ResponseRoute> call, Response<ResponseRoute> response) {

                if (response.isSuccessful()){
                    // tampung response ke variable
                    ResponseRoute dataDirection = response.body();

                    LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);

                    // Dapatkan garis polyline
                    String polylinePoint = dataDirection.getRoutes().get(0).getOverviewPolyline().getPoints();
                    // Decode
                    List<LatLng> decodePath = PolyUtil.decode(polylinePoint);
                    // Gambar garis ke maps
                    mMap.addPolyline(new PolylineOptions().addAll(decodePath)
                            .width(8f).color(Color.argb(255, 56, 167, 252)))
                            .setGeodesic(true);

                    // Tambah Marker
                    mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
                    mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
                    // Dapatkan jarak dan waktu
                    Distance dataDistance = dataLegs.getDistance();
                    Duration dataDuration = dataLegs.getDuration();

                    // Set Nilai Ke Widget
                    double price_per_meter = 250;
                    double priceTotal = dataDistance.getValue() * price_per_meter;
                    // Jarak * harga permeter

                    tvDistance.setText(dataDistance.getText());
                    tvPrice.setText(String.valueOf(priceTotal));
                    /** START
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */

                    LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
                    latLongBuilder.include(pickUpLatLng);
                    latLongBuilder.include(locationLatLng);

                    // Bounds Coordinata
                    LatLngBounds bounds = latLongBuilder.build();

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int paddingMap = (int) (width * 0.2); //jarak dari
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
                    mMap.animateCamera(cu);

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */
                    // Tampilkan info panel
                    infoPanel.setVisibility(View.VISIBLE);

                    mMap.setPadding(10, 180, 10, 180);

                }
            }

            @Override
            public void onFailure(Call<ResponseRoute> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
