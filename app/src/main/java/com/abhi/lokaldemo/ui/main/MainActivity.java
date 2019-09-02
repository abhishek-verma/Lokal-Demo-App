package com.abhi.lokaldemo.ui.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.abhi.lokaldemo.R;
import com.abhi.lokaldemo.api.model.PostResponse;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.d("mainactivity", "oncreate");
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, "MainFragment")
                    .commit();
        }
    }

    public void downloadImage(PostResponse post) {
        // TODO download image here
    }
}
