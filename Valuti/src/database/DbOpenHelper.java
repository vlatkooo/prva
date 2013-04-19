package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DRZAVA = "drzava";
	public static final String COLUMN_VREDNOST = "vrednost";
	public static final String TABLE_NAME = "Valuti";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME_EXPRESSION = "ValutiDatabase_%s.db";

	private static final String DATABASE_CREATE = String
			.format("create table %s (%s  integer primary key autoincrement, "
					+ "%s text not null, %s integer default 0);",
					TABLE_NAME, COLUMN_ID, COLUMN_DRZAVA, COLUMN_VREDNOST);

	

	public DbOpenHelper(Context context, String lang) {
		super(context, String.format(DATABASE_NAME_EXPRESSION, lang), null,
				DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
		onCreate(db);
	}

}
