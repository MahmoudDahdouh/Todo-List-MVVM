package com.mdstudio.todo.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mdstudio.title.R;
import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Todo;

public class TodoAdapter extends ListAdapter<Todo, TodoAdapter.TodoViewHolder> {

    private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
    private AppRepository mRepository;
    private OnItemClickListener onItemClickListener;


    public TodoAdapter(AppRepository mRepository) {
        super(DIFF_CALLBACK);
        this.mRepository = mRepository;
    }

    public Todo getTodoAt(int position) {
        return getItem(position);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String name, int id);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {

        // Declare your views
        private TextView title;
        private CheckBox status;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            // inflate the view
            title = itemView.findViewById(R.id.li_todo_title);
            status = itemView.findViewById(R.id.li_todo_status);
            status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        getItem(getAdapterPosition()).setStatus(1);
                        title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        mRepository.updateTodo(getItem(getAdapterPosition()));

                    } else {
                        title.setPaintFlags(title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        getItem(getAdapterPosition()).setStatus(0);
                        mRepository.updateTodo(getItem(getAdapterPosition()));

                    }
                }
            });
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
            title.setText(getItem(position).getTitle());

            if (getItem(position).getStatus() > 0) {
                status.setChecked(true);
                title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                title.setPaintFlags(title.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                status.setChecked(false);
            }
        }
    }
}
