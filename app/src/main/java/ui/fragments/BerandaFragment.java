package ui.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anonymous.CikgoodPengajar.PlaceAutocompleteActivity;
import com.example.anonymous.CikgoodPengajar.R;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import ui.activities.OjekActivity;
import ui.activities.ViewActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment{

    private CardView CvCars, CvBikes, CvBluebird, CvOthers;
    SliderLayout sliderLayout;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_beranda, container, false);

        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds :

        setSliderViews();

        CvCars = (CardView)view.findViewById(R.id.cv_cars);
        CvCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OjekActivity.class);
                startActivity(intent);
            }
        });

        CvBikes = (CardView)view.findViewById(R.id.cv_bikes);
        CvBikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlaceAutocompleteActivity.class);
                startActivity(intent);
            }
        });

        CvBluebird = (CardView)view.findViewById(R.id.cv_bluebird);
        CvBluebird.setOnClickListener(new View.OnClickListener() {
            Uri gmmIntentUri;

            @Override
                  public void onClick(View v) {
                      // Create a Uri from an intent string. Use the result to create an Intent.
                      String gmmIntentUri = "http://maps.google.com/maps?saddr=0.4662126,101.35527169999999&daddr=0.4644965,101.3844829";
//                      gmmIntentUri = Uri.parse("geo:0.5070677,101.4477793");

                      // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                      Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri));
                      // Make the Intent explicit by setting the Google Maps package
                      mapIntent.setPackage("com.google.android.apps.maps");

                      // Attempt to start an activity that can handle the Intent
                      startActivity(mapIntent);
                  }
              }

        );

        CvOthers = (CardView)view.findViewById(R.id.cv_others);
        CvOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//            sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
//                    Toast.makeText(BerandaFragment.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }



//    private void loadFragment(Fragment fragment){
//        // load fragment
//        FragmentManager mFragmentManager = getFragmentManager();
//        FragmentTransaction transaction =  mFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//    }

//    @Override
//    public void onClick(View v) {
//        Fragment fragment;
//        switch (v.getId()){
//            case R.id.cv_cars:
//                Intent IntentOjek = new Intent(MainActivity.this, OjekActivity.class);
//                startActivity(IntentOjek);
//                break;
//        }
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
//    }
//    @Override
//    public void onStop() {
//        super.onStop();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
//    }
}
