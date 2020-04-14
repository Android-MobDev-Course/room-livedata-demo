package com.mobdev.demo.persistence;

import java.util.List;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.mobdev.demo.activity.MainActivity;
import com.mobdev.demo.model.LogDescriptor;

/**
 * Created by Marco Picone (picone.m@gmail.com) 20/03/2020
 * Singleton to manage Log Storage using Room Library
 */
public class LogDescriptorManager {

	private Context context = null;
	private AppDatabase db = null;
	private LogDao logDao = null;

	/*
	 * The instance is static so it is shared among all instances of the class. It is also private
	 * so it is accessible only within the class.
	 */
	private static LogDescriptorManager instance = null;

	/*
	 * The constructor is private so it is accessible only within the class.
	 */
	private LogDescriptorManager(Context context){
		Log.d(MainActivity.TAG,"Number Manager Created !");

		this.context = context;

		this.db = Room.databaseBuilder(context, AppDatabase.class, "logs-database").allowMainThreadQueries().build();
		this.logDao = this.db.logDao();
	}

	public static LogDescriptorManager getInstance(Context context){
		/*
		 * The constructor is called only if the static instance is null, so only the first time 
		 * that the getInstance() method is invoked.
		 * All the other times the same instance object is returned.
		 */
		if(instance == null)
			instance = new LogDescriptorManager(context);
		return instance;
	}

	public void addLog(LogDescriptor log){
		this.logDao.insertAll(log);
	}

	public void addLogToHead(LogDescriptor log){
		this.addLog(log);
	}

	public void removeLog(LogDescriptor log){
		this.logDao.delete(log);
	}

	public List<LogDescriptor> getLogList(){
		return this.logDao.getAll();
	}

	public LiveData<List<LogDescriptor>> getLogLiveDataList(){
		return this.logDao.getAllLiveData();
	}

}

