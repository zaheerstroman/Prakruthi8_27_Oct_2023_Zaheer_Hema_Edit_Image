package com.prakruthi.ui.ui.profile.myaddress;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.DeleteDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetailsAsyncTask;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetailsEditUpdate;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddressUserDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesAdaptorEditSaveUpdate extends RecyclerView.Adapter<MyAddressesAdaptorEditSaveUpdate.ViewHolder> {

    ImageView iv_edit_yourAddress;

    String name;
    String city;
    String state;
//        Integer id;
    String id;
    String country;
    String address;
    String postalCode;
    boolean isDefault;


    //    private List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList;
    private ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();
//    private ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressModelsList = new ArrayList<>();


    public Context context;

    EditText cityEditText, stateEditText, addressEditText, postalCodeEditText, cityEditText_edit, stateEditText_edit;

    View dialogView;
    private List<String> data;

    DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner;

    GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener getMyDeliveryAddressDetailsFetchedListener;

    GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner getMyDeliveryAddressDetailsDataFetchedListner;
    SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner;



//    public MyAddressesAdaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner) {
//    public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner) {
//    public MyAddressesAdaptorEditSaveUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner, Context context) {
//    public MyAddressesAdaptorEditSaveUpdate(SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner, String name, String city, String state, Integer id, String country, String address, String postalCode, boolean isDefault) {
public MyAddressesAdaptorEditSaveUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList,SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner, String name, String city, String state, String id, String country, String address, String postalCode) {

//    public MyAddressesAdaptorEditSaveUpdate(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner, Context context) {
//    public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener getMyDeliveryAddressDetailsFetchedListener, Context context) {

    this.addressModelsList.clear();

    this.addressModelsList = addressModelsList;
//        this.deleteDeliveryAddressListner = deleteDeliveryAddressListner;

        this.getMyDeliveryAddressDetailsFetchedListener = getMyDeliveryAddressDetailsFetchedListener;
        this.saveDeliveryAddressDetailsEditUpdateListner = saveDeliveryAddressDetailsEditUpdateListner;

//        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.id = id;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
//        this.isDefault = isDefault;

//        this.inflater = inflater;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAddressesAdaptorEditSaveUpdate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_myaddress_layout, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_myaddress_layout_add, parent, false);

        return new MyAddressesAdaptorEditSaveUpdate.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressesAdaptorEditSaveUpdate.ViewHolder holder, int position) {
        try {

            Address_BottomSheet_Recycler_Adaptor_Model addressModel = addressModelsList.get(position);
//            Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate addressModel = addressModelsList.get(position);

            holder.txtName.setText(addressModel.getName());
            holder.txtAddress.setText(addressModel.getAddress());

            holder.txt_personal_my_address_state.setText(addressModel.getState());
            holder.txt_personal_my_address_city.setText(addressModel.getCity());


            holder.txtRemove.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                DeleteDeliveryAddressDetails deleteDeliveryAddressDetails = new DeleteDeliveryAddressDetails(deleteDeliveryAddressListner, addressModel.getId());
//                deleteDeliveryAddressDetails.execute();
                deleteDeliveryAddressDetails.HitApi();
            });

            holder.iv_edit_yourAddress.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());

//                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(saveDeliveryAddressDetailsEditUpdateListner, name,address,city,state,String.valueOf(addressModel.getId()),country,postalCode,isDefault);
//                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(saveDeliveryAddressDetailsEditUpdateListner, name, address, city, state, id, country, postalCode, isDefault);
                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(saveDeliveryAddressDetailsEditUpdateListner, name, city, state, id, country, address, postalCode,boolToInt(isDefault));
                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();


//                GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(getMyDeliveryAddressDetailsFetchedListener, this.toString(), this);
//                getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();

                GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(
                        getMyDeliveryAddressDetailsDataFetchedListner,id);
                getMyDeliveryAddressDetailsAsyncTask.HitMyDeliveryAddressDetailsApi();


//                GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(getMyDeliveryAddressDetailsFetchedListener, addressModel.getId());
//                getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();

            });


//            holder.iv_edit_yourAddress.setOnClickListener(v -> {
//
//                holder.iv_edit_yourAddress.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductPage.class).putExtra("productId", String.valueOf(addressModel.getProduct_id())));
//
//                holder.iv_edit_yourAddress.getContext().startActivity(new Intent(holder.itemView.getContext(), TrackOrderActivity.class).putExtra("Tracking_id", String.valueOf(myOrdersModal.getId())));
//
//
//            });

