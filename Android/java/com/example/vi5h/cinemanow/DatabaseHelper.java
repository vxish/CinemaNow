package com.example.vi5h.cinemanow;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "CinemaNow.db";

    //Cinema Table
    public static final String TABLE_C = "Cinema_table";
    public static final String COL_1_C = "Cinema_ID";
    public static final String COL_2_C = "Area";

    //Screen - 1 Table
    public static final String TABLE_S1 = "Screen_1";
    public static final String COL_1_S1 = "Screen1_ID";
    public static final String COL_2_S1 = "Show_Time";

    //Screen - 2 Table
    public static final String TABLE_S2 = "Screen_2";
    public static final String COL_1_S2 = "Screen2_ID";
    public static final String COL_2_S2 = "Show_Time";

    //Movies Table
    public static final String TABLE_M = "Movie_table";
    public static final String COL_1_M = "Movie_ID";
    public static final String COL_2_M = "Name";
    public static final String COL_3_M = "Plot";
    public static final String COL_4_M = "Duration";

    //User Table
    public static final String TABLE_U = "User_table";
    public static final String COL_1_U = "User_ID";
    public static final String COL_2_U = "Name";
    public static final String COL_3_U = "Username";
    public static final String COL_4_U = "Password";
    public static final String COL_5_U = "User_Type";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 35);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_C + "(Cinema_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Area TEXT)");
        db.execSQL("create table " + TABLE_M + "(Movie_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name TEXT, Plot TEXT, Duration TEXT)");
        db.execSQL("create table " + TABLE_S1 + "(Screen1_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Show_Time TEXT NOT NULL, " +
                "FK_Movie_ID INTEGER NOT NULL, FOREIGN KEY (FK_Movie_ID) REFERENCES Movie_table(Movie_ID))");
        db.execSQL("create table " + TABLE_S2 + "(Screen2_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Show_Time TEXT NOT NULL, " +
                "FK_Movie_ID INTEGER NOT NULL, FOREIGN KEY (FK_Movie_ID) REFERENCES Movie_table(Movie_ID))");
        db.execSQL("create table " + TABLE_U + "(User_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Name TEXT, Username TEXT, Password TEXT," +
                "User_Type TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On upgrade drop older table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_C);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_S1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_S2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_M);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_U);

        //Create new tables
        onCreate(db);
    }

    public boolean registerUser(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_U, user.getName());
        contentValues.put(COL_3_U, user.getUsername());
        contentValues.put(COL_4_U, user.getPass());
        contentValues.put(COL_5_U, user.getUserType());
        long result = db.insert(TABLE_U, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getLocation()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Cinema_table", null);
        return data;
    }

    public Cursor getLocationId(String area)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_1_C + " FROM " + TABLE_C +
                " WHERE " + COL_2_C + " = '" + area + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public List<MovieModel> getMovieInfo()
    {
        List<MovieModel> movies = new ArrayList<MovieModel>();
        String selectQuery = "SELECT * FROM " + TABLE_M;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                MovieModel movieModel = new MovieModel();
                movieModel.setName((c.getString(c.getColumnIndex(COL_2_M))));
                movieModel.setDuration((c.getString(c.getColumnIndex(COL_4_M))));
                movieModel.setPlot((c.getString(c.getColumnIndex(COL_3_M))));
                movies.add(movieModel);

            } while (c.moveToNext());
        }
       return movies;
    }

    public List<ScreenModel> getScreenOneInfo(){
        List<ScreenModel> screen1 = new ArrayList<ScreenModel>();
        String selectQuery = "select Movie_table.Name, Screen_1.Show_Time from Screen_1 " +
                "left join Movie_table on " +
                "Screen_1.FK_Movie_ID=Movie_table.Movie_ID;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                ScreenModel screenModel = new ScreenModel();
                screenModel.setMovieName((c.getString(c.getColumnIndex(COL_2_M))));
                screenModel.setShowTime((c.getString(c.getColumnIndex(COL_2_S1))));
                screen1.add(screenModel);
            } while (c.moveToNext());
        }
        return screen1;
    }

    public List<ScreenModel> getScreenTwoInfo(){
        List<ScreenModel> screen2 = new ArrayList<ScreenModel>();
        String selectQuery = "select Movie_table.Name, Screen_2.Show_Time from Screen_2 \n" +
                "left join Movie_table on \n" +
                "Screen_2.FK_Movie_ID=Movie_table.Movie_ID;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do {
                ScreenModel screenModel = new ScreenModel();
                screenModel.setMovieName((c.getString(c.getColumnIndex(COL_2_M))));
                screenModel.setShowTime((c.getString(c.getColumnIndex(COL_2_S2))));
                screen2.add(screenModel);
            } while (c.moveToNext());
        }
        return screen2;
    }

    public String verifyLogin(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Username, Password from "+TABLE_U;
        Cursor cursor = db.rawQuery(query, null);
        String u, p;
        p = "Not found";
        if (cursor.moveToFirst()){
            do
            {
                u = cursor.getString(0);
                if (u.equals(username))
                {
                    p = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return p;
    }

    public Cursor fetchUserType(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select User_Type from User_Table Where Username = '"+username+"'";
        Cursor cursor = db.rawQuery(query, null);
        String t;
        if(cursor.moveToFirst()){
            t = cursor.getString(0);
        }
        return cursor;
    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " + TABLE_U;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getUserID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select " + COL_1_U + " from " + TABLE_U + " where " + COL_2_U + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "update " + TABLE_U + " set " + COL_2_U + " = '" + newName +
                "' where " + COL_1_U + " = '" + id + "'" + " and " + COL_2_U + " = '" + oldName + "'";
        db.execSQL(query);
    }

    public void deleteUser(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + TABLE_U + " where " + COL_1_U + " = '" + id + "'" +
                " and " + COL_2_U + " = '" + name + "'";
        db.execSQL(query);
    }
}

