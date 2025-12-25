package com.example.AlaaFinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
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

    RecyclerView tableRecycler;
    TableAdapter tableAdapter;
    ArrayList<Table> tableList;

    Button btaddtable, btsave;
    ImageButton btback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_activity);

        tableRecycler = findViewById(R.id.tableRecycler);
        btaddtable = findViewById(R.id.btaddtable);
        btsave = findViewById(R.id.btsave);
        btback = findViewById(R.id.btback);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = prefs.getString("tables", null);
        Type type = new TypeToken<ArrayList<Table>>() {}.getType();

        if (json != null) {
            tableList = gson.fromJson(json, type);
        } else {
            tableList = new ArrayList<>();
            tableList.add(new Table("Table 1", 1, 4, 0, false));
            tableList.add(new Table("Table 2", 2, 4, 0, false));
            tableList.add(new Table("Table 3", 3, 4, 0, false));
        }

        tableAdapter = new TableAdapter(tableList);
        tableRecycler.setLayoutManager(new LinearLayoutManager(this));
        tableRecycler.setAdapter(tableAdapter);

        btaddtable.setOnClickListener(v -> {
            int newId = tableList.size() + 1;
            tableList.add(new Table("Table " + newId, newId, 4, 0, false));
            tableAdapter.notifyItemInserted(tableList.size() - 1);
        });

        btsave.setOnClickListener(v -> {
            String jsonSave = gson.toJson(tableList);
            prefs.edit().putString("tables", jsonSave).apply();
            Toast.makeText(this, "Tables saved!", Toast.LENGTH_SHORT).show();
            finish();
        });

        btback.setOnClickListener(v -> finish());
    }
}