//            holder.iv_edit_yourAddress.setOnClickListener(v -> {
//
//                // Create an AlertDialog Builder
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Edit Address");
//
//
//
//                // Set the custom layout for the dialog
////                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
////                 dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
//
////                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
//                 dialogView = inflater.inflate(R.layout.dialog_edit_address, null);
//
//
//                builder.setView(dialogView);
//
//                EditText nameEditText_edit = dialogView.findViewById(R.id.edittext_name_edit);
//                stateEditText_edit = dialogView.findViewById(R.id.editTextState_edit);
//                cityEditText_edit = dialogView.findViewById(R.id.edittext_city_edit);
//                EditText countryEditText_edit = dialogView.findViewById(R.id.edittext_country_edit);
//                EditText addressEditText_edit = dialogView.findViewById(R.id.edittext_address_edit);
//                EditText postalCodeEditText_edit = dialogView.findViewById(R.id.edittext_postal_code_edit);
//                CheckBox defaultCheckBox_edit = dialogView.findViewById(R.id.checkbox_default_edit);
//
//
//                // Add click listener to the Submit button
//                nameEditText_edit.setText(AddressUserDetails.name);
//                stateEditText_edit.setText(AddressUserDetails.state);
//                cityEditText_edit.setText(AddressUserDetails.city);
//                countryEditText_edit.setText(AddressUserDetails.country);
//                addressEditText_edit.setText(AddressUserDetails.address);
//                postalCodeEditText_edit.setText(AddressUserDetails.postal_code);
//                defaultCheckBox_edit.setText(AddressUserDetails.is_default);
//
//
//                // Set the positive and negative buttons
//                builder.setPositiveButton("Save", null); // Set null initially
//
//                builder.setNegativeButton("Cancel", (dialog, which) -> {
//                    // Handle the cancel button click
//                    // Dismiss the dialog
//                    dialog.dismiss();
//                });
//
//                // Create and show the dialog
//                AlertDialog dialog = builder.create();
//
//                // Override the onClickListener for the positive button
//                dialog.setOnShowListener(dialogInterface -> {
//                    Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                    saveButton.setOnClickListener(view -> {
//
//                        stateEditText_edit.setError(null);
//                        cityEditText_edit.setError(null);
//
//
//                        // Handle the save button click
//                        String name = nameEditText_edit.getText().toString();
//
//                        String city = cityEditText_edit.getText().toString();
////                    String city = String.valueOf(cityEditText.getSelectedIndex() + 1);
//
//                        String state = stateEditText_edit.getText().toString();
////                    String state = String.valueOf(stateEditText.getSelectedIndex() + 1);
//
//                        String country = countryEditText_edit.getText().toString();
//                        String address = addressEditText_edit.getText().toString();
//                        String postalCode = postalCodeEditText_edit.getText().toString();
//                        boolean isDefault = defaultCheckBox_edit.isChecked();
//
//
//                        // Check if any of the fields are empty
//                        boolean hasError = false;
//                        if (name.isEmpty()) {
//                            nameEditText_edit.setError("Name is required");
//                            hasError = true;
//                        }
//                        if (city.isEmpty()) {
//                            cityEditText_edit.setError("City is required");
//                            hasError = true;
//                        }
//                        if (state.isEmpty()) {
//                            stateEditText_edit.setError("State is required");
////                        ObjectAnimator.ofFloat(state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
////                        state.requestFocus();
//                            hasError = true;
//                        }
//                        if (country.isEmpty()) {
//                            countryEditText_edit.setError("Country is required");
//                            hasError = true;
//                        }
//                        if (address.isEmpty()) {
//                            addressEditText_edit.setError("Address is required");
//                            hasError = true;
//                        }
//                        if (postalCode.isEmpty()) {
//                            postalCodeEditText_edit.setError("Postal Code is required");
//                            hasError = true;
//                        }
//
//                        if (!hasError) {
//                            // All fields are properly filled, perform the save operation
////                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, "city", country, address, postalCode, boolToInt(isDefault));
//                            SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails((SaveDeliveryAddressDetails.OnSaveDeliveryAddressApiHits) this, name, state, city, country, address, postalCode, boolToInt(isDefault));
//
//                            saveDeliveryAddressDetails.HitApi();
//                            Loading.show(context);
//                            // Dismiss the dialog
//                            dialog.dismiss();
//                        }
//
//                    });
//                });
//
//                dialog.show();
//            });

            //-------


