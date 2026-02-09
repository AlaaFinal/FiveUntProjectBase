package com.example.AlaaFinal;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import Fragments.HomeFragment;
import Fragments.ProfileFragment;
import Fragments.SettingsFragment;

public class StartActivity extends AppCompatActivity {

    BottomNavigationView btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        btm = findViewById(R.id.btmenu);

        if (savedInstanceState == null) {
            ChangeFragment(new HomeFragment());
        }

        btm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              if(menuItem.getItemId()==R.id.home)
              {
                  ChangeFragment(new HomeFragment());
              }

                if(menuItem.getItemId()==R.id.profile)
                {
                    ChangeFragment(new ProfileFragment());
                }

                if(menuItem.getItemId()==R.id.settings)
                {
                    ChangeFragment(new SettingsFragment());
                }
                return true;
            }

        });
    }

    public void ChangeFragment(Fragment fragment)
    {
        FragmentManager frm = getSupportFragmentManager();
        FragmentTransaction ftn = frm.beginTransaction();
        ftn.replace(R.id.flfarg,fragment);
        ftn.commit();

    }
}