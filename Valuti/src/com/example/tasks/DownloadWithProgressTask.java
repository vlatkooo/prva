package com.example.tasks;

import java.util.List;

import com.example.model.Valuta;
import com.example.utilities.Downloader;
import com.example.utilities.OnContentDownloaded;
import com.example.utilities.OnValutiDownloaded;
import com.example.valuti.R;

import adapter.MojAdapter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

public class DownloadWithProgressTask extends
		AsyncTask<String, Void, List<Valuta>> {

	private Exception exception = null;
	private ProgressDialog loadingDialog;
	private MojAdapter adapter;
	protected Context context;
	Handler handler;

	public DownloadWithProgressTask(Context context, MojAdapter adapter) {
		this.context = context;
		this.adapter = adapter;
		handler = new Handler();
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
				ex.printStackTrace();
				return null;
			}
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		createDialog();

	}

	

	@Override
	protected void onPostExecute(List<Valuta> result) {
		super.onPostExecute(result);
		if (exception != null) {
			Toast.makeText(context, "Error: " + exception.getMessage(),
					Toast.LENGTH_LONG).show();
		} else {
			adapter.clear();
			adapter.addAll(result);
		}
		dismiss();
	}

	private void createDialog() {
		loadingDialog = new ProgressDialog(context);
		loadingDialog.setTitle(context.getResources().getString(
				R.string.download_title));
		loadingDialog.setMessage(context.getResources().getString(
				R.string.download_description));
		loadingDialog.setIndeterminate(true);
		loadingDialog.setCancelable(false);
	}

	public void dismiss() {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
		}
	}

}

