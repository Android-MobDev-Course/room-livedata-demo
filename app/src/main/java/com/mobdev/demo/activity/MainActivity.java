package com.mobdev.demo.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mobdev.demo.fragment.HistoryFragment;
import com.mobdev.demo.R;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Simple Activity to show how to use the Room Framework/Library on Android for Data Storage (DB)
 */
public class MainActivity extends AppCompatActivity {

	public static String TAG = "HelloBD";
	
	private Context mContext = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);
        
        if (savedInstanceState == null) {	
            getSupportFragmentManager().beginTransaction().add(R.id.container, new HistoryFragment()).commit();
        }
        
        this.mContext = this;
        
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
