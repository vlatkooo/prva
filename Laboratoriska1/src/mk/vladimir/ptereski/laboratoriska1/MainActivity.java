package mk.vladimir.ptereski.laboratoriska1;


import mk.vladimir.petreski.adapters.MojAdapter;
import mk.vladimir.petreski.adapters.MojAdapterEN;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;


public class MainActivity extends ListActivity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] iminja = new String[] {"ЕМУ 61,6198","С А Д 47,5902","В.Британија 72,2559","Швајцарија	50,4584","Австралија 49,6453"};
		MojAdapter adapter = new MojAdapter(this, iminja);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View view) {
	switch (view.getId()) {
	    case R.id.btn_en:
	    	String[] iminjaen = new String[] {"EU 61,6198","USA 47,5902","G.Britain 72,2559","Switzerland 50,4584","Australia 49,6453"};
	    	MojAdapterEN aden = new MojAdapterEN(this, iminjaen);
	    	setListAdapter(aden);
	    case R.id.btn_mkd:
	    	Intent ovoj = new Intent(this,MainActivity.class);
	    	startActivity(ovoj);
	}
	}
	
	public void explicit(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}
	
	
}
