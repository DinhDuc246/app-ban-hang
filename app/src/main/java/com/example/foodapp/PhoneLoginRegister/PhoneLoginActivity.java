package com.example.foodapp.PhoneLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapp.EmailLoginRegister.EmailLoginActivity;
import com.example.foodapp.MainActivity;
import com.example.foodapp.OperationRetrofitApi.ApiClient;
import com.example.foodapp.OperationRetrofitApi.ApiInterface;
import com.example.foodapp.OperationRetrofitApi.Users;
import com.example.foodapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText phone, otp;
    private Button loginBtn, btnOtp;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);


        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        init();

    }

    private void init() {
        phone = (EditText) findViewById(R.id.phone);
        otp = (EditText) findViewById(R.id.otp);
        loginBtn = (Button) findViewById(R.id.loginBtn2);
        btnOtp = (Button) findViewById(R.id.loginBtn3);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().equals(""))
                {
                    Toast.makeText(PhoneLoginActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login() {
        String user_phone = phone.getText().toString().trim();
        if (TextUtils.isEmpty(user_phone)){
            phone.setError("Không được để trống");
        }
        else
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Loging...");
            dialog.setMessage("Please wait white we are checking your credentials");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);


            Call<Users> call = apiInterface.performPhoneLogin(user_phone);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.body().getResponse().equals("ok"))
                    {
                        String user_id = response.body().getUserId();
                        Toast.makeText(PhoneLoginActivity.this, user_id, Toast.LENGTH_SHORT).show();

                        Toast.makeText(PhoneLoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if(response.body().getResponse().equals("no_account"))
                    {
                        Toast.makeText(PhoneLoginActivity.this, "Sđt chưa được đăng ký", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(PhoneLoginActivity.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    public void goToRegister(View view) {
        Intent intent = new Intent(this,PhoneRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}