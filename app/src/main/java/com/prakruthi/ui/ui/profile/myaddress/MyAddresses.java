package com.prakruthi.ui.ui.profile.myaddress;

import static com.prakruthi.ui.Variables.postalCode;
import static com.prakruthi.ui.ui.AddressUserDetails.address;
import static com.prakruthi.ui.ui.AddressUserDetails.city;
import static com.prakruthi.ui.ui.AddressUserDetails.country;
import static com.prakruthi.ui.ui.AddressUserDetails.is_default;
import static com.prakruthi.ui.ui.AddressUserDetails.name;
import static com.prakruthi.ui.ui.AddressUserDetails.state;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.DeleteDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetailsAsyncTask;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetails;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetailsEditUpdate;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddressUserDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
//import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate;

import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAddresses extends AppCompatActivity implements GetDeliveryAddressDetails.DeliveryAddressListener, SaveDeliveryAddressDetails.OnSaveDeliveryAddressApiHits, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits, SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner, GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner, GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener {

    //    RecyclerView myAddresses_personal_address_recyclerview_List;
    ShimmerRecyclerView myAddresses_personal_address_recyclerview_List;

    AppCompatButton btn_add_new_address, txtRemove;

    ImageView iv_edit_yourAddress;
//    ImageView

    //    public Integer id;
    public String id;

    EditText nameEditText, countryEditText;
    EditText cityEditText, stateEditText, idEditText, addressEditText, postalCodeEditText;

    AppCompatButton myAddress_back_btn;

    int stateId = 0;
    PowerSpinnerView spinner_city, editTextState, editTextDistrict;


    //    ArrayList<String> districtNames = new ArrayList<>();
    ArrayList<String> cityNames = new ArrayList<>();

    GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener mListner;
    //    GetMyDeliveryAddressDetailsAsyncTask.DeliveryAddressListener mListner1;
    GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner mListner1;


    @SuppressLint({"WrongViewCast", "RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        myAddress_back_btn = findViewById(R.id.myAddress_back_btn);

        myAddress_back_btn.setOnClickListener(v -> super.onBackPressed());

//        View root = binding.getRoot();
//        id = root.getId();

        getMyDeliveryAddressDetailsAsyncTask();
        getDeliveryAddressDetails();

        myAddresses_personal_address_recyclerview_List = findViewById(R.id.myAddresses_personal_address_recyclerview_List);

        // set an OnTouchListener to the root view
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    stateEditText.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = stateEditText.getWidth();
                    int height = stateEditText.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {
                        // dismiss the state view
//                        stateEditText.dismiss();
////                        district.dismiss();
//                        cityEditText.dismiss();

//                        type.dismiss();
                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });

        btn_add_new_address = findViewById(R.id.btn_add_new_address);

        iv_edit_yourAddress = findViewById(R.id.iv_edit_yourAddress);


        btn_add_new_address.setOnClickListener(v -> {
            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Add New Address");

            // Set the custom layout for the dialog
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_address, null);
            builder.setView(dialogView);

            EditText nameEditText = dialogView.findViewById(R.id.edittext_name);
            stateEditText = dialogView.findViewById(R.id.editTextState);
            cityEditText = dialogView.findViewById(R.id.edittext_city);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country);
            EditText addressEditText = dialogView.findViewById(R.id.edittext_address);
            EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code);
            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default);

            // Add click listener to the Submit button
            nameEditText.setText(name);
            stateEditText.setText(state);
            cityEditText.setText(city);
            countryEditText.setText(country);
            addressEditText.setText(address);
            postalCodeEditText.setText(AddressUserDetails.postal_code);
            defaultCheckBox.setText(is_default);
//            addressEditText.setText(AddressUserDetails.postal_code);
//            addressEditText.setText(AddressUserDetails.is_default);

            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {

                    stateEditText.setError(null);
                    cityEditText.setError(null);

                    String name = nameEditText.getText().toString();
                    String state = stateEditText.getText().toString();
                    String city = cityEditText.getText().toString();
                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();


                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
                        hasError = true;
                    }

                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation
