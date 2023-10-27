package com.prakruthi.ui.ui.profile;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.prakruthi.ui.Utility.Constants.REQUEST_GALLERY_PHOTO;
import static com.prakruthi.ui.Utility.Constants.REQUEST_TAKE_PHOTO;
import static com.prakruthi.ui.Variables.city;
//import static com.prakruthi.ui.Variables.departmentName;
//import static com.prakruthi.ui.Variables.department_name;
import static com.prakruthi.ui.Variables.country;
import static com.prakruthi.ui.Variables.description;
import static com.prakruthi.ui.Variables.district;
import static com.prakruthi.ui.Variables.email;
import static com.prakruthi.ui.Variables.id;
import static com.prakruthi.ui.Variables.message;
import static com.prakruthi.ui.Variables.name;
import static com.prakruthi.ui.Variables.state;
import static com.prakruthi.ui.Variables.token;
import static com.prakruthi.ui.Variables.userId;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentProfileHttpURLConnectionBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetRecentViewProductsAPI;
import com.prakruthi.ui.APIs.GetUserData;
import com.prakruthi.ui.APIs.GetUserDataApi;
import com.prakruthi.ui.APIs.UserDetailsUpdate;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetails;

import com.prakruthi.ui.APIs.UserDetailsUpdateApi;
import com.prakruthi.ui.Api.API_class;
import com.prakruthi.ui.Api.Retrofit_funtion_class;
import com.prakruthi.ui.Login;
import com.prakruthi.ui.SplashScreen;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.network.APIClient;
import com.prakruthi.ui.network.ApiInterface;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.WebViewActivityPayment;
import com.prakruthi.ui.ui.WebView_Verification_AnimationRetrofit;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersActivity;
import com.prakruthi.ui.ui.profile.myorders.TrackOrderActivity;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsActivity;
import com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.prakruthi.ui.ui.profile.response.BaseResponseGasaverTProperty;
import com.prakruthi.ui.utils.CommonUtils;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.FilePathUtils;
import com.prakruthi.ui.utils.SharedPrefs;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit, GetUserDataApi.OnGetUserDataApiHitFetchedListner, UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner {
//public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit, GetUserDataApi.OnGetUserDataApiHitFetchedListner, UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner {
public class ProfileFragmentHttpURLConnection extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit {

    ShapeableImageView iv_profile_img, ivProfileImg_Profile,
            iv_profile_Edit, ivProfileEdit;

    private Uri imageUri;

    private String selectedPath = " ";

    private DismissListener dismissListener;


    //    public int id;
    public static int id;
    public static String token;
    public static String departmentName;

    ProgressDialog progressDoalog;

    //    String description, message;
    public static String description, message;


    Dialog dialog;

    GetUserDataApi.OnGetUserDataApiHitFetchedListner mListner;

    AppCompatButton sendotp, backbtn, profile_back_btn;

    ArrayList<String> districtNames = new ArrayList<>();

    PowerSpinnerView spinner_city, editTextState, editTextDistrict;


    String stateId;

    int stateIndex = 0;


    public ShimmerRecyclerView myAddresses_personal_address_recyclerview_List;
    private FragmentProfileHttpURLConnectionBinding binding;
    public SharedPreferences sharedPreferences;

    AppCompatTextView tvRecentProducts, tvViewAll;

    AppCompatButton btn_add_new_address, login_http, Logout_http;

    public YourAdapter yourAdapter;

    ImageView iv_edit_http, iv_close;

    Context context;


    LinearLayout ll_site_visit_req_LastUpdated4_http,
            ll_site_visit_req_LastUpdated5_http;

    //    public ShimmerRecyclerView ProfileHomeProductsRecycler;
    public ShimmerRecyclerView ProfileHomeProductsRecycler_http;

    //    public TextView tvQtyHttp, tvProfileNameHttp, tvEmailHttp, tvPhoneHttp, tvRoleHttp, tvCustomerNameHttp, tvMyOrdersHttp, tvMyAddressHttp, tvMyWishlistHttp, tvPaymentsHttp, tvFeedbackHttp, tvSupportHttp, tvAboutUsHttp, tvTermsConditionsHttp, tvPrivacyPolicyHttp, tvRRPHttp, tvSecurityHttp, tv_layout_sharit_contaced;
    TextView tvProfileNameHttp, tvEmailHttp, tvPhoneHttp, tvRoleHttp, tvDepartmentName,
            tvMyOrdersHttp, tvMyAddressHttp, tvMyWishlistHttp, tvQtyHttp, tvPaymentsHttp,
            tvFeedbackHttp, tvSupportHttp, tvAboutUsHttp, tvTermsConditionsHttp, tvPrivacyPolicyHttp,
            tvRRPHttp, tvSecurityHttp, tv_layout_sharit_contaced;

//    public AppCompatTextView tvCustomerName;

    private ProfileGetUserDataResponse responseProfile_http;
//    private ProfileGetUserDataResponse.ProfileGetUserDataModel responseProfile_http;


    private ProfileGetUserDataResponseHttpURLConnectionAll responseProfileHttpUTLConnection;

    public EditProfileFragmentHttpURLConnection editProfileFragmentHttpURLConnection;


    public ProfileFragmentHttpURLConnection() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_http_u_r_l_connection, container, false);

//        setContentView(binding.getRoot());

//        responseProfile_http = new Gson().fromJson(getActivity().getStringExtra("data"), ProfileUserDataResponseGasaverT.Data.class);
//                responseProfile_http = new Gson().fromJson(requireContext().getStringExtra("data"), ProfileGetUserDataResponse.class);

        Bundle args = getArguments();

        // Check if the intent has the data you need
        if (getArguments() != null) {
            String jsonData = getArguments().getString("data");

            // Parse the JSON data using Gson
//            responseProfile_http = new Gson().fromJson(jsonData, ProfileGetUserDataResponse.ProfileGetUserDataModel.class);
            responseProfile_http = new Gson().fromJson(jsonData, ProfileGetUserDataResponse.class);

            // Now you can access the data object as needed
            if (responseProfile_http != null) {
                // Access data properties, e.g., data.someProperty
            }

        }

        //ProfileFragmentHttpURLConnection profileFragmentHttpURLConnection = new ProfileFragmentHttpURLConnection();
        //Bundle args = new Bundle();
        //args.putString("data", yourJsonStringHere);
        //profileFragmentHttpURLConnection.setArguments(args);
        //
        //getSupportFragmentManager().beginTransaction()
        //    .replace(R.id.fragment_container, profileFragmentHttpURLConnection)
        //    .commit();

//        description=getIntent().getStringExtra("description");
//        String description = getIntent().getStringExtra("description");


        profile_back_btn = rootView.findViewById(R.id.profile_back_btn);

        iv_edit_http = rootView.findViewById(R.id.iv_edit_http);
        tvQtyHttp = rootView.findViewById(R.id.tv_qty_http);

        ivProfileImg_Profile = rootView.findViewById(R.id.iv_profile_img);

        tvProfileNameHttp = rootView.findViewById(R.id.tv_profile_name_http);
        tvEmailHttp = rootView.findViewById(R.id.tv_email_http);
        tvPhoneHttp = rootView.findViewById(R.id.tv_phone_http);
        tvRoleHttp = rootView.findViewById(R.id.tv_role_http);
        tvDepartmentName = rootView.findViewById(R.id.tv_department_name);

//        tv_customer_name = rootView.findViewById(R.id.tv_customer_name);
//        tvCustomerNameHttp = rootView.findViewById(R.id.tvCustomerNameHttp);


        Logout_http = rootView.findViewById(R.id.Logout_http);

        tvMyOrdersHttp = rootView.findViewById(R.id.tv_my_orders_http);

        tvMyAddressHttp = rootView.findViewById(R.id.tv_my_address_http);

        tvMyWishlistHttp = rootView.findViewById(R.id.tv_my_wishlist_http);

        tvPaymentsHttp = rootView.findViewById(R.id.tv_payments_http);

        tvFeedbackHttp = rootView.findViewById(R.id.tv_feedback_http);

        tvSupportHttp = rootView.findViewById(R.id.tv_support_http);

        tvAboutUsHttp = rootView.findViewById(R.id.tv_About_us_http);

        tvPrivacyPolicyHttp = rootView.findViewById(R.id.tv_privacy_policy_http);

        tvTermsConditionsHttp = rootView.findViewById(R.id.tv_Terms_Conditions_http);

        tvRRPHttp = rootView.findViewById(R.id.tv_returnRefundPolicy_http);

        tvSecurityHttp = rootView.findViewById(R.id.tv_security_http);

        tv_layout_sharit_contaced = rootView.findViewById(R.id.tv_layout_sharit_contaced);

        Logout_http = rootView.findViewById(R.id.Logout_http);

        ProfileHomeProductsRecycler_http = rootView.findViewById(R.id.ProfileHomeProductsRecycler_http);
        ProfileHomeProductsRecycler_http.showShimmerAdapter();

        iv_close = rootView.findViewById(R.id.iv_close);

        backbtn = rootView.findViewById(R.id.backbtn);

        editTextState = rootView.findViewById(R.id.editTextState);

        editTextDistrict = rootView.findViewById(R.id.editTextDistrict);


//        setdata();
        SetTextViews();
//        SetClickListeners();
        UserDetailsUpdateApi();

//        showFeedbackDilog();


        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();


        sharedPreferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        SetTextViews();
//        SetClickListeners();


        //---------------------------------------------------------------------------------

        profile_back_btn.setOnClickListener(v -> requireActivity().onBackPressed());

        iv_edit_http.setOnClickListener(v -> {
            GetUserData getUserData = new GetUserData(new GetUserData.OnGetUserDataFetchedListener() {

                @Override
                public void onUserDataFetched() {

                    try {
                        requireActivity().runOnUiThread(() -> {
                            iv_edit_http.setClickable(true);

                            showDialog();

                        });
                    } catch (Exception ignore) {
                        requireActivity().runOnUiThread(() -> {
                            iv_edit_http.setClickable(true);

                        });

                    }

                }

                @Override
                public void onUserDataFetchError(String error) {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                            iv_edit_http.setClickable(true);


                        } catch (Exception ignore) {

                        }
                    });

                }
            });

            getUserData.HitGetUserDataApi();
            iv_edit_http.setClickable(false);


        });


        Logout_http.setOnClickListener(v ->


        {
            //Gasaver Retrofit
            SharedPrefs.getInstance(requireContext()).clearSharedPrefs();

            //
            Variables.clear();
            // Get SharedPreferences.Editor object
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberMe", false);
            // Apply changes
            editor.apply();
            startActivity(new Intent(requireContext(), Login.class));
            requireActivity().finish();

            //Gasaver
            Intent intent1 = new Intent(requireContext(), Login.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent1);
        });

        tvMyOrdersHttp.setOnClickListener(v ->


                startActivity(new Intent(requireContext(), MyOrdersActivity.class)));
