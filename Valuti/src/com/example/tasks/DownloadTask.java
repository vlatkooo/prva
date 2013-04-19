package com.example.tasks;

import java.util.List;

import com.example.model.Valuta;
import com.example.utilities.Downloader;
import com.example.utilities.OnContentDownloaded;
import com.example.utilities.OnValutiDownloaded;
import com.example.valuti.MainActivity;

import database.ValutiDb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

public class DownloadTask extends AsyncTask<String, Void, List<Valuta>> {

	public static final String VALUTI_DOWNLOADED_ACTION = "com.example.valuti.VALUTI_DOWNLOADED_ACTION";
	private Exception exception = null;
	protected Context context;

	public DownloadTask(Context context) {
		this.context = context;
	}

	@Override
	protected List<Valuta> doInBackground(String... params) {
		if (params.length < 1) {
			exception = new IllegalArgumentException(
					"At least one argument for the download url expected. ");
			return null;
		} else {

			String url = params[0];
			OnContentDownloaded<List<Valuta>> handler = new OnValutiDownloaded();
			try {
				Downloader.getFromUrl(url, handler);
				publishProgress(null);
				return handler.getResult();
			} catch (Exception ex) {
				exception = ex;
				return null;
			}
		}
	}

	@Override
	protected void onPostExecute(List<Valuta> result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Error: " + exception.getMessage(),
					Toast.LENGTH_LONG).show();
		} else {

			ValutiDb v = new ValutiDb(context, MainActivity.DEFAULT_LANG);
			v.open();

			for (Valuta valuta : result) {
				v.insert(valuta);
			}
			v.close();
			Intent intent=new Intent(VALUTI_DOWNLOADED_ACTION);
			context.sendBroadcast(intent);

		}
	}

}
