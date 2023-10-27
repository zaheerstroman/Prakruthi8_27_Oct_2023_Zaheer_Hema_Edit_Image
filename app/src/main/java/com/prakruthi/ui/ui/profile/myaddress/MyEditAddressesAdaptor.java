package com.prakruthi.ui.ui.profile.myaddress;

import static com.prakruthi.ui.Variables.isDefault;

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

public class MyEditAddressesAdaptor extends RecyclerView.Adapter<MyEditAddressesAdaptor.ViewHolder> {

    String id;

    ImageView iv_edit_yourAddress;

    //    private List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList;
    private ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();
//    private ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressModelsList = new ArrayList<>();



    public Context context;

    EditText cityEditText, stateEditText, addressEditText, postalCodeEditText, cityEditText_edit, stateEditText_edit;

    View dialogView;
    private List<String> data;

    DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner;

    GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner getMyDeliveryAddressDetailsDataFetchedListner;

    GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener getMyDeliveryAddressDetailsFetchedListener;
    SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner;

//    public MyAddressesAdaptor(List<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner) {

    //        public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner) {
//    public MyEditAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener getMyDeliveryAddressDetailsFetchedListener) {
    public MyEditAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner getMyDeliveryAddressDetailsDataFetchedListner) {

//    public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, SaveDeliveryAddressDetailsEditUpdate.OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner saveDeliveryAddressDetailsEditUpdateListner, Context context) {
//    public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner, GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener getMyDeliveryAddressDetailsFetchedListener, Context context) {

        this.addressModelsList.clear();

        this.addressModelsList = addressModelsList;
        this.deleteDeliveryAddressListner = deleteDeliveryAddressListner;

        this.getMyDeliveryAddressDetailsFetchedListener = getMyDeliveryAddressDetailsFetchedListener;
        this.getMyDeliveryAddressDetailsDataFetchedListner = getMyDeliveryAddressDetailsDataFetchedListner;

        this.saveDeliveryAddressDetailsEditUpdateListner = saveDeliveryAddressDetailsEditUpdateListner;

//        this.inflater = inflater;
//        this.context = context;
    }

    @NonNull
    @Override
    public MyEditAddressesAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_myaddress_layout_edit, parent, false);
        return new MyEditAddressesAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEditAddressesAdaptor.ViewHolder holder, int position) {
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

                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(saveDeliveryAddressDetailsEditUpdateListner, addressModel.getName(),addressModel.getAddress(),addressModel.getCity(),addressModel.getState(),String.valueOf(addressModel.getId()),addressModel.getAddress(),addressModel.getPostal(),isDefault);
                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();

//                GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(getMyDeliveryAddressDetailsFetchedListener);
//                getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();

//                SaveDeliveryAddressDetailsEditUpdate saveDeliveryAddressDetailsEditUpdate = new SaveDeliveryAddressDetailsEditUpdate(saveDeliveryAddressDetailsEditUpdateListner, name, city, state, id, country, address, postalCode,boolToInt(isDefault));
//                saveDeliveryAddressDetailsEditUpdate.HitSaveDeliveryAddressDetailsEditUpdateApiApi();

                GetMyDeliveryAddressDetailsAsyncTask getMyDeliveryAddressDetailsAsyncTask = new GetMyDeliveryAddressDetailsAsyncTask(getMyDeliveryAddressDetailsDataFetchedListner, this.toString());
                getMyDeliveryAddressDetailsAsyncTask.HitMyDeliveryAddressDetailsApi();

                GetMyDeliveryAddressDetails getMyDeliveryAddressDetails = new GetMyDeliveryAddressDetails(getMyDeliveryAddressDetailsFetchedListener, this.toString());
                getMyDeliveryAddressDetails.HitMyDeliveryAddressDetailsApi();

            });




        } catch (Exception e) {
            e.printStackTrace();
        }

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

            txtName = itemView.findViewById(R.id.myAddresses_Name_edit);
            txtAddress = itemView.findViewById(R.id.txt_personal_my_address_edit);
            txt_personal_my_address_state = itemView.findViewById(R.id.txt_personal_my_address_state_edit);
            txt_personal_my_address_city = itemView.findViewById(R.id.txt_personal_my_address_city_edit);

            iv_edit_yourAddress = itemView.findViewById(R.id.iv_edit_yourAddress_edit);
            txtRemove = itemView.findViewById(R.id.txt_remove_edit);
        }
    }
}
