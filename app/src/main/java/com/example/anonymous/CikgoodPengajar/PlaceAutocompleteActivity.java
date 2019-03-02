package com.example.anonymous.CikgoodPengajar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class PlaceAutocompleteActivity extends AppCompatActivity {

    // Deklarasi variable
    private TextView tvPickUpFrom, tvDestLocation;
    private TextView tvPickUpAddr, tvPickUpLatLng, tvPickUpName;
    private TextView tvDestLocAddr, tvDestLocLatLng, tvDestLocName;
    private TextView tvDistance, tvPrice;
    private LinearLayout infoPanel;
    private String API_KEY = "AIzaSyCbX09ztk-EA8E3_HvCfTY8uRF5y0Bc3q8";


    public LatLng pickUpLatLng = null;
    public LatLng locationLatLng = null;

    public static final int PICK_UP = 0;
    public static final int DEST_LOC = 1;
    public static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_place_auto_complete);

        getSupportActionBar().setTitle("Place Auto Complete");
        //Inisialisasi widget
        wigetInit();

        // Event onClick
        tvPickUpFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Jalankan method untuk menampilkan Place auto complete
                // Bawa constrant DESC_LOC
                showPlaceAutoComplete(PICK_UP);
            }
        });

        // Event OnClick
        tvDestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menjalankan Method untuk menampilkan Place Auto Complete
                // Bawa condtraint DEST_LOC
                showPlaceAutoComplete(DEST_LOC);
            }
        });
    }
    //method ,manampilkan input Place Auto Complete
    private void showPlaceAutoComplete(int typeLocation) {
        //isi RESUL_CODE tergantung type lokasi yang di pilih
        // titip input atau tujuan
        REQUEST_CODE = typeLocation;

        //filter hanya tempat yang ada di Indonesia
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            // Intent untuk mengirim implisit Intent
            Intent mIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(this);

            // jalankan Intent Implisit
            startActivityForResult(mIntent, REQUEST_CODE);
        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace(); // mencetak error
        }catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace(); // cetak error
            // Dispay Toast
            Toast.makeText(this, "Layanan Services tidak Tersedia", LENGTH_SHORT).show();
        }
    }
    // method untukialisasi widget agar lebih rapih
    private void wigetInit() {
    tvPickUpFrom = findViewById(R.id.tvPickUpFrom);
    tvDestLocation = findViewById(R.id.tvDestLocation);
    tvDestLocAddr = findViewById(R.id.tvDestLocAddr);
    tvDestLocLatLng = findViewById(R.id.tvDestLocLatLng);
    tvDestLocName = findViewById(R.id.tvDestLocName);
    tvPickUpAddr = findViewById(R.id.tvPickUpAddr);
    tvPickUpName = findViewById(R.id.tvPickUpName);
    tvPickUpLatLng = findViewById(R.id.tvPickUpLatLng);
    infoPanel = findViewById(R.id.infoPanel);
    tvPrice = findViewById(R.id.tvPrice);
    tvDistance = findViewById(R.id.tvDistance);

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // pastikan result ok
        if (resultCode == RESULT_OK){
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // tampung data tempat variable
            Place placeData = PlaceAutocomplete.getPlace(this, data);
            if (placeData.isDataValid()){
                //show n Log Cat
                Log.d("autoCompletePlace Data", placeData.toString());

                // Dapatkan Detail
                String placeAddres = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
                String placeName = placeData.getName().toString();
                String typeName = placeData.getPlaceTypes().toString();


                // cek user memilih jrmput atau titik tujuan
                switch (REQUEST_CODE){
                    case PICK_UP:
                        // set ke widget lokasi asal
                        tvPickUpFrom.setText(placeAddres);
                        tvPickUpAddr.setText("location Address : "+placeAddres);
                        tvPickUpLatLng.setText("LatLang : "+placeLatLng.toString());
                        tvPickUpName.setText("Place Name :"+placeName);
                        break;
                    case DEST_LOC:
                        // set ke widget lokasi tujuan
                        tvDestLocation.setText(placeAddres);
                        tvDestLocAddr.setText("Destinasi Address :"+placeAddres);
                        tvDestLocLatLng.setText("latlAng :"+placeLatLng.toString());
                        tvDestLocName.setText("Place Name :"+placeName);
                        break;
                }
                // Jalankan Action Route
                if (tvPickUpFrom != null && tvDestLocation != null) {
                    // Dapatkan jarak dan waktu
//                    Toast.makeText(this, "Layanan tersedia", Toast.LENGTH_SHORT).show();
                    actionRoute(placeLatLng, REQUEST_CODE);
                }

            } else{
                // Data tempat tidak valid
                Toast.makeText(this, "Invalid Place!", LENGTH_SHORT).show();
            }
        }
    }

    private void actionRoute(LatLng placeLatLng, int requestCode) {
        String lokasiAwal = pickUpLatLng.latitude + "," + pickUpLatLng.longitude;
        String lokasiAkhir = locationLatLng.latitude + "," + locationLatLng.longitude;

        // Clear dulu Map nya
//        mMap.clear();
        // Panggil Retrofit
        ApiServices api = InitLibrary.getInstance();
        // Siapkan request
        Call<ResponseRoute> routeRequest = api.request_route(lokasiAwal, lokasiAkhir, API_KEY);
        // kirim request
        routeRequest.enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(Call<ResponseRoute> call, Response<ResponseRoute> response) {
                System.out.println(response);
                if (response.isSuccessful()){
                    // tampung response ke variable
                    ResponseRoute dataDirection = response.body();

                    LegsItem dataLegs = dataDirection.getRoutes().get(0).getLegs().get(0);

                    // Dapatkan garis polyline
                    String polylinePoint = dataDirection.getRoutes().get(0).getOverviewPolyline().getPoints();
                    // Decode
                    List<LatLng> decodePath = PolyUtil.decode(polylinePoint);
                    // Gambar garis ke maps
//                    mMap.addPolyline(new PolylineOptions().addAll(decodePath)
//                            .width(8f).color(Color.argb(255, 56, 167, 252)))
//                            .setGeodesic(true);

                    // Tambah Marker
//                    mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
//                    mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
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
//                    mMap.animateCamera(cu);

                    /** END
                     * Logic untuk membuat layar berada ditengah2 dua koordinat
                     */
                    // Tampilkan info panel
                    infoPanel.setVisibility(View.VISIBLE);

//                    mMap.setPadding(10, 180, 10, 180);

                }
            }

            @Override
            public void onFailure(Call<ResponseRoute> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}