//                        startActivity(new Intent(requireContext(), TrackOrderActivity.class))

//        );

        if (tvMyAddressHttp != null) {
            tvMyAddressHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), MyAddresses.class));
                }
            });
        }

        if (Variables.departmentId != 2) {
//            tvMyWishlistHttp.setText("quantity");
            tvQtyHttp.setVisibility(View.VISIBLE);
            tvMyWishlistHttp.setVisibility(View.GONE);
//            Drawable top = getResources().getDrawable(R.drawable.baseline_warehouse_24);
//            tvMyWishlistHttp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.quantity_icon1, 0, 0, 0);
//            Drawable top = getResources().getDrawable(R.drawable.quantity_icon1);
//            tvMyWishlistHttp.setCompoundDrawables(null, top, null, null);

            tvQtyHttp.setOnClickListener(v -> {

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });

        } else {
            tvMyWishlistHttp.setVisibility(View.VISIBLE);
            tvQtyHttp.setVisibility(View.GONE);
            tvMyWishlistHttp.setOnClickListener(v -> {

                BottomNavigationView bottomNavigationView;
                bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setSelectedItemId(R.id.navigation_wishlist);

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });
        }

        if (tvPaymentsHttp != null) {
            tvPaymentsHttp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), MyPaymentsActivity.class));
                }
            });
        }

        if (tvFeedbackHttp != null) {
            tvFeedbackHttp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    postFeedbackVinni(dialog,description);
                    showFeedbackDilogVinni();


//                    showFeedbackDilogSri();
//                    FeedBackDialog();
//                    startActivity(new Intent(requireContext(), FeedBackActivity.class));


                }
            });
        }


        if (tvPrivacyPolicyHttp != null) {
            tvPrivacyPolicyHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), PrivacyPolicyActivity.class));
