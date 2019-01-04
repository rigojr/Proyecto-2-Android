package com.example.jefferson.mytaskassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHoler> {

    private ArrayList<Task> mTasksData;
    private Context mContext;

    TaskAdapter (Context context, ArrayList<Task> tasksData ){
        this.mTasksData = tasksData;
        this.mContext = context;
    }

    @Override
    public TaskAdapter.ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoler(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHoler holder, int position) {
        //Get current sport
        Task currentTask = mTasksData.get(position);
        //Populate the textviews with data
        holder.bindTo(currentTask);
    }

    @Override
    public int getItemCount() {
        return mTasksData.size();
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
            mFechaText.setText(dateFormat.format(currentTasK.getFecha()));
            mHoraTetx.setText(timeFormat.format(currentTasK.getFecha()));
            mCompletado.setChecked(currentTasK.getCompletado());


        }

        @Override
        public void onClick(View view) {
            Task currentTask = mTasksData.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentTask.getTitulo());
            detailIntent.putExtra("completado", currentTask.getCompletado());

            mContext.startActivity(detailIntent);
        }
    }
}