//                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, "city", country, address, postalCode, boolToInt(isDefault));
                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));

                        saveDeliveryAddressDetails.HitApi();
                        Loading.show(this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
            });

            dialog.show();
        });

//        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this, id);
        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);

        getDeliveryAddressDetails.execute();

        //    ProfileFragmentHttpURLConnection/Edit(Half):------------------------------------------------------------------------------

        iv_edit_yourAddress = findViewById(R.id.iv_edit_yourAddress);

//        iv_edit_yourAddress.setOnClickListener(v -> {
        myAddresses_personal_address_recyclerview_List.setOnClickListener(v -> {

//            GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(new GetMyDeliveryAddressDetailsAsyncTask.DeliveryAddressListener() {
            GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(new GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner() {
//            GetDeliveryAddressDetails getMyDeliveryAddressDetailsAsyncTask = new GetDeliveryAddressDetails(new GetDeliveryAddressDetails.DeliveryAddressListener() {

                @Override
                public void onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {
//                    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {


                    try {
                        MyAddresses.this.runOnUiThread(() -> {
//                            iv_edit_yourAddress.setClickable(true);
                            myAddresses_personal_address_recyclerview_List.setClickable(true);
                            showDialog();
                        });
                    } catch (Exception ignore) {
                        MyAddresses.this.runOnUiThread(() -> {
//                            iv_edit_yourAddress.setClickable(true);
                            myAddresses_personal_address_recyclerview_List.setClickable(true);
                        });
                    }
                }

                @Override
                public void onGetMyDeliveryAddressDetailsDataFetchError(String error) {
//                public void onDeliveryAddressLoadedError(String error) {

                    MyAddresses.this.runOnUiThread(() -> {
                        try {
                            Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();
//                            iv_edit_yourAddress.setClickable(true);

                            myAddresses_personal_address_recyclerview_List.setClickable(true);

                        } catch (Exception ignore) {

                        }
                    });
                }

//            GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(new GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener() {
//            GetUserData getUserData = new GetUserData(new GetUserData.OnGetUserDataFetchedListener() {

//                @Override
//                public void onGetMyDeliveryAddressDetailsDataFetched() {
//                    try {
//                        MyAddresses.this.runOnUiThread(() -> {
////                            iv_edit_yourAddress.setClickable(true);
//                            myAddresses_personal_address_recyclerview_List.setClickable(true);
//                            showDialog();
//                        });
//                    } catch (Exception ignore) {
//                        MyAddresses.this.runOnUiThread(() -> {
////                            iv_edit_yourAddress.setClickable(true);
//                            myAddresses_personal_address_recyclerview_List.setClickable(true);
//                        });
//                    }
//                }

//                @Override
//                public void onGetMyDeliveryAddressDetailsDataFetchError(String error) {
//                    MyAddresses.this.runOnUiThread(() -> {
//                        try {
//                            Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();
////                            iv_edit_yourAddress.setClickable(true);
//
//                            myAddresses_personal_address_recyclerview_List.setClickable(true);
//
//                        } catch (Exception ignore) {
//
//                        }
//                    });
//
//                }
            }, id);

//            getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();
            getMyDeliveryAddressDetailsAsyncTask.HitMyDeliveryAddressDetailsApi();
            getMyDeliveryAddressDetailsAsyncTask.execute();

//            getDeliveryAddressDetails.


            myAddresses_personal_address_recyclerview_List.setClickable(true);

//        GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails((GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener) this, id);
//            GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails((GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener) this);
//            getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();

        });
//        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
//        getDeliveryAddressDetails.execute();
    }

    public void getMyDeliveryAddressDetailsAsyncTask() {
        GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(this, id);
        getMyDeliveryAddressDetailsAsyncTask.HitMyDeliveryAddressDetailsApi();
//        getMyDeliveryAddressDetailsAsyncTask.equals();
    }

