package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodapp.Adapters.PlateAdapter;
import com.example.foodapp.Adapters.ProductAdapter;
import com.example.foodapp.EmailLoginRegister.EmailLoginActivity;
import com.example.foodapp.EmailLoginRegister.EmailRegisterActivity;
import com.example.foodapp.Models.PlateModel;
import com.example.foodapp.Models.Product;
import com.example.foodapp.PhoneLoginRegister.PhoneLoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PlateModel> plateModelList;
    private PlateAdapter plateAdapter;
    private LinearLayout emailContinue,phoneContinue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    ////app update check start///
        AppUpdateChecker appUpdateChecker=new AppUpdateChecker(this);  //pass the activity in constructure
        appUpdateChecker.checkForUpdate(false); //mannual check false here



        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///
        emailContinue = (LinearLayout) findViewById(R.id.linear1);
        phoneContinue = (LinearLayout) findViewById(R.id.linear2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModelList = new ArrayList<>();
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        plateModelList.add(new PlateModel(R.drawable.logo1));
        

        plateAdapter = new PlateAdapter(plateModelList, this);
        recyclerView.setAdapter(plateAdapter);
        plateAdapter.notifyDataSetChanged();











        ////////////auto scroll//////////
        autoscroll();
        /////end/////

        phoneContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });




        emailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmailLoginActivity.class);
                startActivity(intent);

            }
        });

        


    }





    public void autoscroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
            if (count == plateAdapter.getItemCount())
                count = 0;
            if (count < plateAdapter.getItemCount()) {
                recyclerView.smoothScrollToPosition(++count);
                handler.postDelayed(this, speedScroll);
            }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    public void skipBtn(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}