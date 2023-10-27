package com.prakruthi.ui.APIs;

import android.os.AsyncTask;
import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.AddressUpdateDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetMyDeliveryAddressDetailsAsyncTask extends AsyncTask<Void, Void, ArrayList<Address_BottomSheet_Recycler_Adaptor_Model>> {

    ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();

    String id;

    //    private static final String URL = Variables.BaseUrl + "getDeliveryAddressDetails";
    private static final String URL = Variables.BaseUrl + "getMyDeliveryAddressDetails";


    private static final String[] FIELD = {"user_id", "token", "id"};

    private final String[] DATA = {String.valueOf(Variables.id), Variables.token, String.valueOf(id)};

    //    private DeliveryAddressListener mListener;
//private onGetMyDeliveryAddressDetailsDataFetched mListener;
    private onGetMyDeliveryAddressDetailsDataFetchedListner mListener;


    //    public GetMyDeliveryAddressDetailsAsyncTask(DeliveryAddressListener listener) {
//        mListener = listener;
//        this.id = id;
//    }
    public GetMyDeliveryAddressDetailsAsyncTask(onGetMyDeliveryAddressDetailsDataFetchedListner listener, String id) {
        mListener = listener;
//        this.id = this.id;
        this.id = id;
    }


    public void HitMyDeliveryAddressDetailsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetMyDeliveryAddressDetailsApiApi());
    }

    private class GetMyDeliveryAddressDetailsApiApi implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[3];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "id";

            //Creating array for data
            String[] data = new String[3];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = String.valueOf(id);

            PutData putData = new PutData(Variables.BaseUrl + "getMyDeliveryAddressDetails", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    handleResponse(result);
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to server");
            }
        }
    }


    @Override
    protected ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> doInBackground(Void... voids) {
        ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList = new ArrayList<>();

        // Create PutData object to make API call
        PutData putData = new PutData(URL, "POST", FIELD, DATA);

        // Start API call
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                //2
                handleResponse(result);

                try {
                    // Parse JSON response
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("delivery_address");

                    // Loop through JSON array and create Address objects
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        String address = obj.getString("full_address");

                        String state = obj.getString("state");
                        String city = obj.getString("city");

                        int Defualt = obj.getInt("is_default");
                        int id = obj.getInt("id");
                        Variables.addressID = String.valueOf(id);
                        if (Defualt == 1) {
                            Variables.address = address;
                        }
                        addressList.add(new Address_BottomSheet_Recycler_Adaptor_Model(name, address, state, city, Defualt, id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                handleError("Failed to fetch data");
            }


        } else {
            handleError("Failed to connect to server");
        }
        return addressList;
    }

    private void handleResponse(String result) {
//        if (result != null) {

            if (mListener != null && result != null) {
            try {
                Log.wtf("GetMyDeliveryAddressDetailsApi: ", result);
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.getBoolean("status_code")) {

                    ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1 = new ArrayList<Address_BottomSheet_Recycler_Adaptor_Model>();

                    JSONObject UserData = jsonResponse.getJSONObject("delivery_address");

                    ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();

                    AddressUpdateDetails.name = UserData.getString("name");
                    AddressUpdateDetails.city = UserData.getString("city");
                    AddressUpdateDetails.state = UserData.getString("state");
                    AddressUpdateDetails.district = UserData.getString("district");
                    AddressUpdateDetails.country = UserData.getString("country");
                    AddressUpdateDetails.address = UserData.getString("address");
                    AddressUpdateDetails.postal_code = UserData.getString("postal_code");
                    AddressUpdateDetails.is_default = UserData.getString("is_default");
//                    AddressUpdateDetails.full_address = UserData.getString("full_address");


//                    mListener.onGetMyDeliveryAddressDetailsDataFetched(addressList1);
//                    mListener.onGetMyDeliveryAddressDetailsDataFetched(message);
                    mListener.onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(addressModelsList);
//                    mListener.onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(addressList1);
//                    mListener.onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1);




                } else {
                    handleError("Internal Error");
                }


            } catch (JSONException e) {
                e.printStackTrace();
                handleError("Failed to parse data");
            }
        } else {
            handleError("Failed to fetch data");
        }

    }


    @Override
    protected void onPostExecute(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {
        super.onPostExecute(addressList1);
//        mListener.onDeliveryAddressLoaded(addressList);
        mListener.onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(addressList1);
    }

    private void handleError(String failed_to_fetch_data) {
        mListener.onGetMyDeliveryAddressDetailsDataFetchError(failed_to_fetch_data);
        //onDeliveryAddressLoaded(
    }


    //    public interface DeliveryAddressListener {
    public interface onGetMyDeliveryAddressDetailsDataFetchedListner {

        //        void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList);
        void onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1);

        void onGetMyDeliveryAddressDetailsDataFetchError(String error);


//        void onGetMyDeliveryAddressDetailsDataFetched();
    }

}
