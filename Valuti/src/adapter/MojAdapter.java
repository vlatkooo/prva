package adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Valuta;
import com.example.valuti.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MojAdapter extends BaseAdapter {

	private Context ctx;
	private LayoutInflater inflater;
	private List<Valuta> valuti;
	
	public MojAdapter(Context ctx) {
		
		valuti = new ArrayList<Valuta>();
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.ctx = ctx;
	}
	
	public MojAdapter(Context ctx, List<Valuta> v) {
		this.ctx = ctx;
		inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.valuti = v;
	}
	
	static class ViewHolder {
		public LinearLayout lay;
		public TextView drzava;
		public TextView vrednost;
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return valuti.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return valuti.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Valuta v = valuti.get(position);
		ViewHolder hol = null;
		if (convertView == null) {
			hol = new ViewHolder();
			hol.lay = (LinearLayout) inflater.inflate(R.layout.rowlayout, null);
			hol.drzava = (TextView) hol.lay.findViewById(R.id.drzava);
			hol.vrednost = (TextView) hol.lay.findViewById(R.id.vrednost);
			convertView = hol.lay;
			convertView.setTag(hol);
		}
	
		hol = (ViewHolder) convertView.getTag();
		
		hol.drzava.setText(v.getDrzava());
		hol.vrednost.setText(v.getVrednost());
			
		return convertView;
	}
	
	public void add(Valuta v) {
		valuti.add(v);
		notifyDataSetChanged();
	}
	
	public void addAll(List<Valuta> items) {
		this.valuti.addAll(items);
		notifyDataSetChanged();
	}
	
	public void clear(){
		valuti.clear();
		notifyDataSetInvalidated();
	}
}
