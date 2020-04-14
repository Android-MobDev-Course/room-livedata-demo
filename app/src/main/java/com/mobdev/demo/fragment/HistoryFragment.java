package com.mobdev.demo.fragment;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdev.demo.adapter.MyAdapter;
import com.mobdev.demo.R;
import com.mobdev.demo.activity.MainActivity;
import com.mobdev.demo.model.LogDescriptor;
import com.mobdev.demo.persistence.LogDescriptorManager;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Fragment for Logs History
 */
public  class HistoryFragment extends Fragment {

	private static final String TAG = "HistoryFragment";

	private RecyclerView mRecyclerView = null;
	private LinearLayoutManager mLayoutManager = null;
	private MyAdapter mAdapter = null;
	private ImageButton addButton = null;

	private Context mContext = null;

	private LogDescriptorManager logDescriptorManager = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container, false);

		this.mContext = getContext();
		this.logDescriptorManager = LogDescriptorManager.getInstance(mContext);

		init(rootView);
		observeLogData();

		return rootView;
	}

	private void init(View rootView){

		mRecyclerView  = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

		// use a linear layout manager
		mLayoutManager  = new LinearLayoutManager(getActivity());
		mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mLayoutManager.scrollToPosition(0);

		mRecyclerView.setLayoutManager(mLayoutManager);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// specify an adapter (see also next example)
		mAdapter  = new MyAdapter(mContext);
		mRecyclerView.setAdapter(mAdapter);

		addButton  = (ImageButton)rootView.findViewById(R.id.addButton);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(MainActivity.TAG,"Add Button CLicked !");
				logDescriptorManager.addLogToHead(createRandomLogDescriptor());
			}
		});
	}

	private void observeLogData(){
		this.logDescriptorManager.getLogLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<LogDescriptor>>() {
			@Override
			public void onChanged(List<LogDescriptor> logDescriptors) {
				if(logDescriptors != null){
					Log.d(TAG, "Update Log List Received ! List Size: " + logDescriptors.size());
					refreshRecyclerView(logDescriptors, 0);
				}
				else
					Log.e(TAG, "Error observing Log List ! Received a null Object !");
			}
		});
	}

	private void refreshRecyclerView(List<LogDescriptor> updatedList, int scrollPosition){
		mAdapter.setDataset(updatedList);
		mAdapter.notifyDataSetChanged();
		if(scrollPosition >= 0)
			mLayoutManager.scrollToPosition(scrollPosition);
	}
	
	public static LogDescriptor createRandomLogDescriptor() {
	    Random random = new Random();

	    double x0 = 44.766992;
	    double y0 = 10.310035;
	    int radius = 1000;

	    // Convert radius from meters to degrees
	    double radiusInDegrees = radius / 111000f;

	    double u = random.nextDouble();
	    double v = random.nextDouble();
	    double w = radiusInDegrees * Math.sqrt(u);
	    double t = 2 * Math.PI * v;
	    double x = w * Math.cos(t);
	    double y = w * Math.sin(t);

	    // Adjust the x-coordinate for the shrinking of the east-west distances
	    double new_x = x / Math.cos(y0);

	    double foundLongitude = new_x + x0;
	    double foundLatitude = y + y0;
	    
	    int number = random.nextInt((1000 - 0) + 1);
	    
	    return new LogDescriptor(foundLatitude, foundLongitude, "RANDOM_LOG", ""+Double.valueOf(number));
	}

}