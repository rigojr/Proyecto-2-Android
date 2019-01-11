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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final int DATABASE_OLD_VERSION = 2;
    public static final String DATABASE_NAME = "Task.db";
    private static final String TAG = "tag";
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


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
        /*
        taskFalsa(sqLiteDatabase, new Task("Hacer proyecto de Android", "Lorem ipsum",
                Date.valueOf("2019-01-10 14:00:00"), false));
        taskFalsa(sqLiteDatabase, new Task("Trotar", "Lorem ipsum x2",
                Date.valueOf("2019-01-11 18:00:00"), false));
                */
        Task[] tasks = {new Task("Hacer proyecto de Android", "Lorem ipsum",
                new java.util.Date(),false),
                new Task("Tarea 2", "Lorem ipsum x2",
                        new java.util.Date(),false),
                new Task("Tarea 3", "Lorem ipsum x3",
                        new java.util.Date(),false)
                };
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
/*
    public ArrayList<Task> getAlltaskIncompleted(String position) {
        String columns[] = new String[]{TaskContract.TaskEntry.TITULO,
                TaskContract.TaskEntry.FECHA,
                TaskContract.TaskEntry.COMPLETADO};
        String orderBy = TaskContract.TaskEntry.FECHA + " DESC";
        ArrayList<Task> tasks = new ArrayList();
        Cursor cursor = null;
        try {
            cursor = getReadableDatabase().query(
                    TaskContract.TaskEntry.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    orderBy);
            while (cursor.moveToNext()){
                Task task = new Task();
                task.setTitulo(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TITULO)));
                task.setDetalle(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.DETALLE)));
                if ((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.TITULO))) == 1)
                    task.setCompletado(true);
                else
                    task.setCompletado(false);
                String date = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.FECHA));
                /*
                //SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
                java.util.Date parsed = dateFormat.parse(date);
                Date dateinString = new Date(parsed.getTime());
                task.setFecha(dateinString);
                */
/*
                task.setFecha(new java.util.Date());
                tasks.add(task);
        }
        } catch (Exception e){
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return tasks;
        }
    }
*/
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
            if ((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.TITULO)))==1)
                task.setCompletado(true);
            else
                task.setCompletado(false);
            java.util.Date utilDate = new java.util.Date(cursor.getColumnIndex(TaskContract.TaskEntry.FECHA));
            task.setFecha(utilDate);
        } catch (Exception e){
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return task;
        }
    }

    /**
     * Método para actualizar el estado de una tarea directamente en la base de datos.
     * @param taskId id de la tarea
     * @param status estatus de la tarea
     * @return
     */
    public int updateStatusTask(int taskId, boolean status){
        int mNumberOfRowsUpdated = -1;
        try {

            if( this.mWritableDB == null){
                mWritableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            if(status){
                values.put(TaskContract.TaskEntry.COMPLETADO, 0);
            }else{
                values.put(TaskContract.TaskEntry.COMPLETADO, 1);
            }
            mNumberOfRowsUpdated = mWritableDB.update(TaskContract.TaskEntry.TABLE_NAME,
                    values,
                    TaskContract.TaskEntry._ID + " = ?",
                    new String[]{String.valueOf(taskId)});
        }catch (Exception e){
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }
}