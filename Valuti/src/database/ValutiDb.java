package database;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Valuta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ValutiDb {
	// Database fields
	private SQLiteDatabase database;
	private DbOpenHelper dbHelper;
	private String[] allColumns = { DbOpenHelper.COLUMN_ID,
			DbOpenHelper.COLUMN_DRZAVA, DbOpenHelper.COLUMN_VREDNOST};

	public ValutiDb(Context context, String lang) {
		dbHelper = new DbOpenHelper(context, lang);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public boolean insert(Valuta v) {

		if (v.getId() != null) {
			return update(v);
		}

		long insertId = database.insert(DbOpenHelper.TABLE_NAME, null,
				itemToContentValues(v));

		if (insertId > 0) {
			v.setId(insertId);
			return true;
		} else {
			return false;
		}

	}

	public boolean update(Valuta v) {
		long numRowsAffected = database.update(DbOpenHelper.TABLE_NAME,
				itemToContentValues(v), DbOpenHelper.COLUMN_ID + " = "
						+ v.getId(), null);
		return numRowsAffected > 0;
	}

	public List<Valuta> getAllItems() {
		List<Valuta> items = new ArrayList<Valuta>();

		Cursor cursor = database.query(DbOpenHelper.TABLE_NAME, allColumns,
				null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				items.add(cursorToItem(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return items;
	}

	public Valuta getById(long id) {

		Cursor cursor = database
				.query(DbOpenHelper.TABLE_NAME, allColumns,
						DbOpenHelper.COLUMN_ID + " = " + id, null, null,
						null, null);
		try {
			if (cursor.moveToFirst()) {
				return cursorToItem(cursor);
			} else {
				// no items found
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			cursor.close();
		}

	}

	protected Valuta cursorToItem(Cursor cursor) {
		Valuta v = new Valuta();
		v.setId(cursor.getLong(cursor
				.getColumnIndex(DbOpenHelper.COLUMN_ID)));

		v.setDrzava(cursor.getString(cursor
				.getColumnIndex(DbOpenHelper.COLUMN_DRZAVA)));

		v.setVrednost(cursor.getInt((cursor
				.getColumnIndex(DbOpenHelper.COLUMN_VREDNOST))));


		return v;
	}

	protected ContentValues itemToContentValues(Valuta v) {
		ContentValues values = new ContentValues();
		if (v.getId() != null) {
			values.put(DbOpenHelper.COLUMN_ID, v.getId());
		}
		values.put(DbOpenHelper.COLUMN_DRZAVA, v.getDrzava());
		values.put(DbOpenHelper.COLUMN_VREDNOST, v.getVrednost());
		return values;
	}
}
