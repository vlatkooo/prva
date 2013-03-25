package mk.vladimir.petreski.adapters;

import mk.vladimir.ptereski.laboratoriska1.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MojAdapterEN extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private String[] iminja;
	
	public MojAdapterEN(Context ctx) {
		
		iminja = new String[] {};
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.ctx = ctx;
	}
	
	public MojAdapterEN(Context ctx, String[] iminja) {
		this.ctx = ctx;
		inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.iminja = iminja;
	}
	
	static class ViewHolder {
		public LinearLayout lay;
		public TextView text;
		public ImageView slika;
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return iminja.length;
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return iminja[pos];
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder hol = null;
		if (convertView == null) {
			hol = new ViewHolder();
			hol.lay = (LinearLayout) inflater.inflate(R.layout.rowlayout, null);
			hol.text = (TextView) hol.lay.findViewById(R.id.label);
			hol.slika = (ImageView) hol.lay.findViewById(R.id.slika);
			convertView = hol.lay;
			convertView.setTag(hol);
		}
		
		hol = (ViewHolder) convertView.getTag();
		String s = iminja[position];
		hol.text.setText(s);
		if (s.startsWith("EU")) { hol.slika.setImageResource(R.drawable.eu_flag);}
		else if (s.startsWith("USA")) {hol.slika.setImageResource(R.drawable.usa_flag);}
		else if (s.startsWith("G.Britain")) {hol.slika.setImageResource(R.drawable.gb_flag);}
		else if (s.startsWith("Switzerland")) {hol.slika.setImageResource(R.drawable.swiss_flag);}
		else {hol.slika.setImageResource(R.drawable.aus_flag);}
			
		return convertView;
	}

}