//            holder.iv_edit_yourAddress.setOnClickListener(v -> {
//                Loading.show(holder.itemView.getContext());
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(this, name, state, city, country, address);
////                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(this, name, state, city, country);
//
////                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
//
//
////                deleteDeliveryAddressDetails.execute();
////                deleteDeliveryAddressDetails.HitApi();
////                saveDeliveryAddressDetails.HitApi();
////                userDetailsUpdate.HitUserDetailsUpdateApi();
//
////                userDetailsUpdate.HitUserDetailsUpdateApi();
//
//
//            });


//            ProfileFragmentHttpURLConnection/Edit:------

//            holder.iv_edit_yourAddress.setOnClickListener(v -> {
//                GetUserData getUserData = new GetUserData(new GetUserData.OnGetUserDataFetchedListener() {
//
//                    @Override
//                    public void onUserDataFetched() {
//
//                        try {
////                            MyAddresses.this.runOnUiThread(() -> {
////                                iv_edit_yourAddress.setClickable(true);
////                                showDialog();
////
////                            });
//                        } catch (Exception ignore) {
////                            MyAddresses.this.runOnUiThread(() -> {
////                                iv_edit_yourAddress.setClickable(true);
////
////                            });
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onUserDataFetchError(String error) {
////                        MyAddresses.this.runOnUiThread(() -> {
////                            try {
////                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
////                                iv_edit_yourAddress.setClickable(true);
////
////
////                            } catch (Exception ignore) {
////
////                            }
////                        });
//
//                    }
//                });
//                getUserData.HitGetUserDataApi();
//
//                iv_edit_yourAddress.setClickable(false);
//
//
//            });


            //Add Address:---
//            holder.iv_edit_yourAddress.setOnClickListener(v -> {
//
//                // Create an AlertDialog Builder
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Edit Address");
//
////                final Dialog dialogView = new Dialog(context);
////                dialogView.setContentView(R.layout.dialog_edit_address);
////                dialogView.setCancelable(true);
//
//                // Set the custom layout for the dialog
////                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
//                dialogView = inflater.inflate(R.layout.dialog_edit_address, null);
//                builder.setView(dialogView);
//
//                EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
//                stateEditText = dialogView.findViewById(R.id.editTextState_edit);
//                cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
//                EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
//                EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
//                EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
//                CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);
//
//
//                // Add click listener to the Submit button
//                nameEditText.setText(AddressUserDetails.name);
//                stateEditText.setText(AddressUserDetails.state);
//                cityEditText.setText(AddressUserDetails.city);
//                countryEditText.setText(AddressUserDetails.country);
//                addressEditText.setText(AddressUserDetails.address);
//                postalCodeEditText.setText(AddressUserDetails.postal_code);
//                defaultCheckBox.setText(AddressUserDetails.is_default);
//
//
//                // Set the positive and negative buttons
//                builder.setPositiveButton("Save", null); // Set null initially
//
//                builder.setNegativeButton("Cancel", (dialog, which) -> {
//                    // Handle the cancel button click
//                    // Dismiss the dialog
//                    dialog.dismiss();
//                });
//
//                // Create and show the dialog
//                AlertDialog dialog = builder.create();
//
//                // Override the onClickListener for the positive button
//                dialog.setOnShowListener(dialogInterface -> {
//                    Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                    saveButton.setOnClickListener(view -> {
//
//                        stateEditText.setError(null);
//                        cityEditText.setError(null);
//
//
//                        // Handle the save button click
//                        String name = nameEditText.getText().toString();
//
//                        String state = stateEditText.getText().toString();
////                    String state = String.valueOf(stateEditText.getSelectedIndex() + 1);
//
//                        String city = cityEditText.getText().toString();
////                    String city = String.valueOf(cityEditText.getSelectedIndex() + 1);
//
//                        String country = countryEditText.getText().toString();
//                        String address = addressEditText.getText().toString();
//                        String postalCode = postalCodeEditText.getText().toString();
//                        boolean isDefault = defaultCheckBox.isChecked();
//
//
//                        // Check if any of the fields are empty
//                        boolean hasError = false;
//                        if (name.isEmpty()) {
//                            nameEditText.setError("Name is required");
//                            hasError = true;
//                        }
//                        if (state.isEmpty()) {
//                            stateEditText.setError("State is required");
////                        ObjectAnimator.ofFloat(state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
////                        state.requestFocus();
//                            hasError = true;
//                        }
//                        if (city.isEmpty()) {
//                            cityEditText.setError("City is required");
//                            hasError = true;
//                        }
//                        if (country.isEmpty()) {
//                            countryEditText.setError("Country is required");
//                            hasError = true;
//                        }
//                        if (address.isEmpty()) {
//                            addressEditText.setError("Address is required");
//                            hasError = true;
//                        }
//                        if (postalCode.isEmpty()) {
//                            postalCodeEditText.setError("Postal Code is required");
//                            hasError = true;
//                        }
//
//                        if (!hasError) {
//                            // All fields are properly filled, perform the save operation
////                            SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, city, state, country, address, postalCode, boolToInt(isDefault));
////                            SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode);
////                            UserDetailsUpdate userDetailsUpdates = new UserDetailsUpdate(this, name , email , city , state , district);
//
////                            saveDeliveryAddressDetails.HitApi();
////                            userDetailsUpdates.HitUserDetailsUpdateApi();
//                            Loading.show(context);
//                            // Dismiss the dialog
//                            dialog.dismiss();
//                        } else {
//
//                            UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(
//
////                                nameEditText.getText().toString(), stateEditText.getText().toString(), cityEditText.getText().toString(), countryEditText.getText().toString(), addressEditText.getText().toString(),postalCodeEditText.getText().toString(),
//                                    nameEditText.getText().toString(),
//                                    stateEditText.getText().toString(),
//                                    cityEditText.getText().toString(),
//                                    countryEditText.getText().toString(),
//                                    addressEditText.getText().toString(),
//
//                                    new UserDetailsUpdate.OnUserDetailsUpdateListener() {
//
//                                        @Override
//                                        public void onUserDetailsUpdate(String messaage) {
//
////                                            MyAddressesAdaptor.this.runOnUiThread(() -> {
////                                                try {
////                                                    Variables.name = nameEditText.getText().toString();
//////                                                myAddresses_Name.setText(Variables.name);
////
//////                                                    email = stateEditText.getText().toString();
////                                                    Variables.address = addressEditText.getText().toString();
////
//////                                                txt_personal_my_address.setText(email);
////
////                                                    Toast.makeText(context, messaage, Toast.LENGTH_SHORT).show();
////                                                    dialog.dismiss();
////                                                    Loading.hide();
////                                                } catch (Exception ignore) {
////                                                    Loading.hide();
////                                                }
////
////                                            });
//
//                                        }
//
//                                        @Override
//                                        public void onUserDetailsError(String error) {
//
////                                            context.runOnUiThread(() -> {
////                                                try {
////                                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
////                                                    dialog.dismiss();
////                                                    Loading.hide();
////                                                } catch (Exception ignore) {
////                                                    Loading.hide();
////                                                }
////                                            });
//
//                                        }
//                                    });
//
//                            userDetailsUpdate.HitUserDetailsUpdateApi();
//                            Loading.show(context);
//                        }
//
//                    });
//                });
//
//                dialog.show();
//            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }


    private void showDialog() {
//        iv_edit_yourAddress.setOnClickListener(v -> {

//            final Dialog dialog = new Dialog(MyAddresses.this);
//            dialog.setContentView(R.layout.dialog_edit_address);
//            dialog.setCancelable(true);
        final Dialog dialogView = new Dialog(context);
        dialogView.setContentView(R.layout.dialog_edit_address);
        dialogView.setCancelable(true);

        // Set the dialog's window width to match_parent
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialogView.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogView.getWindow().setAttributes(layoutParams);

//        // Create an AlertDialog Builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
//        builder.setTitle("Edit New Address");

//        // Set the custom layout for the dialog
//        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_address, null);
//        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
        stateEditText = dialogView.findViewById(R.id.editTextState_edit);
        cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
        EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
        EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
        EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
        CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);

        Button buttonSubmit = dialogView.findViewById(R.id.edit_address_btn_save);

        // Add click listener to the Submit button
        nameEditText.setText(AddressUserDetails.name);
        stateEditText.setText(AddressUserDetails.state);
        cityEditText.setText(AddressUserDetails.city);
        countryEditText.setText(AddressUserDetails.country);
        addressEditText.setText(AddressUserDetails.address);
        postalCodeEditText.setText(AddressUserDetails.postal_code);
        defaultCheckBox.setText(AddressUserDetails.is_default);


//        // Set the positive and negative buttons
//        builder.setPositiveButton("Save", null); // Set null initially
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> {
//            // Handle the cancel button click
//            // Dismiss the dialog
//            dialog.dismiss();
//        });


        // Create and show the dialog
//        AlertDialog dialog = builder.create();

        // Override the onClickListener for the positive button
//        dialog.setOnShowListener(dialogInterface -> {
//            Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        buttonSubmit.setOnClickListener(view -> {
//
//            stateEditText.setError(null);
//            cityEditText.setError(null);
//
//
//            // Handle the save button click
//            String name = nameEditText.getText().toString();
//
//            String state = stateEditText.getText().toString();
////          String state = String.valueOf(stateEditText.getSelectedIndex() + 1);
//
//            String city = cityEditText.getText().toString();
////          String city = String.valueOf(cityEditText.getSelectedIndex() + 1);
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
//            if (state.isEmpty()) {
//                stateEditText.setError("State is required");
////                        ObjectAnimator.ofFloat(state, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
////                        state.requestFocus();
//                hasError = true;
//            }
//            if (city.isEmpty()) {
//                cityEditText.setError("City is required");
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
//            if (!hasError) {
//                // All fields are properly filled, perform the save operation
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));
////                SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode);
//
////                saveDeliveryAddressDetails.HitApi();
//                Loading.show(context);
//                // Dismiss the dialog
////                dialog.dismiss();
//                return;
//            } else {
//
//                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(
//
////                                nameEditText.getText().toString(), stateEditText.getText().toString(), cityEditText.getText().toString(), countryEditText.getText().toString(), addressEditText.getText().toString(),postalCodeEditText.getText().toString(),
//                        nameEditText.getText().toString(),
//                        stateEditText.getText().toString(),
//                        cityEditText.getText().toString(),
//                        countryEditText.getText().toString(),
//                        addressEditText.getText().toString(),
//                        //Postalcode
//
//                        new UserDetailsUpdate.OnUserDetailsUpdateListener() {
//
//                            @Override
//                            public void onUserDetailsUpdate(String messaage) {
//
//                                MyAddresses.this.runOnUiThread(() -> {
//                                    try {
//                                        Variables.name = nameEditText.getText().toString();
////                                        myAddresses_Name.setText(Variables.name);
////                                        myAddresses_personal_address_recyclerview_List.setText(Variables.name);
////                                        myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
////                                        myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
////                                        myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(this));
//
//                                        Variables.address = addressEditText.getText().toString();
////                                                txt_personal_my_address.setText(address);
////                                        myAddresses_personal_address_recyclerview_List.setText(address);
//
//                                        Toast.makeText(context, messaage, Toast.LENGTH_SHORT).show();
////                                        dialog.dismiss();
//                                        Loading.hide();
//                                    } catch (Exception ignore) {
//                                        Loading.hide();
//                                    }
//
//                                });
//
//                            }
//
//                            @Override
//                            public void onUserDetailsError(String error) {
//
//                                MyAddresses.this.runOnUiThread(() -> {
//                                    try {
//                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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
//                userDetailsUpdate.HitUserDetailsUpdateApi();
//                Loading.show(context);
//            }
//
//        });
//    });

//        dialog.show();
    }


//    public int boolToInt(int value) {
//        return value ? 1 : 0;
//    }


    @Override
    public int getItemCount() {
        return addressModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtAddress;

        TextView txt_personal_my_address_state, txt_personal_my_address_city;

        EditText cityEditText, stateEditText, addressEditText, postalCodeEditText, cityEditText_edit, stateEditText_edit;

        ImageView iv_edit_yourAddress;

        AppCompatButton txtRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.myAddresses_Name);
            txtAddress = itemView.findViewById(R.id.txt_personal_my_address);
            txt_personal_my_address_state = itemView.findViewById(R.id.txt_personal_my_address_state);
            txt_personal_my_address_city = itemView.findViewById(R.id.txt_personal_my_address_city);

            iv_edit_yourAddress = itemView.findViewById(R.id.iv_edit_yourAddress);
            txtRemove = itemView.findViewById(R.id.txt_remove);
        }
    }
}
