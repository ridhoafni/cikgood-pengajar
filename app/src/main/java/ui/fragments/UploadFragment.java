package ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {


    public UploadFragment() {
        // Required empty public constructor
    }

    Button GetImageFromGalleryButton, GetImageFromGalleryButton2, UploadImageOnServerButton;

    ImageView ShowSelectedImage, ShowSelectedImage2;

    EditText GetImageName;

    Bitmap FixBitmap, FixBitmap2;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    String ImageName2 = "image_data2" ;

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_SERVER_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;
    String ConvertImage2 ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        GetImageFromGalleryButton = (Button)view.findViewById(R.id.button);

        GetImageFromGalleryButton2 = (Button)view.findViewById(R.id.buttonImage2);

        UploadImageOnServerButton = (Button)view.findViewById(R.id.button2);

        ShowSelectedImage = (ImageView)view.findViewById(R.id.imageView);

        ShowSelectedImage2 = (ImageView)view.findViewById(R.id.imageView2);

        GetImageName = (EditText)view.findViewById(R.id.editText);

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        GetImageFromGalleryButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent();

                intent2.setType("image/*");

                intent2.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent2, "Select Image From Gallery2"), 2);

            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameFromEditText = GetImageName.getText().toString();

                UploadImageToServer();

            }
        });

        return  view;
    }

    public void onActivityResult(int RC, int RQC, Intent I){
        switch(RC){
            case 1: // Do your stuff here...
                        Uri uri = I.getData();
                    try {

                        FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                        ShowSelectedImage.setImageBitmap(FixBitmap);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                break;
            case 2: // Do your other stuff here...
                Uri uri2 = I.getData();
                try {

                    FixBitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri2);

                    ShowSelectedImage2.setImageBitmap(FixBitmap2);

                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;
        }
    }

//    @Override
//    public void onActivityResult(int RC, int RQC, Intent I) {
//
//        super.onActivityResult(RC, RQC, I);
//
//        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
//
//            Uri uri = I.getData();
//
//            try {
//
//                FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
//
////                ShowSelectedImage.setImageBitmap(FixBitmap);
//                ShowSelectedImage2.setImageBitmap(FixBitmap);
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        ConvertImage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageTag, GetImageNameFromEditText);

                HashMapParams.put(ImageName, ConvertImage);

                HashMapParams.put(ImageName2, ConvertImage2);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }


}
