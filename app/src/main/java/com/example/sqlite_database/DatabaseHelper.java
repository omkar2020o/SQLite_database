package com.example.sqlite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "student.db";
    public static final String TABLE_NAME= "student_table";
    public static final String COL_1= "ID";
    public static final String COL_2= "NAME";
    public static final String COL_3= "SURNAME";
    public static final String COL_4= "MARKS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("create Table student_table(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                 " NAME TEXT, " +
                 "SURNAME TEXT," +
                 "MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
           db.execSQL("drop table if exists student_table");
           onCreate(db);
    }

    public boolean insertData(String name,String surname, String marks){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("SURNAME",surname);
        contentValues.put("MARKS",marks);
       long result=db.insert("student_table",null,contentValues);
       if(result==-1)
       {
           return false;
       }
       else
           return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from student_table",null);
        return res;
    }

    public boolean updateData(String id,String name,String surname, String marks)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("NAME",name);
        contentValues.put("SURNAME",surname);
        contentValues.put("MARKS",marks);
        db.update("student_table",contentValues,"ID = ?",new String[]{id});
        return true;
    }

    public Integer DeleteData(String id)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete("student_table","ID= ?",new String[]{id});
    }
}