//                    startActivity(new Intent(requireContext(), TrackOrderActivity.class));


                }
            });
        }

        if (tvTermsConditionsHttp != null) {
            tvTermsConditionsHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), TermNConditionWebViewActivity.class));

                }
            });
        }

        if (tvAboutUsHttp != null) {
            tvAboutUsHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), AboutUsWebViewActivity.class));

                }
            });
        }

        if (tvSupportHttp != null) {
            tvSupportHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), SupportActivity.class));
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    startActivity(i);
                }

            });
        }

        if (tvRRPHttp != null) {
            tvRRPHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(requireContext(), ReturnRefundPolicyActivity.class));

                }
            });
        }


        if (tvSecurityHttp != null) {
            tvSecurityHttp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), SecurityActivity.class));

                }
            });
        }


        if (tv_layout_sharit_contaced != null) {
            tv_layout_sharit_contaced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(requireContext(), ShareitActivity.class));

                }
            });
        }

        if (ll_site_visit_req_LastUpdated4_http != null) {
            ll_site_visit_req_LastUpdated4_http.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefs.getInstance(getActivity()).clearSharedPrefs();
                    Intent intent1 = new Intent(getActivity(), SplashScreen.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            });
        }

        if (ll_site_visit_req_LastUpdated5_http != null) {
            ll_site_visit_req_LastUpdated5_http.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefs.getInstance(getActivity()).clearSharedPrefs();
                    Intent intent1 = new Intent(getActivity(), SplashScreen.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent1);
                }
            });
        }

        return rootView;

    }

    //Feedback:Gasaver_Vinni---    //********------Retrofit-Gasaver_Vinni-------@@@@@@@@----------%%%%-------------#######-------------------

    private void showFeedbackDilogVinni() {

        progressDoalog = new ProgressDialog(requireContext());
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        String ContentType = "application/json";
        String Accept = "application/json";

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.feedback_dialog);
        dialog.setCancelable(false);

        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        EditText et_feedback = dialog.findViewById(R.id.et_feedback);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_feedback.getText().toString().trim()))
//                    postFeedback(dialog, et_feedback.getText().toString());
//                    postFeedbackVinni(dialog, et_feedback.getText().toString());
                    postFeedbackVinni(dialog);

                else
                    Toast.makeText(requireContext(), "Please Enter message to submit feedback", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    //Feedback:------    //********------Retrofit-Prakruithi-Sriniwas-------@@@@@@@@----------%%%%-------------#######-------------------
    //-----Sriniwas

    //    FEEDAKRUTHTEST1
    private void showFeedbackDilogSri() {

        //Gasaver
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.feedback_dialog);
        dialog.setCancelable(false);

        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        EditText et_feedback = dialog.findViewById(R.id.et_feedback);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_feedback.getText().toString().trim()))
