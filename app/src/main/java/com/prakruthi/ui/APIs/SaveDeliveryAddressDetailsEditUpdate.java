package com.prakruthi.ui.APIs;

//import static androidx.fragment.app.FragmentManager.TAG;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SaveDeliveryAddressDetailsEditUpdate {

    //    private final OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener;
    private final OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener;

//    public String id, name, city, state, country, address;

//    int id;

    //    public String id;
    public String name;
    public String city;
    public String state;
//    Integer id;
    String id;
    public String country;
    public String address;
    public String postal_code;
//    public int is_default;
    public String is_default;

//    int id;

    public SaveDeliveryAddressDetailsEditUpdate(OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String name, String city, String state, String id, String country, String address, String postal_code, int is_default) {

//    public SaveDeliveryAddressDetailsEditUpdate(OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String name, String city, String state, String country, String address, String postal_code, int is_default,int id) {

//public SaveDeliveryAddressDetailsEditUpdate(OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, int id, String name, String city, String state, String country, String address, String postal_code, int is_default) {

//    public SaveDeliveryAddressDetailsEditUpdate(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String id, String name, String city, String state, String country, String address, String postal_code, int is_default) {
//    public SaveDeliveryAddressDetailsEditUpdate(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String id, String name, String city, String state, String country, String address, String postal_code, String is_default) {
//    public SaveDeliveryAddressDetailsEditUpdate(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String id, String name, String address, String city, String state) {

        this.mListener = mListener;
//        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.id = id;
        this.country = country;
        this.address = address;
        this.postal_code = postal_code;
        this.is_default = String.valueOf(is_default);

//        this.id = id;

//        this.mListener = mListener;
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.city = city;
//        this.state = state;
    }

    public void HitSaveDeliveryAddressDetailsEditUpdateApiApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitSaveDeliveryAddressDetailsEditUpdateApi());
    }

    private class HitSaveDeliveryAddressDetailsEditUpdateApi implements Runnable {

        @Override
        public void run() {
            // Creating array for parameters
//            String[] field = new String[10];
//            field[0] = "user_id";
//            field[1] = "token";
//            field[2] = "id";
//            field[3] = "name";
//            field[4] = "city";
//            field[5] = "state";
//            field[6] = "country";
//            field[7] = "address";
//            field[8] = "postal_code";
//            field[9] = "is_default";

            String[] field = new String[10];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "name";
            field[3] = "address";
            field[4] = "city";
            field[5] = "state";
            field[6] = "id";
            field[7] = "country";
            field[8] = "postal_code";
            field[9] = "is_default";


//            String[] field = new String[7];
//            field[0] = "user_id";
//            field[1] = "token";
//            field[2] = "id";
//            field[3] = "name";
//            field[4] = "address";
//            field[5] = "city";
//            field[6] = "state";


            // Creating array for data
            String[] data = new String[10];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = name;
            data[3] = address;
            data[4] = city;
            data[5] = state;
            data[6] = String.valueOf(id);
            data[7] = country;
            data[8] = postal_code;
            data[9] = String.valueOf(is_default);

//            String[] data = new String[7];
//            data[0] = String.valueOf(Variables.id);
//            data[1] = Variables.token;
//            data[2] = String.valueOf(id);
//            data[3] = name;
//            data[4] = address;
//            data[5] = city;
//            data[6] = state;


            Log.e(TAG, Arrays.toString(field) + Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG, result);
                if (putData.onComplete()) {
                    result = putData.getResult();
                    Log.e(TAG, result);
                    try {
                        JSONObject response = new JSONObject(result);

                        // Extract the "message" string
                        String message = response.getString("message");

                        // Use the "message" string as needed
                        handleResponse(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to the server");
            }
        }
    }

    private void handleError(String error) {
        mListener.onSaveDeliveryAddressDetailsEditUpdateApiError(error);
    }

    private void handleResponse(String message) {
        mListener.onSaveDeliveryAddressDetailsEditUpdate(message);
    }


    public interface OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner {
        void onSaveDeliveryAddressDetailsEditUpdate(String message);

        void onSaveDeliveryAddressDetailsEditUpdateApiError(String error);
    }


}
