package ha.thanh.pikerfree.dataHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HaVan on 12/12/2017.
 */

public class NotificationDataHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notificationDatabase";

    // Contacts table name
    private static final String TABLE_CONTACTS = "notification";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MESS = "mess";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA_ID = "data_id"; // relative to post id or user id
    private static final String KEY_IS_READ = "is_read"; // is read or clicked by user

    public NotificationDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + TABLE_CONTACTS
                        + "("
                        + KEY_ID + " INTEGER PRIMARY KEY,"
                        + KEY_MESS + " TEXT,"
                        + KEY_TYPE + " INTEGER,"
                        + KEY_DATA_ID + " TEXT,"
                        + KEY_IS_READ + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addNotification(SQLiteNotification mess) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESS, mess.getMess()); // content of the notification
        values.put(KEY_TYPE, mess.getType());
        values.put(KEY_DATA_ID, mess.getDataID()); // post id or user id
        values.put(KEY_IS_READ, mess.isRead()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    SQLiteNotification getNotification(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS,
                new String[]{KEY_ID,
                        KEY_MESS,
                        KEY_TYPE,
                        KEY_DATA_ID,
                        KEY_IS_READ}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SQLiteNotification contact = new SQLiteNotification(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString (3),
                cursor.getInt(4));
        // return contact
        cursor.close();
        db.close();
        return contact;
    }

    // Getting All Contacts
    public List<SQLiteNotification> getAllMess() {
        List<SQLiteNotification> contactList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLiteNotification contact = new SQLiteNotification();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setMess(cursor.getString(1));
                contact.setType(cursor.getInt(2));
                contact.setDataID(cursor.getString(3));
                contact.setRead(cursor.getInt(4));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateNotification(SQLiteNotification contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESS, contact.getMess());
        values.put(KEY_TYPE, contact.getType());
        values.put(KEY_DATA_ID, contact.getDataID());
        values.put(KEY_IS_READ, contact.isRead());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }

    // Deleting single contact
    public void deleteNotification(SQLiteNotification contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    // Getting contacts Count
    public int getNotificationCount() {

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE " + KEY_IS_READ + " = 0 " ; // 0 mean unread
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

}