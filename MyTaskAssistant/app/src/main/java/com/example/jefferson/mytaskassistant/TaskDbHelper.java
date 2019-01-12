package com.example.jefferson.mytaskassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final int DATABASE_OLD_VERSION = 2;
    public static final String DATABASE_NAME = "Task.db";
    private static final String TAG = "tag";


    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct WordListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " ("
                    + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TaskContract.TaskEntry.TITULO + " TEXT NOT NULL,"
                    + TaskContract.TaskEntry.DETALLE + " TEXT ,"
                    + TaskContract.TaskEntry.FECHA + " TEXT NOT NULL,"
                    + TaskContract.TaskEntry.COMPLETADO + " INTEGER  NOT NULL );");


            fillDatabaseWithData(db);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            Log.d(TAG, "EXCEPTION! " + ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // SAVE USER DATA FIRST!!!
        int oldVersion = 1;
        Log.w(TaskDbHelper.class.getName(),
                "Upgrading database from version " + DATABASE_OLD_VERSION + " to "
                        + DATABASE_VERSION + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    private void fillDatabaseWithData(SQLiteDatabase sqLiteDatabase) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Task[] tasks = new Task[0];
        try {
            tasks = new Task[]{new Task("Hacer proyecto de Android", "Lorem ipsum",
                            format.parse("2019-01-01 10:00:00"),false),
                    new Task("Tarea 2", "Lorem ipsum x2",
                            format.parse("2019-01-01 10:00:00"),false),
                    new Task("Tarea 3", "Lorem ipsum x3",
                            format.parse("2019-01-01 10:00:00"),false)
                    };
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i=0; i < tasks.length; i++) {
            taskFalsa(sqLiteDatabase,tasks[i]);
        }

    }

    private long taskFalsa(SQLiteDatabase db, Task task) {
        return db.insert(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                task.toContentValues());
    }

    public Cursor getAlltaskIncompleted() {
        String columns[] = new String[]{
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.TITULO,
                TaskContract.TaskEntry.FECHA,
                TaskContract.TaskEntry.COMPLETADO};
        String orderBy = TaskContract.TaskEntry.FECHA + " DESC";
        String selection = TaskContract.TaskEntry.COMPLETADO + " = 0";
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().query(
                    TaskContract.TaskEntry.TABLE_NAME,
                    columns,
                    selection,
                    null,
                    null,
                    null,
                    orderBy);
            Log.d(TAG, "The total cursor count is " + cursor.getCount());
        } catch (Exception e){
            Log.e(TAG, "Error", e);
        } finally {
            return cursor;
        }
    }

    public Cursor getAlltaskCompleted() {
        String columns[] = new String[]{
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.TITULO,
                TaskContract.TaskEntry.FECHA,
                TaskContract.TaskEntry.COMPLETADO};
        String orderBy = TaskContract.TaskEntry.FECHA + " DESC";
        String selection = TaskContract.TaskEntry.COMPLETADO + " = 1";
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().query(
                    TaskContract.TaskEntry.TABLE_NAME,
                    columns,
                    selection,
                    null,
                    null,
                    null,
                    orderBy);
            Log.d(TAG, "The total cursor count is " + cursor.getCount());
        } catch (Exception e){
            Log.e(TAG, "Error", e);
        } finally {
            return cursor;
        }
    }


    public Task getTaskById(int taskId) {

        String selection = TaskContract.TaskEntry._ID + " LIKE " + taskId;
        Task task = new Task();
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().query(
                    TaskContract.TaskEntry.TABLE_NAME,
                    null,
                    selection,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            task.setTitulo(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TITULO)));
            task.setDetalle(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.DETALLE)));
            if ((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COMPLETADO)))==1)
                task.setCompletado(true);
            else
                task.setCompletado(false);
            SimpleDateFormat format = new SimpleDateFormat(TaskContract.TaskEntry.DATE_FORMAT);
            String fecha = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.FECHA));
            java.util.Date date= format.parse(fecha);
            task.setFecha(date);
        } catch (Exception e){
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return task;
        }
    }

    public Boolean updateStatus(int taskId, Boolean completado){
        Boolean flag;
      try {
          ContentValues values = new ContentValues();
          if(completado) {
              values.put(TaskContract.TaskEntry.COMPLETADO, 1);
              SimpleDateFormat format = new SimpleDateFormat(TaskContract.TaskEntry.DATE_FORMAT);
              String dateString = format.format( new java.util.Date());
              values.put(TaskContract.TaskEntry.FECHA, dateString);
          }
          else
              values.put(TaskContract.TaskEntry.COMPLETADO,0);
          getWritableDatabase().update(
                  TaskContract.TaskEntry.TABLE_NAME,
                  values,
                  " _id = "+taskId,
                  null);
          flag = true;
      }catch (Exception e){
          flag = false;
      }
      return flag;
    }
}