//                    if (!TextUtils.isEmpty(et_feedback.getText().toString().trim()));

                    postFeedback(dialog, et_feedback.getText().toString());

                else
                    Toast.makeText(requireContext(), "Please Enter message to submit feedback", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //********------Retrofit-Prakruithi-Sriniwas-------@@@@@@@@----------%%%%-------------#######-------------------

        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);
        Call<JsonElement> callRetrofit = null;
        callRetrofit = service.FEEDAKRUTHTEST1(String.valueOf(Variables.id), Variables.token, description);
        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {

//                    progressDoalog.dismiss();

                    String searchResponse = response.body().toString();
                    Log.d("Regisigup", searchResponse);

                    btn_submit.setVisibility(View.GONE);

                    try {
                        try {

                            JSONObject lObj = new JSONObject(searchResponse);
                            String status_code = lObj.getString("status_code");

                            if (status_code.equalsIgnoreCase("true")) {
                                String message = lObj.getString("message");

                                Intent intent = new Intent(requireContext(), ProfileFragmentHttpURLConnection.class);

                                intent.putExtra("message", message);

                                startActivity(intent);

                                btn_submit.setVisibility(View.VISIBLE);


                            } else {

                                String message = lObj.getString("message");

                                if (message.equalsIgnoreCase("Data Saved.")) {
                                    Intent intent = new Intent(requireContext(), ProfileFragmentHttpURLConnection.class);
//                                    Intent intent = new Intent(WebViewActivityPayment.this, SavePaymentDetailsActivity.class);
                                    startActivity(intent);
                                }
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

//                    progressDoalog.dismiss();

                    if (!response.isSuccessful()) {

                        InputStream i = response.errorBody().byteStream();
                        BufferedReader r = new BufferedReader(new InputStreamReader(i));
                        StringBuilder errorResult = new StringBuilder();
                        String line;

                        try {
                            while ((line = r.readLine()) != null) {

                                errorResult.append(line).append('\n');
                                try {
                                    JSONObject jsonObject = new JSONObject(line);
                                    jsonObject.getString("message");
                                    Log.d("lineappende >>>>  ", "lineapends  >>> " + jsonObject.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("line", "line" + errorResult.append("message" + line));
                                Log.d("searchResponse", "searchResponse" + errorResult.append(line).append('\n'));


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());
            }
        });
    }

    //********------Retrofit-Prakruithi-Sriniwas-------@@@@@@@@----------%%%%-------------#######-------------------

    private void postFeedback(Dialog dialog, String description) {

        final API_class service = Retrofit_funtion_class.getClient().create(API_class.class);
        Call<JsonElement> callRetrofit = null;
        callRetrofit = service.FEEDAKRUTHTEST1(String.valueOf(Variables.id), Variables.token, description);
        callRetrofit.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                System.out.println("----------------------------------------------------");
                Log.d("Call request", call.request().toString());
                Log.d("Call request header", call.request().headers().toString());
                Log.d("Response raw header", response.headers().toString());
                Log.d("Response raw", String.valueOf(response.raw().body()));
                Log.d("Response code", String.valueOf(response.code()));

                System.out.println("----------------------------------------------------");

                if (response.isSuccessful()) {

//                    progressDoalog.dismiss();

                    String searchResponse = response.body().toString();
                    Log.d("Feedback", searchResponse);

//                    btn_submit.setVisibility(View.GONE);

//                    FeedBackTResponse feedBackTResponse = response.body();


                    try {
                        try {

                            JSONObject lObj = new JSONObject(searchResponse);
                            String status_code = lObj.getString("status_code");

                            if (status_code.equalsIgnoreCase("true")) {
                                String message = lObj.getString("message");

                                Intent intent = new Intent(requireContext(), ProfileFragmentHttpURLConnection.class);

                                intent.putExtra("message", message);
                                intent.putExtra("description", "Your description data");


                                startActivity(intent);

//                                btn_submit.setVisibility(View.VISIBLE);

                                dialog.dismiss();
//                                Toast.makeText(requireContext(), feedBackTResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(requireContext(), "Data Saved.", Toast.LENGTH_SHORT).show();


                            } else {

                                String message = lObj.getString("message");

                                if (message.equalsIgnoreCase("Data Saved.")) {
                                    Intent intent = new Intent(requireContext(), ProfileFragmentHttpURLConnection.class);
//                                    Intent intent = new Intent(WebViewActivityPayment.this, SavePaymentDetailsActivity.class);
                                    startActivity(intent);
                                }

                                dialog.dismiss();

                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

//                    progressDoalog.dismiss();

                    if (!response.isSuccessful()) {

                        InputStream i = response.errorBody().byteStream();
                        BufferedReader r = new BufferedReader(new InputStreamReader(i));
                        StringBuilder errorResult = new StringBuilder();
                        String line;

                        try {
                            while ((line = r.readLine()) != null) {

                                errorResult.append(line).append('\n');
                                try {
                                    JSONObject jsonObject = new JSONObject(line);
                                    jsonObject.getString("message");
                                    Log.d("lineappende >>>>  ", "lineapends  >>> " + jsonObject.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("line", "line" + errorResult.append("message" + line));
                                Log.d("searchResponse", "searchResponse" + errorResult.append(line).append('\n'));


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("Error Call", ">>>>" + call.toString());
                Log.d("Error", ">>>>" + t.toString());
                Toast.makeText(requireContext(), "Something went wrong. Please Try again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //------------------------------------------------------------------------------------------------


    //    private void postFeedback(Dialog dialog, String des) {
//    private void postFeedback(Dialog dialog, String msg) {

    //********------Retrofit-Gasaver-Vinni-------@@@@@@@@----------%%%%-------------#######-------------------

    //    private void postFeedbackVinni(Dialog dialog, String description) {
    private void postFeedbackVinni(Dialog dialog) {


//    private void postFeedbackVinni(Dialog dialog, String message) {
//    private void postFeedbackToApi(String description, String name, String email, String mobile, int userId, String token) {
//    private void postFeedbackToApi(String message, String name, String email, String phone, int userId, String token) {


//-----

        CommonUtils.showLoading(requireContext(), "Please Wait", false);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();

//        // Debugging: Check the values you're trying to send
//        String name = SharedPrefs.getInstance(requireContext()).getString(Constants.USER_NAME);
//        String email = SharedPrefs.getInstance(requireContext()).getString(Constants.USER_EMAIL);
////        String phone = SharedPrefs.getInstance(requireContext()).getString(Constants.USER_MOBILE);
//        String mobile = SharedPrefs.getInstance(requireContext()).getString(Constants.USER_MOBILE);
//
////        String userId = SharedPrefs.getInstance(requireContext()).getString(String.valueOf(Constants.ID));
//        String userId = SharedPrefs.getInstance(requireContext()).getString(String.valueOf(Constants.USER_ID));
//
//        String token = SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN);
//
//        String message = SharedPrefs.getInstance(requireContext()).getString(Constants.MESSAGE);
////        String description = SharedPrefs.getInstance(requireContext()).getString(Constants.MESSAGE);
//
//        Log.d("Debug", "name: " + name);
//        Log.d("Debug", "email: " + email);
//        Log.d("Debug", "mobile: " + mobile);
//        Log.d("Debug", "user_id: " + userId);
//        Log.d("Debug", "token: " + token);
//        Log.d("Debug", "message: " + message);
//
//
//        jsonObject.addProperty("name", name);
//        jsonObject.addProperty("email", email);
//        jsonObject.addProperty("mobile", mobile);
//        jsonObject.addProperty("user_id", userId);
//        jsonObject.addProperty("token", token);
//        jsonObject.addProperty("message", message);

        //------

        jsonObject.addProperty("post_type", "feedback");


//        jsonObject.addProperty("name", SharedPrefs.getInstance(requireContext()).getString(Variables.USER_NAME));
//        jsonObject.addProperty("name", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_NAME));

//        jsonObject.addProperty("email", SharedPrefs.getInstance(requireContext()).getString(Variables.USER_EMAIL));
//        jsonObject.addProperty("email", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_EMAIL));

//        jsonObject.addProperty("phone", SharedPrefs.getInstance(requireContext()).getString(Variables.USER_MOBILE));
//        jsonObject.addProperty("phone", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_MOBILE));


//        jsonObject.addProperty("name", SharedPrefs.getInstance(this).getString(Constants.USER_NAME));
//        jsonObject.addProperty("email", SharedPrefs.getInstance(this).getString(Constants.USER_EMAIL));
//
//        jsonObject.addProperty("phone", SharedPrefs.getInstance(this).getString(Constants.USER_MOBILE));
//        jsonObject.addProperty("fcm_token", SharedPrefs.getInstance(this).getString(Constants.FCM_TOKEN));

//        jsonObject.addProperty("id", SharedPrefs.getInstance(this).getString(Constants.ID));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(userId));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(id)));
//        jsonObject.addProperty("id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(id)));
//        jsonObject.addProperty("id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(Variables.id)));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(Variables.id)));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(Constants.ID)));

        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID));
//        jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(id)));
//        jsonObject.addProperty("id", SharedPrefs.getInstance(requireContext()).getString(String.valueOf(id)));


//        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN));
        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(token));
//        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(Variables.token));
//        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN));

//        jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(token));

//        jsonObject.addProperty("description", SharedPrefs.getInstance(requireContext()).getString(Variables.description));
//        jsonObject.addProperty("message", msg);
        jsonObject.addProperty("message", message);

//        jsonObject.addProperty("description", msg);
//        jsonObject.addProperty("description", des);
//        jsonObject.addProperty("description", description);


//        Call<FeedBackTResponse> call = apiInterface.fetchfeedBack(jsonObject);
        Call<FeedBackTResponse> call = apiInterface.fetchfeedBack(jsonObject);


        call.enqueue(new Callback<FeedBackTResponse>() {
            @Override
            public void onResponse(Call<FeedBackTResponse> call, Response<FeedBackTResponse> response) {
                FeedBackTResponse feedBackTResponse = response.body();
                if (feedBackTResponse != null && feedBackTResponse.isStatusCode()) {

                    // Data saved successfully
                    String message = feedBackTResponse.getMessage();

                    dialog.dismiss();
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(requireContext(), feedBackTResponse.message, Toast.LENGTH_SHORT).show();
                    Toast.makeText(requireContext(), feedBackTResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(requireContext(), "Data Saved.", Toast.LENGTH_SHORT).show();


                } else {
                    // Handle API response with status_code=false
                    String errorMessage = feedBackTResponse != null ? feedBackTResponse.getMessage() : "Unknown Error";
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();

                    // Handle the error message as needed
                }
                CommonUtils.hideLoading();
            }

            @Override
            public void onFailure(Call<FeedBackTResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Something went wrong. Please Try again", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                CommonUtils.hideLoading();
            }
        });


    }


    //    EditProfile:-------------------------
    private void showDialog() {


        // Create a custom dialog
        final Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.profile_edit_dialog);
        dialog.setCancelable(true);


        // Set the dialog's window width to match_parent
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        ImageView iv_close = dialog.findViewById(R.id.iv_close);

        ivProfileEdit = dialog.findViewById(R.id.iv_edit_profile);

        // Get references to the UI elements in the custom dialog
        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextEmail = dialog.findViewById(R.id.editTextEmail);
        EditText editTextCity = dialog.findViewById(R.id.editTextCity);


        editTextState = dialog.findViewById(R.id.editTextState);
        editTextDistrict = dialog.findViewById(R.id.editTextDistrict);

        Button buttonSubmit = dialog.findViewById(R.id.btn_save);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ivProfileEdit.setOnClickListener(new View.OnClickListener() {

            //        iv_profile_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        SetTextViews();

        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            stateId = newItem;
            getDropDownData(editTextState, editTextDistrict);
            editTextDistrict.setText("District");
        });

        getDropDownData(editTextState, editTextDistrict);
        // Add click listener to the Submit button
        editTextName.setText(UserDetails.name);
        editTextEmail.setText(UserDetails.email);
        editTextCity.setText(UserDetails.city);
        editTextState.setText(UserDetails.state);
        editTextDistrict.setText(UserDetails.district);


        buttonSubmit.setOnClickListener(v -> {

            editTextState.setError(null);
            editTextDistrict.setError(null);

            // Check if any of the EditText fields are empty
            boolean hasEmptyFields = false;
            if (TextUtils.isEmpty(editTextName.getText())) {
                editTextName.setError("Name cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextEmail.getText())) {
                editTextEmail.setError("Email cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextCity.getText())) {
                editTextCity.setError("City cannot be empty");
                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextState.getText())) {
                editTextState.setError("State cannot be empty");

                ObjectAnimator.ofFloat(editTextState, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                editTextState.requestFocus();

                hasEmptyFields = true;
            }
            if (TextUtils.isEmpty(editTextDistrict.getText())) {
                editTextDistrict.setError("District cannot be empty");

                ObjectAnimator.ofFloat(editTextDistrict, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                editTextDistrict.requestFocus();

                hasEmptyFields = true;
            }



//            Glide.with(requireContext())
//                    .load(responseProfile_http.getData().getAttachment())
//                    .error(R.drawable.profile_img)
//                    .into(ivProfileImg_Profile);

//            Glide.with(ProfileFragmentHttpURLConnection.this)
//                    .load(responseProfile_http.getData().getAttachment())
//                    .error(R.drawable.profile_img)
//                    .into(ivProfileEdit);



            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID))
                    .addFormDataPart("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN)).build();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID));
            jsonObject.addProperty("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN));




            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            builder
//                    .addFormDataPart("fcm_token", SharedPrefs.getInstance(requireContext()).getString(Constants.FCM_TOKEN))
                    .addFormDataPart("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID))
                    .addFormDataPart("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN))
                    .addFormDataPart("name", editTextName.getText().toString())
//                    .addFormDataPart("dob", binding.etDob.getText().toString())
//                    .addFormDataPart("gender", gender)
                    .addFormDataPart("email", editTextEmail.getText().toString())
                    .addFormDataPart("city", editTextCity.getText().toString())
                    .addFormDataPart("state", String.valueOf(editTextState))
                    .addFormDataPart("district", String.valueOf(editTextDistrict))
                    .addFormDataPart("country", String.valueOf(editTextCity))
//                    .addFormDataPart("dob", binding.etDob.getText().toString())
//                    .addFormDataPart("gender", gender)
                    .build();

            if (!selectedPath.trim().isEmpty()) {
                File file = new File(selectedPath);
                RequestBody fbody = RequestBody.create(MediaType.parse("image/*"),
                        file);
                builder.addFormDataPart("file", file.getName(), fbody);
            }

            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

            Call<EditProfileUpdateDrailsUpdateModels> call = apiInterface.userDetailsUpdate(builder.build());
//            call.enqueue(new Callback<EditProfileUpdateDrailsUpdateModels>() {
//                @Override
//                public void onResponse(Call<EditProfileUpdateDrailsUpdateModels> call, Response<EditProfileUpdateDrailsUpdateModels> response) {
//                    CommonUtils.hideLoading();
//                    setResult(RESULT_OK);
//                    if (response.body() != null && response.body().getStatusCode()) {
//                        if (response.body().getStatusCode()) {
//                            Toast.makeText(ProfileFragmentHttpURLConnection.this, "Profile Details Updated successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ProfileFragmentHttpURLConnection.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ProfileFragmentHttpURLConnection> call, Throwable t) {
//                    CommonUtils.hideLoading();
//                    Toast.makeText(ProfileFragmentHttpURLConnection.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//            });


//            try {
//                city = binding.spinnerCity.getSelectedItem().toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                state = binding.editTextState.getSelectedItem().toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                country = binding.spinnerCountry.getSelectedItem().toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // If any EditText field is empty, return without dismissing the dialog
            if (hasEmptyFields) {
                return;
            } else {
                UserDetailsUpdate userDetailsUpdate = new UserDetailsUpdate(

                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCity.getText().toString(),
                        editTextState.getText().toString(),
                        editTextDistrict.getText().toString(),

                        new UserDetailsUpdate.OnUserDetailsUpdateListener() {

                            @Override
                            public void onUserDetailsUpdate(String messaage) {

                                requireActivity().runOnUiThread(() -> {
                                    try {
                                        Variables.name = editTextName.getText().toString();
                                        tvProfileNameHttp.setText(Variables.name);

                                        email = editTextEmail.getText().toString();
                                        tvEmailHttp.setText(email);
//                                         Variables.customer = TextView.getText().toString();
//                                        tvcustomerhttp.setText(cutomer);

//                                        departmentName = editTextName.getText().toString();
//                                        tv_customer_name.setText(Variables.departmentName);

                                        Glide.with(ProfileFragmentHttpURLConnection.this)
                                                .load(responseProfile_http.getData().getAttachment())
                                                .error(R.drawable.profile_img)
                                                .into(ivProfileEdit);


                                        Toast.makeText(requireContext(), messaage, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Loading.hide();
                                    } catch (Exception ignore) {
                                        Loading.hide();
                                    }

                                });

                            }

                            @Override
                            public void onUserDetailsError(String error) {

                                requireActivity().runOnUiThread(() -> {
                                    try {
                                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Loading.hide();
                                    } catch (Exception ignore) {
                                        Loading.hide();
                                    }
                                });

                            }
                        });

                userDetailsUpdate.HitUserDetailsUpdateApi();
                Loading.show(requireContext());
            }

            dialog.dismiss(); // Close the dialog after processing
        });


        dialog.show();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                }
            } else if (items[item].equals("Choose from Library")) {
                if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY_PHOTO);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), REQUEST_GALLERY_PHOTO);
                }
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public interface DismissListener {
        public void onDismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_PHOTO || requestCode == REQUEST_TAKE_PHOTO) {
            Log.e("selected profile img", requestCode + " " + resultCode);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = null;
                    String path = " ";
                    if (requestCode == REQUEST_TAKE_PHOTO) {
                        selectedPath = FilePathUtils.getRealPath(requireContext(), imageUri);

//                        selectedPath = imageUri.getPath();
                        bitmap = BitmapFactory.decodeFile(selectedPath);
                    } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                        selectedPath = FilePathUtils.getRealPath(requireContext(), data.getData());
                        bitmap = BitmapFactory.decodeFile(selectedPath);
                    }
                    Log.e("selected profile img", selectedPath);
//                    iv_profile.setImageBitmap(bitmap);
//                    iv_profile_Edit.setImageBitmap(bitmap);

                    ivProfileEdit.setImageBitmap(bitmap);
//                    ivProfileImg_Profile.setImageBitmap(bitmap);

                    SetTextViews();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TAKE_PHOTO) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //launch camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                imageUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } else {
                Toast.makeText(requireContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_GALLERY_PHOTO) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //launch gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), REQUEST_GALLERY_PHOTO);
            } else {
                Toast.makeText(requireContext(), "storage permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==102&&resultCode==RESULT_OK)
////            fetchProfileDetails();
//            SetTextViews();
//    }

    @SuppressLint("StaticFieldLeak")
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView District) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {

//                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
//                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");
                FetchData fetchData = new FetchData("http://prakruthiepl.com/admin/api/getDropdownData");


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
                            stateNames.add(state.getString("name"));
                        }
                        State.setItems(stateNames);


                        JSONArray districts = jsonObj.getJSONArray("district");
                        districtNames.clear();

//                        ArrayList<String> districtNames = new ArrayList<>();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);

                            if (districtt.getString("state_name").equalsIgnoreCase(stateId)) {

//                                districtNames.add(districtt.getString("name"));
//                                spinner_district.setItems(districtNames);
//                                District.setItems(districtNames);
                                districtNames.add(districtt.getString("name"));

                            }


                        }
                        District.setItems(districtNames);


                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    //HttpURLConnection:---

    public void SetTextViews() {

        tvProfileNameHttp.setText("");
        tvProfileNameHttp.append(String.valueOf(name));


        tvEmailHttp.setText("");
        tvEmailHttp.append(String.valueOf(Variables.email));


        tvPhoneHttp.setText("");
        tvPhoneHttp.append(String.valueOf(Variables.mobile));


        tvRoleHttp.setText("");
        tvRoleHttp.append(String.valueOf(Variables.userCode));


//        tvCustomerNameHttp.setText("");
//        tvCustomerNameHttp.append(String.valueOf(Variables.departmentName));


        tvDepartmentName.setText("");
        tvDepartmentName.append(String.valueOf(Variables.departmentName));

        ProfileHomeProductsRecycler_http.showShimmerAdapter();
        tvTermsConditionsHttp.setSelected(true);

//        Glide.with(requireContext()).load(responseProfile_http.getData().getAttachment())
//                .error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivProfileImg);

        // Load and display the image using Glide
//        Glide.with(requireContext())
//                .load(responseProfile_http.getData().getAttachment())
//                .error(R.drawable.profile_img)
//                .into(ivProfileImg_Profile);

//        Glide.with(requireContext())
//                .load(responseProfile_http.getData().getAttachment())
//                .error(R.drawable.profile_img)
//                .into(ivProfileImg_Profile);

        // Check if responseProfile_http is null or if its data is null
        if (responseProfile_http == null) {
//        if (responseProfile_http == null || responseProfile_http.getData() == null) {
            // Handle the case where responseProfile_http or its data is null
            // You can set default values, show an error message, or perform other actions as needed.

            Glide.with(requireContext())
                    .load(R.drawable.profile_img) // Set your placeholder image resource
                    .into(ivProfileImg_Profile);

//                    Glide.with(requireContext())
//                .load(responseProfile_http.getData().getAttachment())
//                .error(R.drawable.profile_img)
//                .into(ivProfileImg_Profile);

             return; // Exit the method to avoid further processing
        }

        //-----------------------------------------------------------------------

//        if (responseProfile_http != null && responseProfile_http.getData() != null) {
//            // Load and display the image using Glide
//            Glide.with(requireContext())
//                    .load(responseProfile_http.getData().getAttachment())
//                    .error(R.drawable.profile_img)
//                    .into(ivProfileImg_Profile);
//        } else {
//            // Handle the case where responseProfile_http or its data is null
//            // You can set a placeholder image or show an error message here.
//
//            Glide.with(requireContext())
//                    .load(R.drawable.profile_img) // Set your placeholder image resource
//                    .into(ivProfileImg_Profile);
//
////            Glide.with(requireContext())
////                    .load(responseProfile_http.getData().getAttachment())
////                    .error(R.drawable.profile_img)
////                    .into(ivProfileImg_Profile);
//
//            // You can also show an error message to the user, for example:
//            Toast.makeText(requireContext(), "Image data not available", Toast.LENGTH_SHORT).show();
//        }

        //-----------------------------------------------------

        //        URL imageUrl = new URL(response.body().getBase_path() + response.body().getData().getProfilePhoto());

//        responseProfile_http
//        Glide.with(ProfileFragmentHttpURLConnection.this).load(response.body().getBase_path() + response.body().getData().getProfilePhoto())

//        Glide.with(ProfileFragmentHttpURLConnection.this).load(responseProfile_http.getBasePath() + responseProfile_http.getData().getProfilePhoto())
//                .error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivProfileImg);


        ProfileHomeProductsRecycler_http.showShimmerAdapter();
        tvTermsConditionsHttp.setSelected(true);

        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();


        //Retrofit:-------
        if (editProfileFragmentHttpURLConnection != null && editProfileFragmentHttpURLConnection.isVisible()) {
            editProfileFragmentHttpURLConnection.updateDetails(responseProfile_http.getData());

        }


    }

    @SuppressLint("StaticFieldLeak")
    public void ApiTaskProHttp() {
        Loading.show(requireContext());
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "user_id";
                field[1] = "token";

                //Creating array for data
                String[] data = new String[2];
                data[0] = String.valueOf(Variables.id);
                data[1] = token;
                PutData putData = new PutData(Variables.BaseUrl + "getUserData", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        try {
                            // Parse JSON response
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();

                            // Loop through JSON array and create Address objects
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                boolean statusCode = jsonObject.getBoolean("status_code");
                                String message = jsonObject.getString("message");
                                String privacyPolicy = jsonObject.getString("privacyPolicy");
                                String termsAndConditions = jsonObject.getString("termsAndConditions");

                                String id = obj.getString("id");
                                String department_id = obj.getString("department_id");
                                String user_code = obj.getString("user_code");

                                String name = obj.getString("name");
                                String last_name = obj.getString("last_name");
                                String email = obj.getString("email");


                                String password = obj.getString("password");
                                String mobile = obj.getString("mobile");
                                String gender = obj.getString("gender");
                                String dob = obj.getString("dob");
                                String attachment = obj.getString("attachment");
                                String city = obj.getString("city");
                                String postCode = obj.getString("postCode");
                                String address = obj.getString("address");
                                String state = obj.getString("state");
                                String country = obj.getString("country");
                                String district = obj.getString("district");
                                String street = obj.getString("street");
                                String about = obj.getString("about");
                                String status = obj.getString("status");
                                String mobile_verified = obj.getString("mobile_verified");
                                String is_verified = obj.getString("is_verified");
                                String log_date_created = obj.getString("log_date_created");
                                String created_by = obj.getString("created_by");
                                String log_date_modified = obj.getString("log_date_modified");
                                String modified_by = obj.getString("modified_by");
                                String fcm_token = obj.getString("fcm_token");
                                String device_id = obj.getString("device_id");
                                String allow_email = obj.getString("allow_email");
                                String allow_sms = obj.getString("allow_sms");
                                String allow_push = obj.getString("allow_push");
//                                String customer=obj.getString("customer");
//                                String departmentName=obj.getString("departmentName");
                                String departmentName = obj.getString("department_name");


                                profileGetUserDataModels.add(new ProfileGetUserDataResponse.ProfileGetUserDataModel(id, department_id, user_code, name, last_name, email, password, mobile, gender, dob, attachment, city, postCode, address, state, country, district, street, about, status, mobile_verified, is_verified, log_date_created, created_by, log_date_modified, modified_by, fcm_token, device_id, allow_email, allow_sms, allow_push, departmentName));

                            }
                            mListner.OnGetUserDataResultApiGivesSuccess(profileGetUserDataModels);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    try {
                        Log.e(TAG, result);
                        JSONObject json = new JSONObject(result);
                        boolean statusCode = json.getBoolean("status_code");
                        String message = json.getString("message");
                        if (statusCode) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            getUserData(json);
                        } else {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        login_http.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login_http.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
                login_http.setVisibility(View.VISIBLE);
                Loading.hide();
            }
        }.execute();

        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();

    }

    public void refreshFragment() {

        yourAdapter.notifyDataSetChanged();


    }

    // Example usage: call this method to refresh the fragment
    public void refreshButtonClicked() {
        refreshFragment();
    }


    public void UserDetailsUpdateApi() {

        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();

    }


    //HTTPURLCONNECTION:---
    public void FeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Feedback");

        // Create the EditText
        final EditText editText = new EditText(requireContext());
        builder.setView(editText);

        // Add the submit button
        builder.setPositiveButton("Submit", null); // Set the click listener to null for now

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Get the button from the dialog's view
        Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(view -> {
            // Handle the submit button click
            String feedback = editText.getText().toString();

            if (feedback.isEmpty()) {
                editText.setError("Feedback cannot be empty");
            } else {
                // Do something with the feedback
                FeedBackApi feedBackApi = new FeedBackApi(this, feedback);
                feedBackApi.FeedbackHitApi();
                dialog.dismiss(); // Dismiss the dialog after handling the click
            }
        });
    }


    private FragmentManager getSupportFragmentManager() {
//        return null;
        return null;

    }

    @Override
    public void OnProfileItemFeedback(String description) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnProfileItemFeedbackAPiGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnGetRecentViewProductsAPIGivesResult(ArrayList<RecentProductModel> recentProductModels) {
        try {
            requireActivity().runOnUiThread(() -> {

                ProfileHomeProductsRecycler_http.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                ProfileHomeProductsRecycler_http.setAdapter(new RecentProductAdaptor(recentProductModels));
                ProfileHomeProductsRecycler_http.hideShimmerAdapter();

            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    @Override
    public void OnGetRecentViewProductsAPIGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();

                ProfileHomeProductsRecycler_http.hideShimmerAdapter();
                tvRecentProducts.setVisibility(View.GONE);
                tvViewAll.setVisibility(View.GONE);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void getUserData(JSONObject ResultJson) {
        try {
            String status_code = ResultJson.getString("status_code");
            String privacyPolicy = ResultJson.getString("privacyPolicy");
            String termsAndConditions = ResultJson.getString("termsAndConditions");
            String returnRefundPolicy = ResultJson.getString("returnRefundPolicy");
            String security = ResultJson.getString("security");
            String aboutUs = ResultJson.getString("aboutUs");
            String message = ResultJson.getString("message");

            JSONArray userDetailsArray = ResultJson.getJSONArray("data");


            JSONObject userDetails = userDetailsArray.getJSONObject(0);
            String id = String.valueOf(userDetails.getInt("id"));
            String departmentId = String.valueOf(userDetails.getInt("department_id"));
            String userCode = userDetails.getString("user_code");
            String name = userDetails.optString("name", "");
            String lastName = userDetails.optString("last_name", "");
            String email = userDetails.optString("email", "");

            String password = userDetails.optString("password", "");
            String mobile = userDetails.optString("mobile", "");
            String gender = userDetails.optString("gender", "");
            String dob = userDetails.optString("dob", "");
            String attachment = userDetails.optString("attachment", "");
            String city = userDetails.optString("city", "");
            String postCode = userDetails.optString("postCode", "");
            String address = userDetails.optString("address", "");
            String state = userDetails.optString("state", "");
            String country = userDetails.optString("country", "");
            String district = userDetails.optString("district", "");
            String street = userDetails.optString("street", "");
            String about = userDetails.optString("about", "");
            String status = userDetails.optString("status", "");
            String mobileVerified = userDetails.optString("mobile_verified", "");
            String isVerified = userDetails.optString("is_verified", "");
            String logDateCreated = userDetails.optString("log_date_created", "");
            String createdBy = userDetails.optString("created_by", "");
            String logDateModified = userDetails.optString("log_date_modified", "");
            String modifiedBy = userDetails.optString("modified_by", "");
            String fcmToken = userDetails.optString("fcm_token", "");
            String deviceId = userDetails.optString("device_id", "");
            String allowEmail = userDetails.optString("allow_email", "");
            String allowSms = userDetails.optString("allow_sms", "");
            String allowPush = userDetails.optString("allow_push", "");

            //            String customer=userDetails.optString("customer","");
//            String department_name=userDetails.optString("department_name","");
//            String department_name=userDetails.optString("department_name","");
//            String departmentName=userDetails.optString("department_name","");
            String departmentName = userDetails.optString("department_name", "");


            // Store values in static variables
            Variables.clear();

            Variables.status_code = status_code;
            Variables.privacyPolicy = privacyPolicy;
            Variables.termsAndConditions = termsAndConditions;
            Variables.returnRefundPolicy = returnRefundPolicy;
            Variables.security = security;
            Variables.aboutUs = aboutUs;
            Variables.message = message;

            Variables.id = Integer.valueOf(String.valueOf(id));
            Variables.departmentId = Integer.valueOf(String.valueOf(departmentId));
            Variables.userCode = userCode;
            Variables.name = name;
            Variables.lastName = lastName;
            Variables.email = email;

            Variables.password = password;
            Variables.mobile = mobile;
            Variables.gender = gender;
            Variables.dob = dob;
            Variables.attachment = attachment;
            Variables.city = city;
            Variables.postCode = postCode;
            Variables.address = address;
            Variables.state = state;
            Variables.country = country;
            Variables.district = district;
            Variables.street = street;
            Variables.about = about;
            Variables.status = status;
            Variables.mobileVerified = mobileVerified;
            Variables.isVerified = isVerified;
            Variables.logDateCreated = logDateCreated;
            Variables.createdBy = createdBy;
            Variables.logDateModified = logDateModified;
            Variables.modifiedBy = modifiedBy;
            Variables.fcmToken = fcmToken;
            Variables.deviceId = deviceId;
            Variables.allowEmail = allowEmail;
            Variables.allowSms = allowSms;
            Variables.allowPush = allowPush;

            //            Variables.customer=customer;
//            Variables.department_name=department_name;
            Variables.departmentName = departmentName;
//            departmentName = departmentName;


            iv_edit_http.setVisibility(View.VISIBLE);

            Variables.name = tvProfileNameHttp.getText().toString();
            Variables.email = tvEmailHttp.getText().toString();
            Variables.mobile = tvPhoneHttp.getText().toString();
            Variables.userCode = tvRoleHttp.getText().toString();

//            Variables.customer=tvcustomerhttp.getText().toString();
//            Variables.department_name=tvcustomerhttp.getText().toString();
//            Variables.departmentName=tv_customer_name.getText().toString();
//            departmentName = tv_customer_name.getText().toString();
//            departmentName = tvCustomerName.getText().toString();
//            Variables.departmentName = tvCustomerNameHttp.getText().toString();
            Variables.departmentName = tvDepartmentName.getText().toString();

            Glide.with(ProfileFragmentHttpURLConnection.this)
                    .load(responseProfile_http.getData().getAttachment())
                    .error(R.drawable.profile_img)
                    .into(ivProfileImg_Profile);





//            Glide.with(ProfileFragmentHttpURLConnection.this).load(getIntent().getStringExtra("basepath") + data.getProfilePhoto()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivProfile);


            //            Variables.basePath = ivProfileImg.getText().toString();

//            Glide.with(ProfileFragmentHttpURLConnection.this).load(response.body().getBase_path() + response.body().getData().getProfilePhoto())
//                    .error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivProfileImg);



//            String imageUrl = getArguments().getString("attachment") + responseProfile_http.getData().getAttachment();

//            Glide.with(requireContext())
//                    .load(imageUrl)
//                    .error(R.drawable.profile_img)
//                    .into(ivProfileEdit),
//            Loading.hide();

            Loading.hide();

            startActivity(new Intent(requireContext(), EditProfileFragmentHttpURLConnection.class));


        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Toast.makeText(requireContext(), "Internal Error", Toast.LENGTH_SHORT).show();
            iv_edit_http.setVisibility(View.VISIBLE);
        }

    }


}