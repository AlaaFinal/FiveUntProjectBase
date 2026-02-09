package com.example.AlaaFinal.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AlaaFinal.R;
import com.example.AlaaFinal.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private final ArrayList<Table> tableList;
    private OnTableActionsListener actionsListener;

    public interface OnTableActionsListener {
        void onToggleReserve(Table table, int position);
        void onEdit(Table table, int position);
        void onDelete(Table table, int position);
    }

    public TableAdapter(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }

    public void setOnTableActionsListener(OnTableActionsListener listener) {
        this.actionsListener = listener;
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

        holder.tableName.setText(table.getTableName());
        holder.tableNumber.setText("رقم: " + table.getTableNumber());
        holder.tableSeats.setText("المقاعد: " + table.getSeatsNum() + " | العملاء: " + table.getCustomersNum());

        boolean reserved = table.isReserved();
        holder.tableStatusBadge.setText(reserved ? "محجوزة" : "متاحة");
        GradientDrawable badgeBg = new GradientDrawable();
        badgeBg.setCornerRadius(24);
        badgeBg.setColor(ContextCompat.getColor(holder.itemView.getContext(),
                reserved ? R.color.table_reserved : R.color.table_available));
        holder.tableStatusBadge.setBackground(badgeBg);

        int cardColor = reserved
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.table_reserved)
                : ContextCompat.getColor(holder.itemView.getContext(), R.color.table_available);
        holder.tableCard.setBackgroundColor(cardColor);

        holder.btnToggleReserve.setOnClickListener(v -> {
            if (actionsListener != null) actionsListener.onToggleReserve(table, holder.getAdapterPosition());
        });
        holder.btnEdit.setOnClickListener(v -> {
            if (actionsListener != null) actionsListener.onEdit(table, holder.getAdapterPosition());
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (actionsListener != null) actionsListener.onDelete(table, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tableName, tableNumber, tableSeats, tableStatusBadge;
        Button btnToggleReserve, btnEdit, btnDelete;
        LinearLayout tableCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableName = itemView.findViewById(R.id.tableName);
            tableNumber = itemView.findViewById(R.id.tableNumber);
            tableSeats = itemView.findViewById(R.id.tableSeats);
            tableStatusBadge = itemView.findViewById(R.id.tableStatusBadge);
            btnToggleReserve = itemView.findViewById(R.id.btnToggleReserve);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tableCard = itemView.findViewById(R.id.tableCard);
        }
    }
}
