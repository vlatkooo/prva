package com.example.valuti;

import java.util.List;

import com.example.model.Valuta;
import com.example.tasks.DownloadTask;
import com.example.tasks.DownloadWithProgressTask;


import database.ValutiDb;
import adapter.MojAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static final String DEFAULT_LANG = "mk";
	
	private MojAdapter adapter;
	private ValutiDb dbase;
	private ListView lista;
	private TextView tekst;
	private EditText drzava;
	private EditText vrednost;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void loadviews() {
		drzava = (EditText) findViewById(R.id.drzava);
		vrednost = (EditText) findViewById(R.id.vrednost);
		tekst = (TextView) findViewById(R.id.kursna_lista);
		lista = (ListView) findViewById(R.id.lista);
	}
	
	private void init() {
		adapter = new MojAdapter(this);
		lista.setAdapter(adapter);	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		dbase = new ValutiDb(this, DEFAULT_LANG);
		dbase.open();
		loadviews();
		loadData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		dbase.close();
	}

	private void loadData() {

		List<Valuta> res = dbase.getAllItems();
		adapter.clear();
		adapter.addAll(res);
	}
	

	public void explicit(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, "Refresh");
		menu.add(1, 2, 2, "Refresh with service");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			if (checkConnection()) {
				DownloadWithProgressTask task = new DownloadWithProgressTask(
						this, adapter);
				task.execute(getString(R.string.site_valuti));

				return true;
			}

		} else if (item.getItemId() == 2) {
			if (checkConnection()) {
				createDialog();
				IntentFilter filter = new IntentFilter(
						DownloadTask.VALUTI_DOWNLOADED_ACTION);
				registerReceiver(new OnDownloadRefreshReceiver(), filter);
				startService(new Intent(this, DownloadService.class));
				return true;
			}
		}

		return false;
	}

	private boolean checkConnection() {

		ConnectivityManager connectivityMannager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityMannager.getActiveNetworkInfo();
		if (netInfo == null) {
			showSettingsAlert();
			return false;
		} else {
			return true;
		}

	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		// Setting Dialog Title
		alertDialog.setTitle("Internet settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("No active connection. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface promptDialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_WIFI_SETTINGS);
						MainActivity.this.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface promptDialog, int which) {
						promptDialog.dismiss();

					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	private ProgressDialog loadingDialog;

	private void createDialog() {
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setTitle(this.getResources().getString(
				R.string.download_title));
		loadingDialog.setMessage(this.getResources().getString(
				R.string.download_description));
		loadingDialog.setIndeterminate(true);
		loadingDialog.setCancelable(false);
	}
	
	public void addValuta(View view) {

		Valuta v = new Valuta(drzava.getText().toString(), Integer.valueOf(vrednost.getText().toString()) );

		adapter.add(v);
		dbase.insert(v);
	}
	
	class OnDownloadRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			loadData();

			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}

			MainActivity.this.unregisterReceiver(this);

		}
	}
	
}