//    public void getMyDeliveryAddressDetails() {
//        GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(this, id);
//        getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();
//    }

    public void getDeliveryAddressDetails() {
        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    //    ProfileFragmentHttpURLConnection/Edit(Full):------------------------------------------------------------------------------
//--------------------------------------------------------

//    private void showDialog() {
////        iv_edit_yourAddress.setOnClickListener(v -> {
////            final Dialog dialog = new Dialog(MyAddresses.this);
////            dialog.setContentView(R.layout.dialog_edit_address);
////            dialog.setCancelable(true);
//        final Dialog dialogView = new Dialog(MyAddresses.this);
//        dialogView.setContentView(R.layout.dialog_edit_address);
//        dialogView.setCancelable(true);
//
//        // Set the dialog's window width to match_parent
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialogView.getWindow().getAttributes());
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogView.getWindow().setAttributes(layoutParams);
//
////        // Create an AlertDialog Builder
////        AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
////        builder.setTitle("Edit New Address");
//
////        // Set the custom layout for the dialog
////        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
////        builder.setView(dialogView);
//
//        EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
//        cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
//        stateEditText = dialogView.findViewById(R.id.editTextState_edit);
//        idEditText = dialogView.findViewById(R.id.edittext_id_edit);
//        EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
//        EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
//        EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
//        CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);
//
//        Button buttonSubmit = dialogView.findViewById(R.id.edit_address_btn_save);
//
//        // Add click listener to the Submit button
//        nameEditText.setText(AddressUserDetails.name);
//        cityEditText.setText(AddressUserDetails.city);
//        stateEditText.setText(AddressUserDetails.state);
//        idEditText.setText(AddressUserDetails.id);
//        countryEditText.setText(AddressUserDetails.country);
//        addressEditText.setText(AddressUserDetails.address);
//        postalCodeEditText.setText(AddressUserDetails.postal_code);
//        defaultCheckBox.setText(is_default);
//
//
//        //-------Extra
////        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
////            stateId = Integer.parseInt(newItem);
////            getDropDownData(editTextState, editTextDistrict);
////            editTextDistrict.setText("District");
////        });
////
////        getDropDownData(editTextState, editTextDistrict);
//
//        buttonSubmit.setOnClickListener(view -> {
//
//            cityEditText.setError(null);
//            stateEditText.setError(null);
//
//
//            // Handle the save button click
//            String name = nameEditText.getText().toString();
//            String city = cityEditText.getText().toString();
////          String city = String.valueOf(cityEditText.getSelectedIndex() + 1);
//            String state = stateEditText.getText().toString();
////          String state = String.valueOf(stateEditText.getSelectedIndex() + 1);
//
////            Integer id = Integer.valueOf(idEditText.getText().toString());
//            String id = idEditText.getText().toString();
//
//            String country = countryEditText.getText().toString();
//            String address = addressEditText.getText().toString();
//            String postalCode = postalCodeEditText.getText().toString();
//            boolean isDefault = defaultCheckBox.isChecked();
//
//
//            // Check if any of the fields are empty
//            boolean hasError = false;
//            if (name.isEmpty()) {
//                nameEditText.setError("Name is required");
//                hasError = true;
//            }
//            if (city.isEmpty()) {
//                cityEditText.setError("City is required");
//                hasError = true;
//            }
//            if (state.isEmpty()) {
//                stateEditText.setError("State is required");
//                hasError = true;
//            }
////            if (id.isEmpty()) {
////                idEditText.setError("Id is required");
////                hasError = true;
////            }
//            if (id == null) {
//                idEditText.setError("Id is required");
//                hasError = true;
//            }
//            if (country.isEmpty()) {
//                countryEditText.setError("Country is required");
//                hasError = true;
//            }
//            if (address.isEmpty()) {
//                addressEditText.setError("Address is required");
//                hasError = true;
//            }
//            if (postalCode.isEmpty()) {
//                postalCodeEditText.setError("Postal Code is required");
//                hasError = true;
//            }
//
////            if (isDefault.isEmpty()) {
////                defaultCheckBox.setError("Postal Code is required");
////                hasError = true;
////            }
//
//            if (hasError) {
//                return;
//            }
//            //or
//            if (!hasError) {
//                // All fields are properly filled, perform the save operation
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode);
//
////                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, name, address, city, state);
//
////                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, id, name, city, state, country, address, postal_code, is_default);
////                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, id, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();
////                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate((SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner) this, name, state, city, id, country, address, postalCode, boolToInt(isDefault));
////                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate((SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner) this, id, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();
//
////                saveDeliveryAddressDetails.HitApi();
//                Loading.show(MyAddresses.this);
//                // Dismiss the dialog
////                dialog.dismiss();
//                return;
//            } else {
//
////                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(
//                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(
//
////                                nameEditText.getText().toString(), stateEditText.getText().toString(), cityEditText.getText().toString(), countryEditText.getText().toString(), addressEditText.getText().toString(),postalCodeEditText.getText().toString(),
//                        nameEditText.getText().toString(),
//                        cityEditText.getText().toString(),
//                        stateEditText.getText().toString(),
//                        idEditText.getText().toString(),
//                        countryEditText.getText().toString(),
//                        addressEditText.getText().toString(),
//                        postalCodeEditText.getText().toString(),
//                        //is_default
//
//                        //Postalcode
//
////                        new UserDetailsUpdate.OnUserDetailsUpdateListener() {
//                        new SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner() {
//
//                            @Override
////                            public void onUserDetailsUpdate(String messaage) {
//                            public void onSaveDeliveryAddressDetailsEditUpdate(String messaage) {
//
//                                try {
//                                    MyAddresses.this.runOnUiThread(() -> {
//
//                                        Variables.name = nameEditText.getText().toString();
////                                        myAddresses_Name.setText(Variables.name);
////                                        myAddresses_personal_address_recyclerview_List.setText(Variables.name);
//
//                                        myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
////                                        myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner));
////                                        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(this, this,name, city, state, id, country, address, postalCode));
//
//                                        ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList = new ArrayList<>();
//
//
////                                        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(this, this, name, city, state, id, country, address, postalCode));
//                                        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList, this, name, city, state, id, country, address, postalCode));
//
//
//                                        Variables.address = addressEditText.getText().toString();
////                                                txt_personal_my_address.setText(address);
////                                        myAddresses_personal_address_recyclerview_List.setText(address);
//
//                                        Toast.makeText(MyAddresses.this, messaage, Toast.LENGTH_SHORT).show();
////                                        dialog.dismiss();
//                                        Loading.hide();
//
//
//                                    });
//                                } catch (Exception ignore) {
//                                    Loading.hide();
//                                }
//
//                            }
//
//                            @Override
////                            public void onUserDetailsError(String error) {
//                            public void onSaveDeliveryAddressDetailsEditUpdateApiError(String error) {
//
//                                MyAddresses.this.runOnUiThread(() -> {
//                                    try {
//                                        Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();
////                                        dialog.dismiss();
//                                        Loading.hide();
//                                    } catch (Exception ignore) {
//                                        Loading.hide();
//                                    }
//                                });
//
//                            }
//                        });
//
////                userDetailsUpdate.HitUserDetailsUpdateApi();
//                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();
//
//                Loading.show(MyAddresses.this);
//            }
//
//        });
////    });
//
////        dialog.show();
//    }
//    }


    //Add Address type:--
    private void showDialog() {
//        iv_edit_yourAddress.setOnClickListener(v -> {
        myAddresses_personal_address_recyclerview_List.setOnClickListener(v -> {

//            final Dialog dialog = new Dialog(MyAddresses.this);
//            dialog.setContentView(R.layout.dialog_edit_address);
//            dialog.setCancelable(true);
            final Dialog dialogView = new Dialog(MyAddresses.this);
            dialogView.setContentView(R.layout.dialog_edit_address);
            dialogView.setCancelable(true);

            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Edit New Address");

            // Set the custom layout for the dialog
//            View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
//            builder.setView(dialogView);

            EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
            stateEditText = dialogView.findViewById(R.id.editTextState_edit);
            cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
            EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
            EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);

            Button buttonSubmit = dialogView.findViewById(R.id.edit_address_btn_save);

            // Add click listener to the Submit button
            nameEditText.setText(name);
            stateEditText.setText(state);
            cityEditText.setText(city);
            countryEditText.setText(country);
            addressEditText.setText(address);
            postalCodeEditText.setText(AddressUserDetails.postal_code);
            defaultCheckBox.setText(AddressUserDetails.is_default);


            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {

                    stateEditText.setError(null);
                    cityEditText.setError(null);


                    // Handle the save button click
                    String name = nameEditText.getText().toString();

                    String state = stateEditText.getText().toString();
//                    String state = String.valueOf(stateEditText.getSelectedIndex() + 1);

                    String city = cityEditText.getText().toString();
//                    String city = String.valueOf(cityEditText.getSelectedIndex() + 1);

                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();


                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
                        hasError = true;
                    }
                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation
//                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
//                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode);
//                        SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate((SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner) this, name, state, city, id, country, address, postalCode, boolToInt(isDefault));
//                        SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
//                        SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, name, state, city, id,country, address, postalCode, isDefault);
                        SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(this, name, state, city, id, country, address, postalCode, boolToInt(isDefault));


//                        saveDeliveryAddressDetails.HitApi();
                        saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();
                        Loading.show(MyAddresses.this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }

//                    else {
//
////                        UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(
//                        SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(
//
////                                nameEditText.getText().toString(), stateEditText.getText().toString(), cityEditText.getText().toString(), countryEditText.getText().toString(), addressEditText.getText().toString(),postalCodeEditText.getText().toString(),
//                                nameEditText.getText().toString(),
//                                cityEditText.getText().toString(),
//                                stateEditText.getText().toString(),
//                                idEditText.getText().toString(),
//                                countryEditText.getText().toString(),
//                                addressEditText.getText().toString(),
//                                postalCodeEditText.getText().toString(),
//
////                                new UserDetailsUpdate.OnUserDetailsUpdateListener() {
//                                new SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner() {
//
//                                    @Override
////                                    public void onUserDetailsUpdate(String messaage) {
//                                    public void onSaveDeliveryAddressDetailsEditUpdate(String messaage) {
//
//                                        MyAddresses.this.runOnUiThread(() -> {
//                                            try {
//                                                Variables.name = nameEditText.getText().toString();
////                                                myAddresses_Name.setText(Variables.name);
//
////                                                email = stateEditText.getText().toString();
////                                                txt_personal_my_address.setText(email);
////                                                myAddresses_personal_address_recyclerview_List.setText(Variables.name);
//
//                                                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
//
//                                                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner));
//                                                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(this, this, name, city, state, id, country, address, postalCode));
//
//
//                                                Toast.makeText(MyAddresses.this, messaage, Toast.LENGTH_SHORT).show();
//                                                dialog.dismiss();
//                                                Loading.hide();
//                                            } catch (Exception ignore) {
//                                                Loading.hide();
//                                            }
//
//                                        });
//
//                                    }
//
//                                    @Override
//                                    public void onUserDetailsError(String error) {
//
//                                        MyAddresses.this.runOnUiThread(() -> {
//                                            try {
//                                                Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();
//                                                dialog.dismiss();
//                                                Loading.hide();
//                                            } catch (Exception ignore) {
//                                                Loading.hide();
//                                            }
//                                        });
//
//                                    }
//                                });
//
//                        userDetailsUpdate.HitUserDetailsUpdateApi();
//                        Loading.show(MyAddresses.this);
//                    }

                });
            });

            dialog.show();
        });
    }

    @SuppressLint("StaticFieldLeak")
