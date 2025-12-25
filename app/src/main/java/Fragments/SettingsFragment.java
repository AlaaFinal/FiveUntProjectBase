package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.AlaaFinal.R;
import com.example.AlaaFinal.TableActivity;


public class SettingsFragment extends Fragment {
View view;
CardView bttabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_settings, container, false);

        bttabel = view.findViewById(R.id.bttabel);
        bttabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TableActivity.class);
                startActivity(i);
            }
        });



        return  view;
    }
}