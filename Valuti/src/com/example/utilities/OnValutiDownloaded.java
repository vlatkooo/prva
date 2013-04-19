package com.example.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.model.Valuta;

public class OnValutiDownloaded implements
		OnContentDownloaded<List<Valuta>> {

	private List<Valuta> items = new ArrayList<Valuta>();

	@Override
	public void onContentDownloaded(String content, int httpStatus)
			throws Exception {
		JSONArray jsonItems = new JSONArray(content);

		for (int i = 0; i < jsonItems.length(); i++) {
			JSONObject jObj = (JSONObject) jsonItems.get(i);
			Valuta v = new Valuta();
			v.setDrzava(jObj.getString("drzava"));
			v.setVrednost(jObj.getInt("vrednost"));
			v.setId(jObj.getLong("id"));
			items.add(v);
		}

	}

	@Override
	public List<Valuta> getResult() {
		return items;
	}

}