//    public void getDropDownData(PowerSpinnerView State) {
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView City) {

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
//                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");

                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("getDropDownData", result);
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            return jsonObj;
//                            return new JSONObject(result);
                        } catch (JSONException e) {
                            return null;
                        }
                    }
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObj) {
                super.onPostExecute(jsonObj);

                if (jsonObj != null) {
                    try {

                        JSONArray states = jsonObj.getJSONArray("state");
                        ArrayList<String> stateNames = new ArrayList<>();
                        for (int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            stateNames.add(state.getString("state"));
                        }
//                        State.setItems(stateNames);
//                        stateEditText.setItems(stateNames);

                        //----------
                        JSONArray districts = jsonObj.getJSONArray("city");
//                        districtNames.clear();
                        cityNames.clear();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);

                            if (districtt.getInt("state_id") == stateId) {

//                                districtNames.add(districtt.getString("name"));
                                cityNames.add(districtt.getString("city"));

//                                City.setItems(cityNames);
//                                cityEditText.setItems(cityNames);

                            }


                            cityNames.add(districtt.getString("city"));
                        }
                        City.setItems(cityNames);


                    } catch (JSONException e) {
                        Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
//    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressList) {
        try {
            MyAddresses.this.runOnUiThread(() -> {
                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList, this, this, this));
                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddAddressesAdaptor(addressList, this, this, this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList));

            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    //    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
//    public void onDeliveryAddressLoadedEditUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressList) {
//    public void onGetMyDeliveryAddressDetailsDataFetched(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressList1) {
//    public void onGetMyDeliveryAddressDetailsDataFetched(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {

    //    public void onGetMyDeliveryAddressDetailsDataFetchedListner(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {
    public void onGetMyDeliveryAddressDetailsDataFetchedEditUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {
//    public void onGetMyDeliveryAddressDetailsDataFetchError(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList1) {

//        GetMyDeliveryAddressDetailsAsyncTask
//                onGetMyDeliveryAddressDetailsDataFetched
        try {
            MyAddresses.this.runOnUiThread(() -> {
                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList, this, this, this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList1, this, name, city, state, id, country, address, postalCode));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList1, this, name, city, state, id, country, address, postalCode));
//
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyEditAddressesAdaptor(addressList1, this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyEditAddressesAdaptor(addressList1, (DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits) this, (GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener) this));
                myAddresses_personal_address_recyclerview_List.setAdapter(new MyEditAddressesAdaptor(addressList1, (DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits) this, (GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner) this));


//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddAddressesAdaptor(addressList1, this));
                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList1, this, name, city, state, id, country, address, postalCode));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList1, this));


