package project226.a000webhostapp.com.feelsecure;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDB.db";
    public static final String TABLE_MEMBERS = "members";
    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String USER_ID = "user_id";
    public static final String PRESENT_TOTAL= "presentTotal";
    public static final String ABSENT_TOTAL = "absentTotal";
    public static final String DATE = "Date";
    public static final String  TABLE_DISTRICT ="district_table";
    public static final String DISTRICT_ID ="district_id";
    public static final String DISTRICT_NAME = "district_name";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + TABLE_MEMBERS +"( user_id INT PRIMARY KEY , " +
                " user_name VARCHAR(30), email VARCHAR(50),key VARCHAR(5), flag INT(2) DEFAULT 0);";
        db.execSQL(query);
        String query1 = "CREATE TABLE " + TABLE_ATTENDANCE + "(" +
                USER_ID + " INT NOT NULL ," +
                PRESENT_TOTAL + " int ," + ABSENT_TOTAL + " INT ,"+ DATE +" DATE "+ ");";
        db.execSQL(query1);
        String query2 = "CREATE TABLE " + TABLE_DISTRICT + " ( " + DISTRICT_ID +" INT , "
                            + DISTRICT_NAME + " VARCHAR (25) );";
        db.execSQL(query2);
        String query4 ="CREATE TABLE user_list (key VARCHAR(6) , userName VARCHAR (25));";
        db.execSQL(query4);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISTRICT);
        db.execSQL("DROP TABLE IF EXISTS user_list");
        onCreate(db);
    }

    public  void removeUser(String key){
        SQLiteDatabase db = getWritableDatabase();
        String query ="DELETE FROM user_list WHERE key = '"+key+ "';";
        db.execSQL(query);
        db.close();

    }
    public void  insertUsers(String key,String user_name){
        Log.d("inside database",key+user_name);
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO user_list VALUES ( '" + key +"' , " +"'" + user_name + "' );";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<String> getUserNameList(){
        ArrayList<String> user = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM  user_list WHERE 1;";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            for (int i=0;i<recordSet.getCount();i++ ) {
                user.add(recordSet.getString(recordSet.getColumnIndex("userName")));
                recordSet.moveToNext();
            }

        }

        db.close();
        return user;
    }
    public List<String> getKeyList(){
        List<String> user = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM  user_list WHERE 1;";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            for (int i=0;i<recordSet.getCount();i++ ) {
                user.add(recordSet.getString(recordSet.getColumnIndex("key")));
                recordSet.moveToNext();
            }

        }

        db.close();
        return user;
    }

    //Add a new row to the database
    public void addMember(int userId,String userName,String email,String key){
        SQLiteDatabase db = getWritableDatabase();
        String query3="INSERT INTO "+TABLE_MEMBERS+" VALUES( "+userId+" , "+"'"+userName+"'"+" , "
                +"'"+email+"' , '"+key+"' , 1 );";
        db.execSQL(query3);
        db.close();
    }
    public int checkMember(){
        int flag;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            flag=recordSet.getInt(recordSet.getColumnIndex("flag"));}
        else
            flag=0;
        db.close();
        return flag;

    }
    public int getUserId(){
        int user_id;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            user_id=recordSet.getInt(recordSet.getColumnIndex("user_id"));}
        else
            user_id=0;
        db.close();
        return user_id;
    }
    public String getUserName(){
        String user_name;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            user_name=recordSet.getString(recordSet.getColumnIndex("user_name"));}
        else
            user_name=null;
        db.close();
        return user_name;
    }
    public String getUserEmail(){
        String user_name;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            user_name=recordSet.getString(recordSet.getColumnIndex("email"));}
        else
            user_name=null;
        db.close();
        return user_name;
    }
    public String getUserKey(){
        String key;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_MEMBERS + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            key=recordSet.getString(recordSet.getColumnIndex("key"));}
        else
            key=null;
        db.close();
        return key;
    }
    public void logout(){
        SQLiteDatabase db =getWritableDatabase();
        String query ="DELETE FROM "+ TABLE_MEMBERS + " WHERE 1";
        db.execSQL(query);
        String query1 ="DELETE FROM user_list WHERE 1";
        db.execSQL(query1);
        db.close();
    }
    public  void insertDistrict(int i ,String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "INSERT INTO " + TABLE_DISTRICT + " VALUES ( " + i +" , " +"'" + name + "' );";
        db.execSQL(query);
        db.close();
    }
    public int getDistrictId(String name){
        int district_id;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DISTRICT + " WHERE " + DISTRICT_NAME +" = '"+ name +"' ;";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        if(recordSet.getCount()!=0){
            recordSet.moveToLast();
            district_id=recordSet.getInt(recordSet.getColumnIndex(DISTRICT_ID));}
        else
            district_id=0;
        db.close();
        Log.d("MainActivity1","district id :"+ district_id);
        return district_id;
    }
    /*//Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\";");
    }

    // this is goint in record_TextView in the Main activity.*/
   /* public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("productname")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("productname"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }*/

}