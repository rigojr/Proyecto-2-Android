package com.example.jefferson.mytaskassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Task.db";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " ("
                + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TaskContract.TaskEntry.TITULO + " TEXT NOT NULL,"
                + TaskContract.TaskEntry.DETALLE + " TEXT ,"
                + TaskContract.TaskEntry.FECHA+ " TEXT NOT NULL,"
                + TaskContract.TaskEntry.COMPLETADO + " INTEGER  NOT NULL"
                +")");
        ContentValues values = new ContentValues();

        dataFalsa(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void dataFalsa(SQLiteDatabase sqLiteDatabase) {
        taskFalsa(sqLiteDatabase, new Task("Hacer proyecto de Android", "Lorem ipsum",
                Date.valueOf("2019-01-10 14:00:00"), false));
        taskFalsa(sqLiteDatabase, new Task("Trotar", "Lorem ipsum x2",
                Date.valueOf("2019-01-11 18:00:00"), false));
    }

    private long taskFalsa(SQLiteDatabase db, Task task) {
        return db.insert(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                task.toContentValues());
    }

    public Cursor getAlltask() {
        String columns[] = new String[]{TaskContract.TaskEntry.TITULO,
                TaskContract.TaskEntry.FECHA,
                TaskContract.TaskEntry.COMPLETADO};
        Cursor c = getReadableDatabase().query(
                TaskContract.TaskEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        return c;
    }

    public Cursor getTaskById(String taskId) {
        String selection = TaskContract.TaskEntry._ID + " LIKE ?";
        String orderBy = TaskContract.TaskEntry.FECHA + " DESC";
        Cursor c = getReadableDatabase().query(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                selection,
                new String[]{taskId},
                null,
                null,
                orderBy);
        return c;
    }
}