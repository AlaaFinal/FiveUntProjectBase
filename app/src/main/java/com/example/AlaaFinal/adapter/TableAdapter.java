package com.example.AlaaFinal.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AlaaFinal.R;
import com.example.AlaaFinal.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    ArrayList<Table> tableList;

    public TableAdapter(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Table table = tableList.get(position);

        holder.tableName.setText(table.tableName);
        holder.tableNumber.setText("Table No: " + table.tableNumber);

        // مكان اللون الثابت حسب رقم الطاولة بدون مصفوفة
        int color = Color.rgb(
                (table.tableNumber * 40) % 256,
                (table.tableNumber * 70) % 256,
                (table.tableNumber * 100) % 256
        );

        holder.tableCard.setBackgroundColor(color);

        holder.btnDelete.setOnClickListener(v -> {
            tableList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tableList.size());
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tableName, tableNumber;
        Button btnDelete;
        LinearLayout tableCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tableName = itemView.findViewById(R.id.tableName);
            tableNumber = itemView.findViewById(R.id.tableNumber);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tableCard = itemView.findViewById(R.id.tableCard); // المهم هنا ✔
        }
    }
}