//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(addressList1, name,city,state,id,country,address,postalCode,isDefault));

//                ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressList
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList));

            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    @Override
    public void onGetMyDeliveryAddressDetailsDataFetched() {
        try {
            MyAddresses.this.runOnUiThread(() -> {
                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyEditAddressesAdaptor((DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits) this, (GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner) this));
//                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptorEditSaveUpdate(this, name, city, state, id, country, address, postalCode));
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

    }

    @Override
    public void onGetMyDeliveryAddressDetailsDataFetchError(String error) {
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }


    @Override
    public void onSaveDeliveryAddress(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this, id);
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);

            getDeliveryAddressDetails.execute();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added New Address").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
            Loading.hide();
        });
    }

    @Override
    public void onSaveDeliveryAddressApiError(String error) {
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }


    @Override
    public void onSaveDeliveryAddressDetailsEditUpdate(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

//            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(this, id);

//            getDeliveryAddressDetails.execute();
            getMyDeliveryAddressDetailsAsyncTask.execute();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added New Address").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
            Loading.hide();
        });

    }

    @Override
    public void onSaveDeliveryAddressDetailsEditUpdateApiError(String error) {
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void OnDeleteDeliveryAddress(String message) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();

//            getCartDetails();
            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
//            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this, id);
            getDeliveryAddressDetails.execute();
        });
    }

    @Override
    public void OnOnDeleteDeliveryAddressApiGivesError(String error) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();

//            getCartDetails();
            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
//            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this, id);
            getDeliveryAddressDetails.execute();

        });
    }


}