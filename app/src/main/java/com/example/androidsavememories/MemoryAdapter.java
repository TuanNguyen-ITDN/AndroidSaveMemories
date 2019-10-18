package com.example.androidsavememories;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    public List<Memory> memory;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onClickItemDelete(int position);
        void onClickItemUpdate(int position);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }
    public MemoryAdapter(Runnable mainActivity, List<Memory> memories) {
        memory = memories;
    }

    @NonNull
    @Override
    public MemoryAdapter.MemoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memory, parent, false);
        return new MemoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoryAdapter.MemoryViewHolder holder, final int position) {
        holder.tvMemory.setText(memory.get(position).getDescription());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemDelete(position);

            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemUpdate(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (memory == null) {
            return 0;
        }
        return memory.size();
    }

    class MemoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvMemory;
        Button btnUpdate, btnDelete;

        public MemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMemory = itemView.findViewById(R.id.tvMemory);
            btnUpdate = itemView.findViewById(R.id.updateTask);
            btnDelete = itemView.findViewById(R.id.deleteTask);
        }
    }


}
