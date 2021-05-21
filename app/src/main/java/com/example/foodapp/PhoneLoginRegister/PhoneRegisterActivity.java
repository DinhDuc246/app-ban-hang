package com.example.foodapp.PhoneLoginRegister;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.EmailLoginRegister.EmailRegisterActivity;
import com.example.foodapp.MainActivity;
import com.example.foodapp.OperationRetrofitApi.ApiClient;
import com.example.foodapp.OperationRetrofitApi.ApiInterface;
import com.example.foodapp.OperationRetrofitApi.Users;
import com.example.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.AuthProvider;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneRegisterActivity extends AppCompatActivity {
    private EditText phone, otp;
    private Button register, btnOtp;
    public static ApiInterface apiInterface;
    String user_id;


    ////phone otp//////////
    private String mVetificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    /////////////////
    ProgressDialog dialog;
    ///////////////





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);


        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mAuth = FirebaseAuth.getInstance();



        init();

    }

    private void init() {
        phone = (EditText) findViewById(R.id.phone);
        otp = (EditText) findViewById(R.id.otp);
        register = (Button) findViewById(R.id.register);
        btnOtp = (Button) findViewById(R.id.Btn3);


        ///////////progressdialog/////////
        dialog = new ProgressDialog(this) ;
        dialog.setTitle("Registering...");
        dialog.setMessage("Vui long doi!");
        dialog.setCanceledOnTouchOutside(false);
    //////////////////////////////////////////////


        //////////////phone Otp callback start////////////////
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                dialog.dismiss();
                otp.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                btnOtp.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneRegisterActivity.this, "Invalid Phone Number"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token)
            {
                ////Save verification ID and resending token so we can use them late//
                mVetificationId = verificationId;
                mResendToken = token;

                Toast.makeText(PhoneRegisterActivity.this, "Code has been sent, please check", Toast.LENGTH_SHORT).show();


                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                btnOtp.setVisibility(View.VISIBLE);
                register.setVisibility(View.GONE);
                dialog.dismiss();
            }
        };



        ////////////phone otp callback end///////////

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_phone = phone.getText().toString().trim();
                if (TextUtils.isEmpty(user_phone))
                {
                    phone.setError("Không được để trống!");
                }
                else {
                    dialog.show();
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+84" + user_phone,
                            60,
                            TimeUnit.SECONDS,
                            PhoneRegisterActivity.this,
                            callbacks
                    );
                }
            }
        });
        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().equals(""))
                {
                    Toast.makeText(PhoneRegisterActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVetificationId, otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void  signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Register();
                }
                else
                {
                    Toast.makeText(PhoneRegisterActivity.this, "Đã xảy ra lỗi ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void Register() {



            Call<Users> call = apiInterface.performPhonelRegistration(phone.getText().toString());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if (response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        Toast.makeText(PhoneRegisterActivity.this, "Tài khoản của bạn đã được tạo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("fails"))
                    {
                        Toast.makeText(PhoneRegisterActivity.this, "Đã xảy ra một vài lỗi, vui lòng thao tác lại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("already"))
                    {
                        Toast.makeText(PhoneRegisterActivity.this, "SĐT đã được sử dụng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(PhoneRegisterActivity.this, "Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            });
        }



    public void goToLogin(View view) {
        Intent intent = new Intent(this, PhoneLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void BackMainPage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}