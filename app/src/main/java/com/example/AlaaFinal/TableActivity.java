package com.example.AlaaFinal;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AlaaFinal.adapter.TableAdapter;
import com.example.AlaaFinal.model.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_TABLES = "tables";

    RecyclerView tableRecycler;
    TableAdapter tableAdapter;
    ArrayList<Table> tableList;
    Button btaddtable, btsave;
    ImageButton btback;

    SharedPreferences prefs;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_activity);

        tableRecycler = findViewById(R.id.tableRecycler);
        btaddtable = findViewById(R.id.btaddtable);
        btsave = findViewById(R.id.btsave);
        btback = findViewById(R.id.btback);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadTables();

        tableAdapter = new TableAdapter(tableList);
        tableAdapter.setOnTableActionsListener(new TableAdapter.OnTableActionsListener() {
            @Override
            public void onToggleReserve(Table table, int position) {
                table.setReserved(!table.isReserved());
                tableAdapter.notifyItemChanged(position);
            }

            @Override
            public void onEdit(Table table, int position) {
                showEditTableDialog(table, position);
            }

            @Override
            public void onDelete(Table table, int position) {
                new AlertDialog.Builder(TableActivity.this)
                        .setTitle("حذف الطاولة")
                        .setMessage("هل تريد حذف \"" + table.getTableName() + "\"؟")
                        .setPositiveButton("حذف", (d, w) -> {
                            tableList.remove(position);
                            tableAdapter.notifyItemRemoved(position);
                        })
                        .setNegativeButton("إلغاء", null)
                        .show();
            }
        });

        tableRecycler.setLayoutManager(new LinearLayoutManager(this));
        tableRecycler.setAdapter(tableAdapter);

        btaddtable.setOnClickListener(v -> showAddTableDialog());
        btsave.setOnClickListener(v -> saveAndFinish());
        btback.setOnClickListener(v -> finish());
    }

    private void loadTables() {
        String json = prefs.getString(KEY_TABLES, null);
        Type type = new TypeToken<ArrayList<Table>>() {}.getType();
        if (json != null) {
            try {
                tableList = gson.fromJson(json, type);
                if (tableList == null) tableList = new ArrayList<>();
                for (Table t : tableList) if (t.id == null) t.id = "t_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
            } catch (Exception e) {
                tableList = createDefaultTables();
            }
        } else {
            tableList = createDefaultTables();
        }
    }

    private ArrayList<Table> createDefaultTables() {
        ArrayList<Table> list = new ArrayList<>();
        list.add(new Table("طاولة ١", 1, 4, 0, false));
        list.add(new Table("طاولة ٢", 2, 4, 0, false));
        list.add(new Table("طاولة ٣", 3, 4, 0, false));
        return list;
    }

    private int nextTableNumber() {
        int max = 0;
        for (Table t : tableList) if (t.tableNumber > max) max = t.tableNumber;
        return max + 1;
    }

    private void showAddTableDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_table_edit, null);
        EditText etName = dialogView.findViewById(R.id.dialog_et_name);
        EditText etSeats = dialogView.findViewById(R.id.dialog_et_seats);
        EditText etCustomers = dialogView.findViewById(R.id.dialog_et_customers);
        CheckBox chkReserved = dialogView.findViewById(R.id.dialog_chk_reserved);

        int nextNum = nextTableNumber();
        etName.setText("طاولة " + nextNum);
        etSeats.setText("4");
        etCustomers.setText("0");
        etSeats.setInputType(InputType.TYPE_CLASS_NUMBER);
        etCustomers.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setTitle("إضافة طاولة")
                .setView(dialogView)
                .setPositiveButton("إضافة", (d, w) -> {
                    String name = etName.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(this, "أدخل اسم الطاولة", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int seats = 4;
                    int customers = 0;
                    try { seats = Integer.parseInt(etSeats.getText().toString()); } catch (Exception e) {}
                    try { customers = Integer.parseInt(etCustomers.getText().toString()); } catch (Exception e) {}
                    if (seats < 1) seats = 1;
                    if (customers < 0) customers = 0;

                    Table newTable = new Table(name, nextNum, seats, customers, chkReserved.isChecked());
                    tableList.add(newTable);
                    tableAdapter.notifyItemInserted(tableList.size() - 1);
                    Toast.makeText(this, "تمت إضافة الطاولة", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    private void showEditTableDialog(Table table, int position) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_table_edit, null);
        EditText etName = dialogView.findViewById(R.id.dialog_et_name);
        EditText etSeats = dialogView.findViewById(R.id.dialog_et_seats);
        EditText etCustomers = dialogView.findViewById(R.id.dialog_et_customers);
        CheckBox chkReserved = dialogView.findViewById(R.id.dialog_chk_reserved);

        etName.setText(table.getTableName());
        etSeats.setText(String.valueOf(table.getSeatsNum()));
        etCustomers.setText(String.valueOf(table.getCustomersNum()));
        chkReserved.setChecked(table.isReserved());
        etSeats.setInputType(InputType.TYPE_CLASS_NUMBER);
        etCustomers.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setTitle("تعديل الطاولة")
                .setView(dialogView)
                .setPositiveButton("حفظ", (d, w) -> {
                    String name = etName.getText().toString().trim();
                    if (name.isEmpty()) {
                        Toast.makeText(this, "أدخل اسم الطاولة", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int seats = table.getSeatsNum();
                    int customers = table.getCustomersNum();
                    try { seats = Integer.parseInt(etSeats.getText().toString()); } catch (Exception e) {}
                    try { customers = Integer.parseInt(etCustomers.getText().toString()); } catch (Exception e) {}
                    if (seats < 1) seats = 1;
                    if (customers < 0) customers = 0;

                    table.setTableName(name);
                    table.setSeatsNum(seats);
                    table.setCustomersNum(customers);
                    table.setReserved(chkReserved.isChecked());
                    tableAdapter.notifyItemChanged(position);
                    Toast.makeText(this, "تم حفظ التعديلات", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }

    private void saveAndFinish() {
        String jsonSave = gson.toJson(tableList);
        prefs.edit().putString(KEY_TABLES, jsonSave).apply();
        Toast.makeText(this, "تم حفظ الطاولات", Toast.LENGTH_SHORT).show();
        finish();
    }
}
