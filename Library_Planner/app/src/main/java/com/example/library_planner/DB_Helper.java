package com.example.library_planner;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DB_Helper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "eventsDB";
    private static final String TABLE_EVENTS = "EventDetails";
    private static final String KEY_NAME = "eventName";
    private static final String KEY_DURATION = "eventDuration";
    private static final String KEY_START = "eventStart";
    private static final String KEY_DATE = "eventDate";

    ArrayList<LibEvent> eventList;


    public DB_Helper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " +
                TABLE_EVENTS + "(" +
                KEY_NAME + " TEXT," +
                KEY_START + " TEXT," +
                KEY_DURATION + " TEXT," +
                KEY_DATE + " TEXT" + ")";

        db.execSQL(CREATE_TABLE);

    }


    @SuppressLint("Range")
    public ArrayList<LibEvent> get_events() {

        SQLiteDatabase db = this.getReadableDatabase();
        eventList = new ArrayList<>();

        String query = "SELECT " + KEY_NAME + ", " + KEY_START + ", " + KEY_DURATION + ", " + KEY_DATE + " FROM " + TABLE_EVENTS;

        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {

            LibEvent event = new LibEvent();
            event.eventName = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            event.eventDuration = cursor.getString(cursor.getColumnIndex(KEY_DURATION));
            event.eventStart = cursor.getString(cursor.getColumnIndex(KEY_START));
            event.eventDate = cursor.getString(cursor.getColumnIndex(KEY_DATE));
            eventList.add(event);

        }

        cursor.close();

        return eventList;

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);

    }


    public void remove_all_events() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTS, null, null);
        db.close();

    }


    public void create_event(LibEvent event) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvalues = new ContentValues();

        cvalues.put(KEY_NAME, event.getEventName());
        cvalues.put(KEY_DURATION, event.getEventDuration() + " Minutes");
        cvalues.put(KEY_START, event.getEventStart());
        cvalues.put(KEY_DATE, event.getEventDate());

        db.insert(TABLE_EVENTS,null, cvalues);
        db.close();

    }


    public void remove_single_event(String eventName) {

        SQLiteDatabase db = this.getWritableDatabase();

        String remove_query = "DELETE FROM " + TABLE_EVENTS + " WHERE eventName='" + eventName + "';";

        db.execSQL(remove_query);

    }
}
