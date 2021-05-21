package com.example.foodapp.EmailLoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.MainActivity;
import com.example.foodapp.OperationRetrofitApi.ApiClient;
import com.example.foodapp.OperationRetrofitApi.ApiInterface;
import com.example.foodapp.OperationRetrofitApi.Users;
import com.example.foodapp.PhoneLoginRegister.PhoneRegisterActivity;
import com.example.foodapp.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailRegisterActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button regBtn;

    public static ApiInterface apiInterface;
     String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        init();
        
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        regBtn = (Button) findViewById(R.id.regBtn1 );
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration();
            }
        });

    }

    private void Registration() {
        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();


        if (TextUtils.isEmpty(user_name)){
            name.setError("Không được để trống");
        }else if (TextUtils.isEmpty(user_email)){
            email.setError("Không được để trống");
        }else if (TextUtils.isEmpty(user_password)){
            password.setError("Không được để trống");
        }
        else
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Register...");
            dialog.setMessage("Please wait white we are adding your credentials");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            Call<Users> call = apiInterface.performEmailRegistration(user_name, user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();

                        Toast.makeText(EmailRegisterActivity.this, user_id, Toast.LENGTH_SHORT).show();

                        Toast.makeText(EmailRegisterActivity.this, "Tài khoản của bạn đã được tạo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("fails"))
                    {
                        Toast.makeText(EmailRegisterActivity.this, "Đã xảy ra một vài lỗi, vui lòng thao tác lại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("already"))
                    {
                        Toast.makeText(EmailRegisterActivity.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(EmailRegisterActivity.this, "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void goToLogin(View view) {
        Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
        finish();

    }


    public void BackMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}