package Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.AlaaFinal.R;
import com.example.AlaaFinal.TableActivity;
import com.example.AlaaFinal.model.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_TABLES = "tables";

    private TextView tvTotal, tvAvailable, tvReserved;
    private Button btnManageTables;
    private final Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotal = view.findViewById(R.id.home_tv_total);
        tvAvailable = view.findViewById(R.id.home_tv_available);
        tvReserved = view.findViewById(R.id.home_tv_reserved);
        btnManageTables = view.findViewById(R.id.home_btn_manage_tables);

        btnManageTables.setOnClickListener(v -> {
            if (getContext() != null) {
                startActivity(new Intent(getContext(), TableActivity.class));
            }
        });
        refreshSummary();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSummary();
    }

    private void refreshSummary() {
        if (getContext() == null) return;
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, 0);
        String json = prefs.getString(KEY_TABLES, null);
        Type type = new TypeToken<ArrayList<Table>>() {}.getType();

        int total = 0, available = 0, reserved = 0;
        if (json != null) {
            try {
                ArrayList<Table> list = gson.fromJson(json, type);
                if (list != null) {
                    total = list.size();
                    for (Table t : list) {
                        if (t.isReserved()) reserved++;
                        else available++;
                    }
                }
            } catch (Exception ignored) {}
        }

        tvTotal.setText(String.valueOf(total));
        tvAvailable.setText(String.valueOf(available));
        tvReserved.setText(String.valueOf(reserved));
    }
}
