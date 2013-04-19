package com.example.valuti;

import com.example.tasks.DownloadTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		DownloadTask task = new DownloadTask(this);
		task.execute(getString(R.string.site_valuti));

		return super.onStartCommand(intent, flags, startId);
	}

}