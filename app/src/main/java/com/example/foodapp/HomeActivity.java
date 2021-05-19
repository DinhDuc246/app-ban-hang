package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodapp.Adapters.ProductAdapter;
import com.example.foodapp.Fragments.CartFragment;
import com.example.foodapp.Fragments.HomeFragment;
import com.example.foodapp.Fragments.NotificationFragment;
import com.example.foodapp.Fragments.UserFragment;
import com.example.foodapp.Models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /////////////////////////
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        /////////////////////////
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);

        //////////

        ///////////


        ///hide status bar start///
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///hide status bar end///



    }




    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;


                switch (item.getItemId()){
                    case R.id.homeproduct:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.notification:
                        selectedFragment = new NotificationFragment();
                        break;
                    case R.id.cart:
                        selectedFragment = new CartFragment();
                        break;
                    case R.id.user:
                        selectedFragment = new UserFragment();
                        break;
                }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();

                    return true;
                }
            };
}