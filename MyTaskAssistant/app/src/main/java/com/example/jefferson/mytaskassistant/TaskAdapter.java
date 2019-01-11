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
import android.widget.TextView;

import java.text.DateFormat;
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
            Date utilDate = new Date(cursor.getColumnIndex(TaskContract.TaskEntry.FECHA));
            mFechaText.setText(dateFormat.format(utilDate));
            mHoraTetx.setText(timeFormat.format(utilDate));
            if ((cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.TITULO)))==1)
                mCompletado.setChecked(true);
            else
                mCompletado.setChecked(false);
        }

        @Override
        public void onClick(View view) {

            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(mContext);
            DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(mContext);
            Cursor cursor = mCursor;
            cursor.moveToPosition(getAdapterPosition());

            Task task = mDB.getTaskById(cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry._ID)));
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", task.getTitulo());
            detailIntent.putExtra("completado", task.getCompletado());
            detailIntent.putExtra("fecha", dateFormat.format(task.getFecha()));
            detailIntent.putExtra("hora", timeFormat.format(task.getFecha()));
            detailIntent.putExtra("detalle", task.getDetalle());

            mContext.startActivity(detailIntent);
        }

    }
}
