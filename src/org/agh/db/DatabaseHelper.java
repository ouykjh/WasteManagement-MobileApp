package org.agh.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//import org.codehaus.jackson.map.deser.std.StdDeserializer.SqlDateDeserializer;

public class DatabaseHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "WasteMngDB";
	private static final String DATABASE_TAG = "DB_TAG";
	
	//tables names
	private static final String TABLE_ROUTE = "routes";
	private static final String TABLE_FORMULAR = "formulars";
	
	//common tables data
	private static final String KEY_ID = "id";
	
	//TABLE_ROUTE - column names
	private static final String KEY_EXTERNAL_ROUTE_ID = "externalRouteId";
	private static final String KEY_ROUTE_NAME = "name";
	
	//TABLE_FORMULAR - column names
	private static final String KEY_ROUTE_ID = "route_id";
	private static final String KEY_POINT_ID = "point_id";
	private static final String KEY_MOBILE_USER_ID = "mobile_user_id";
	private static final String KEY_BINS_AMOUNT = "bins_amount";
	private static final String KEY_PERCENTAGE_FILL = "percentage_fill";
	
	//Creating tables
	private static final String CREATE_TABLE_ROUTES = "CREATE TABLE " + TABLE_ROUTE + "( " + KEY_ID + " INTEGER PRIMARY KEY, " 
			+ KEY_EXTERNAL_ROUTE_ID + " INTEGER, " + KEY_ROUTE_NAME + " TEXT)";
	
	private static final String CREATE_TABLE_FORMULARS = "CREATE TABLE " + TABLE_FORMULAR + "( " + KEY_ID + " INTEGER PRIMARY KEY, " 
			+ KEY_POINT_ID + " INTEGER, " + KEY_MOBILE_USER_ID + " INTEGER, " + KEY_BINS_AMOUNT + " DOUBLE, " + KEY_PERCENTAGE_FILL + " DOUBLE, " + KEY_ROUTE_ID + " INTEGER, " + 
			"FOREIGN KEY (" + KEY_ROUTE_ID + ") REFERENCES " + TABLE_ROUTE + " (" + KEY_ID + "))";
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ROUTES);
		Log.d(DATABASE_TAG, "Table 'routes' has been created!");
		db.execSQL(CREATE_TABLE_FORMULARS);
		Log.d(DATABASE_TAG, "Table 'formulars' has been created!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
	     db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMULAR);
	     
	     //recreating database
	     onCreate(db);
	}
	
	public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
	
	public long createRoute(Route route){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_EXTERNAL_ROUTE_ID, route.getId());
		values.put(KEY_ROUTE_NAME, route.getName());
		
		long routeID = db.insert(TABLE_ROUTE, null, values);
		
		return routeID;
	}
	
	public long createFormular(Formular formular){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_BINS_AMOUNT, formular.getAmountOfBins());
		values.put(KEY_PERCENTAGE_FILL, formular.getPercentageFilling());
		values.put(KEY_ROUTE_ID, formular.getRouteId());
		values.put(KEY_POINT_ID, formular.getPointId());
		values.put(KEY_MOBILE_USER_ID, formular.getMobileUserId());

		long formularID = db.insert(TABLE_FORMULAR, null, values);
		
		return formularID;
	}
	
	public List<Route> getAllRoutes(){
		List<Route> routes = new ArrayList<Route>();
		String query = "SELECT * FROM " + TABLE_ROUTE;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i(DATABASE_TAG, query);
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				Route route = new Route();
				route.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				route.setName(cursor.getString(cursor.getColumnIndex(KEY_ROUTE_NAME)));
				routes.add(route);
			}while(cursor.moveToNext());
		}
		return routes;
	}
	
	public List<Formular> getAllFormulars(){
		List<Formular> formulars = new ArrayList<Formular>();
		String query = "SELECT * FROM " + TABLE_FORMULAR;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Log.i(DATABASE_TAG, query);
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			do{
				Formular formular = new Formular();
				formular.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				formular.setPointId(cursor.getInt(cursor.getColumnIndex(KEY_POINT_ID)));
				formular.setMobileUserId(cursor.getInt(cursor.getColumnIndex(KEY_MOBILE_USER_ID)));
				formular.setAmountOfBins(cursor.getInt(cursor.getColumnIndex(KEY_PERCENTAGE_FILL)));
				formular.setPercentageFilling(cursor.getInt(cursor.getColumnIndex(KEY_BINS_AMOUNT)));
				formulars.add(formular);
			}while(cursor.moveToNext());
		}
		return formulars;
	}
	
	public List<Formular> getFormularsByRouteId(long routeId){
		List<Formular> formulars = new ArrayList<Formular>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Log.i(DATABASE_TAG, String.valueOf(routeId));
		String query = "SELECT * FROM " + TABLE_FORMULAR + " WHERE " + KEY_ROUTE_ID + " = " + routeId;
		Log.i(DATABASE_TAG, query);
		Cursor cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			do{
				Formular formular = new Formular();
				formular.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				formular.setPointId(cursor.getInt(cursor.getColumnIndex(KEY_POINT_ID)));
				formular.setMobileUserId(cursor.getInt(cursor.getColumnIndex(KEY_MOBILE_USER_ID)));
				formular.setAmountOfBins(cursor.getInt(cursor.getColumnIndex(KEY_PERCENTAGE_FILL)));
				formular.setPercentageFilling(cursor.getInt(cursor.getColumnIndex(KEY_BINS_AMOUNT)));
				formular.setRouteId(routeId);
				formulars.add(formular);
			}while(cursor.moveToNext());
		}
		return formulars;
	}
	
	public void deleteFormular(long id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FORMULAR, KEY_ID + " = ?", new String [] {String.valueOf(id)});
	}
	
	public void deleteRoute(long id){
		SQLiteDatabase db = this.getWritableDatabase();
		for(Formular f : getFormularsByRouteId(id))
			this.deleteFormular(f.getId());
		db.delete(TABLE_ROUTE, KEY_ID + " = ?", new String [] {String.valueOf(id)});
	}
	
}
