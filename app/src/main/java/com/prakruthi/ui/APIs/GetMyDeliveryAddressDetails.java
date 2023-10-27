package com.prakruthi.ui.APIs;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.prakruthi.ui.ui.AddressUpdateDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
import com.prakruthi.ui.ui.profile.myaddress.MyAddAddressesAdaptor;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GetMyDeliveryAddressDetails {

    //    Integer id;
    String id;

    private GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener mListener;

    public GetMyDeliveryAddressDetails(GetMyDeliveryAddressDetailsFetchedListener listener, String id) {
        mListener = listener;
        this.id = id;

    }

    public void HitMyDeliveryAddressDetailsApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsApiApi());
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

    private void handleResponse(String result) {
//        if (result != null) {
            if (mListener != null && result != null) {

                try {
                Log.wtf("GetMyDeliveryAddressDetailsApi: ", result);
                JSONObject jsonResponse = new JSONObject(result);
                if (jsonResponse.getBoolean("status_code")) {
                    JSONObject UserData = jsonResponse.getJSONObject("delivery_address");

                     ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();

                    AddressUpdateDetails.name = UserData.getString("name");
                    AddressUpdateDetails.city = UserData.getString("city");
                    AddressUpdateDetails.state = UserData.getString("state");
                    AddressUpdateDetails.district = UserData.getString("district");
                    AddressUpdateDetails.country = UserData.getString("country");
                    AddressUpdateDetails.address = UserData.getString("address");
                    AddressUpdateDetails.postal_code = UserData.getString("postal_code");
                    AddressUpdateDetails.full_address = UserData.getString("full_address");


//                    mListener.onGetMyDeliveryAddressDetailsDataFetched(addressList1);
                    mListener.onGetMyDeliveryAddressDetailsDataFetched();
//                    mListener.onGetMyDeliveryAddressDetailsDataFetched(addressModelsList);



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


    private void handleError(String failed_to_fetch_data) {
        mListener.onGetMyDeliveryAddressDetailsDataFetchError(failed_to_fetch_data);
    }

//    @Override
//    protected void onPostExecute(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
//        super.onPostExecute(addressList);
//        mListener.onDeliveryAddressLoaded(addressList);
//    }

    public interface GetMyDeliveryAddressDetailsFetchedListener {

        void onGetMyDeliveryAddressDetailsDataFetched();
//        void onGetMyDeliveryAddressDetailsDataFetched(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1);

//        onDeliveryAddressLoadedEditUpdate
        //ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList

        void onGetMyDeliveryAddressDetailsDataFetchError(String error);

    }


}
