package com.snape.shivichu.expensemanager.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shivichu on 12/15/2017.
 */

//The class is extending SQLiteOpenHelper
public class DatabaseManager extends SQLiteOpenHelper {

    /*
    * This time we will not be using the hardcoded string values
    * Instead here we are defining all the Strings that is required for our database
    * for example databasename, table name and column names.
    * */
    private static final String DATABASE_NAME = "EManager";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "entry";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ENTRY = "newentry";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_VIA = "via";
    private static final String COLUMN_PHONENUMBER = "phonenumber";

    private static final String SETTINGS_TABLE = "settings";
    private static final String isWidget = "widget";


    /*
    * We need to call the super i.e. parent class constructur
    * And we need to pass 4 parameters
    * 1. Context context -> It is the context object we will get it from the activity while creating the instance
    * 2. String databasename -> It is the name of the database and here we are passing the constant that we already defined
    * 3. CursorFactory cursorFactory -> If we want a cursor to be initialized on the creation we can use cursor factory, it is optionall and that is why we passed null here
    * 4. int version -> It is an int defining our database version
    * */
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*
        * The query to create our table
        * It is same as we had in the previous post
        * The only difference here is we have changed the
        * hardcoded string values with String Variables
        * */

        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT entry PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_ENTRY + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_DATE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_VIA + " varchar(200) NOT NULL, \n" +
                "    " + COLUMN_PHONENUMBER + " varchar(200) NOT NULL \n" +
                ");";

        String sql2 = "CREATE TABLE " + SETTINGS_TABLE + " (\n" +
                "    " + isWidget + " varchar(200) NOT NULL \n" +
                ");";

        /*
        * Executing the string to create the table
        * */
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*
        * We are doing nothing here
        * Just dropping and creating the table
        * */
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        String sql2 = "DROP TABLE IF EXISTS " + SETTINGS_TABLE + ";";


        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

    /*
    * CREATE OPERATION
    * ====================
    * This is the first operation of the CRUD.
    * This method will create a new employee in the table
    * Method is taking all the parameters required
    *
    * Operation is very simple, we just need a content value objects
    * Inside this object we will put everything that we want to insert.
    * So each value will take the column name and the value that is to inserted
    * for the column name we are using the String variables that we defined already
    * And that is why we converted the hardcoded string to variables
    *
    * Once we have the contentValues object with all the values required
    * We will call the method getWritableDatabase() and it will return us the SQLiteDatabase object and we can write on the database using it.
    *
    * With this object we will call the insert method it takes 3 parameters
    * 1. String -> The table name where the value is to be inserted
    * 2. String -> The default values of null columns, it is null here as we don't have any default values
    * 3. ContentValues -> The values that is to be inserted
    *
    * insert() will return the inserted row id, if there is some error inserting the row
    * it will return -1
    *
    * So here we are returning != -1, it will be true of record is inserted and false if not inserted
    * */

    public boolean addEntry(String entry, String date, String via, String phoneNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ENTRY, entry);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_VIA, via);
        contentValues.put(COLUMN_PHONENUMBER, phoneNumber);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }


    /*
    * READ OPERATION
    * =================
    * Here we are reading values from the database
    * First we called the getReadableDatabase() method it will return us the SQLiteDatabase instance
    * but using it we can only perform the read operations.
    *
    * We are running rawQuery() method by passing the select query.
    * rawQuery takes two parameters
    * 1. The query
    * 2. String[] -> Arguments that is to be binded -> We use it when we have a where clause in our query to bind the where value
    *
    * rawQuery returns a Cursor object having all the data fetched from database
    * */
    public Cursor getAllEntry() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /*
    * UPDATE OPERATION
    * ==================
    * Here we are performing the update operation. The proecess is same as the Create operation.
    * We are first getting a database instance using getWritableDatabase() method as the operation we need to perform is a write operation
    * Then we have the contentvalues object with the new values
    *
    * to update the row we use update() method. It takes 4 parameters
    * 1. String -> It is the table name
    * 2. ContentValues -> The new values
    * 3. String -> Here we pass the column name = ?, the column we want to use for putting the where clause
    * 4. String[] -> The values that is to be binded with the where clause
    * */
    public boolean updateUser(int id, String entry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ENTRY, entry);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }


    /*
    * DELETE OPERATION
    * ======================
    *
    * This is the last delete operation.  To delete again we need a writable database using getWritableDatabase()
    * Then we will call the delete method. It takes 3 parameters
    * 1. String -> Table name
    * 2. String -> The where clause passed as columnname = ?
    * 3. String[] -> The values to be binded on the where clause
    * */
    public boolean deleteEntry(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    public void deleteEntryTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

    }

    /**************************************************************************************************************************/
    /**************************************************************************************************************************/

    /****************SETTINGS DB FUNCTIONS ***************************************************************/

    public boolean addSettingEntry(String isWidgetOnorOFF) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(isWidget, isWidgetOnorOFF);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(SETTINGS_TABLE, null, contentValues) != -1;
    }


    public Cursor getSettings() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + SETTINGS_TABLE, null);
    }

    public void deleteSettingsTable() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SETTINGS_TABLE, null, null);

    }

}