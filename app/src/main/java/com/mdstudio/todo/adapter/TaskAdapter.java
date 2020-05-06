package com.mdstudio.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mdstudio.title.R;
import com.mdstudio.todo.model.Task;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
    private OnItemClickListener onItemClickListener;

    public TaskAdapter() {
        super(DIFF_CALLBACK);
    }

    // get Task at
    public Task getNoteAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String name, int id);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Declare your views
        private TextView name;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            // inflate the view
            name = itemView.findViewById(R.id.li_task_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(
                            getItem(getAdapterPosition()).getTitle(),
                            getItem(getAdapterPosition()).getId());
                }
            });

        }

        private void bind(int position) {
            // Bind data
            name.setText(getItem(position).getTitle());
        }
    }
}
