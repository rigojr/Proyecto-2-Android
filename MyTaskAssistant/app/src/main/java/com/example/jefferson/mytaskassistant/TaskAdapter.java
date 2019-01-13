package com.example.jefferson.mytaskassistant;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHoler> {


    Context mContext;
    Cursor mCursor;
    private TaskDbHelper mDB;

    public TaskAdapter (Context context, Cursor cursor,TaskDbHelper DB) {

        mContext = context;
        mCursor = cursor;
        mDB = DB;

    }

    @Override
    public TaskAdapter.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoler(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHoler holder, int position) {
        mCursor.moveToPosition(position);
        holder.bindCursor(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class ViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTituloText;
        private TextView mFechaText;
        private TextView mHoraTetx;
        private CheckBox mCompletado;

        ViewHoler (View itemView) {
            super(itemView);

            //Initialize the views
            mTituloText = (TextView)itemView.findViewById(R.id.titulo);
            mFechaText  = (TextView)itemView.findViewById(R.id.fecha);
            mHoraTetx = (TextView)itemView.findViewById(R.id.hora);
            mCompletado = (CheckBox)itemView.findViewById(R.id.checkBox);
            itemView.setOnClickListener(this);
        }

        void bindTo(Task currentTasK){
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
            mTituloText.setText(currentTasK.getTitulo());
            //mFechaText.setText(dateFormat.format(currentTasK.getFecha()));
            //mHoraTetx.setText(timeFormat.format(currentTasK.getFecha()));
            mCompletado.setChecked(currentTasK.getCompletado());
        }

        public void bindCursor(Cursor cursor) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
            mTituloText.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.TaskEntry.TITULO)));

            //dando formato a la fecha
            SimpleDateFormat format = new SimpleDateFormat(TaskContract.TaskEntry.DATE_FORMAT);
            String fecha = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.FECHA));
            Date utilDate = null;
            try {
                utilDate = format.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mFechaText.setText(dateFormat.format(utilDate));

            mHoraTetx.setText(timeFormat.format(utilDate));

            mCompletado.setOnCheckedChangeListener(null);
            if ((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COMPLETADO)))==1)
                mCompletado.setChecked(true);
            else
                mCompletado.setChecked(false);
                mCompletado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int originalPosition = mCursor.getPosition();
                    mCursor.moveToPosition(getAdapterPosition());
                    mDB.updateStatus(mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID)),mCompletado.isChecked());
                    mCursor.moveToPosition(originalPosition);
                }


            });

        }

        @Override
        public void onClick(View view) {

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
            int originalPostion = mCursor.getPosition();
            mCursor.moveToPosition(getAdapterPosition());

            Task task = mDB.getTaskById(mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID)));

            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", task.getTitulo());
            detailIntent.putExtra("completado", task.getCompletado());
            detailIntent.putExtra("fecha", dateFormat.format(task.getFecha()));
            detailIntent.putExtra("hora", timeFormat.format(task.getFecha()));
            detailIntent.putExtra("detalle", task.getDetalle());
            detailIntent.putExtra("id", String.valueOf(mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry._ID))));
            //detailIntent.putExtra("id", TaskContract.TaskEntry._ID);

            mCursor.moveToPosition(originalPostion);
            mContext.startActivity(detailIntent);


        }
        

    }


}
