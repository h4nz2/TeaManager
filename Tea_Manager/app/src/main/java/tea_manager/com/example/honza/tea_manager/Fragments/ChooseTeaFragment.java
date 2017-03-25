package tea_manager.com.example.honza.tea_manager.Fragments;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tea_manager.com.example.honza.tea_manager.Activities.TeaPickerActivity;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseTeaFragment extends Fragment {

    public ChooseTeaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_tea, container, false);

        Button greenTeaButton = (Button) view.findViewById(R.id.greenTeaButton);
        greenTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickerActivity.class);
                startActivity(intent);
            }
        });

        Button blackTeaButton = (Button) view.findViewById(R.id.bloackTeaButton);
        blackTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickerActivity.class);
                startActivity(intent);
            }
        });

        Button otherButton = (Button) view.findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickerActivity.class);
                startActivity(intent);
            }
        });

        Button anyButton = (Button) view.findViewById(R.id.anyButton);
        anyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickerActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
