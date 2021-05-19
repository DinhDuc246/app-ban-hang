package com.example.foodapp.EmailLoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;

    public static ApiInterface apiInterface;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        init();
    }

    private void init() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.loginBtn1 );
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });
    }

    private void Login() {
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();


        if (TextUtils.isEmpty(user_email)){
            email.setError("Không được để trống");
        }else if (TextUtils.isEmpty(user_password)){
            password.setError("Không được để trống");
        }
        else
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Loging...");
            dialog.setMessage("Please wait white we are checking your credentials");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);


            Call<Users> call = apiInterface.performEmailLogin(user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        Toast.makeText(EmailLoginActivity.this, user_id, Toast.LENGTH_SHORT).show();

                        Toast.makeText(EmailLoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if(response.body().getResponse().equals("no_account"))
                    {
                        Toast.makeText(EmailLoginActivity.this, "Sai Email hoặc Mật khẩu", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(EmailLoginActivity.this, "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackMainPage(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}