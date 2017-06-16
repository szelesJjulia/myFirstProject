package com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ebookfrenzy.szelesjulide.practiceprojectgooglemaplist.data.MarkerObject;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * Created by szeles.julide on 2017/06/14.
 */

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "marketDBY.db";
    private static final String TABLE_MARKERS = "markers";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGTITUDE = "longtitude";

    private ContentResolver myCR;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,int versiom){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MARKER_TABLE = "create table " + TABLE_MARKERS + "(" + "_id INTEGER primary key autoincrement,"
                + COLUMN_TITLE + " TEXT," + COLUMN_COMMENT + " TEXT,"
                + COLUMN_LATITUDE + " DOUBLE," + COLUMN_LONGTITUDE + " DOUBLE" + ")";
        db.execSQL(CREATE_MARKER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_MARKERS);
        onCreate(db);
    }

    //Insert new element
    public void addMarker(MarkerObject marker){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE,marker.getMarkerTitle());
        values.put(COLUMN_COMMENT,marker.getMarkerComment());
        values.put(COLUMN_LATITUDE,marker.getMarkerLatitude());
        values.put(COLUMN_LONGTITUDE,marker.getMarkerLongtitude());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MARKERS,null,values);

        db.close();
    }

    public ArrayList<MarkerObject> queryAllObjects(){
        ArrayList<MarkerObject> allMarkers = new ArrayList<MarkerObject>();

        String query = "select * from " + TABLE_MARKERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                MarkerObject markerObject = new MarkerObject();
                markerObject.set_id(cursor.getInt(0));
                markerObject.setMarkerTitle(cursor.getString(1));
                markerObject.setMarkerComment(cursor.getString(2));
                markerObject.setMarkerLatitude(cursor.getDouble(3));
                markerObject.setMarkerLongtitude(cursor.getDouble(4));
                allMarkers.add(markerObject);
                cursor.moveToNext();
            }
        }

        return allMarkers;
    }

    public MarkerObject findMarker(MarkerObject markerObject){
        String query = "select * from " + TABLE_MARKERS + " where " + COLUMN_LONGTITUDE + " = " + markerObject.getMarkerLongtitude() + " and " + COLUMN_LATITUDE + " = " + markerObject.getMarkerLatitude();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        MarkerObject markerObject1 = new MarkerObject();

        if (cursor.moveToFirst()){
            markerObject1.set_id(Integer.parseInt(cursor.getString(0)));
            markerObject1.setMarkerTitle(cursor.getString(1));
            markerObject1.setMarkerComment(cursor.getString(2));

            cursor.close();
        }else {
            markerObject1 = null;
        }

        db.close();

        return markerObject1;
    }

    public boolean deleteMarker(MarkerObject marker){
        boolean result = false;
        ArrayList<MarkerObject>tryOutList = queryAllObjects();
        int markerId = marker.get_id();

        MarkerObject m = findMarker(marker);

        String query = "SELECT * from " + TABLE_MARKERS + " where " + COLUMN_LONGTITUDE + " = " + marker.getMarkerLongtitude() + " and " + COLUMN_LATITUDE + " = " + marker.getMarkerLatitude();
        //String query2 = "SELECT * from " + TABLE_MARKERS + " where " + COLUMN_ID + "=" + marker.get_id();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        marker = new MarkerObject();

        if (cursor.moveToFirst()){
                marker.set_id(Integer.parseInt(cursor.getString(0)));
                db.delete(TABLE_MARKERS, COLUMN_ID + " = ?", new String[]{String.valueOf(marker.get_id())});
                cursor.close();
                result = true;
            }

        //cursor.close();

        //db.close();
        return result;

    }


}
