package tea_manager.com.example.honza.tea_manager.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tea_manager.com.example.honza.tea_manager.Activities.TeaPickedActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseTeaFragment extends Fragment {
    public static final String TEA_TYPE_CHOSEN = "teaTypeChosen";

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
                Intent intent = new Intent(v.getContext(), TeaPickedActivity.class);
                intent.putExtra(TEA_TYPE_CHOSEN, Tea.teaType.Green);
                startActivity(intent);
            }
        });

        Button blackTeaButton = (Button) view.findViewById(R.id.blackTeaButton);
        blackTeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickedActivity.class);
                intent.putExtra(TEA_TYPE_CHOSEN, Tea.teaType.Black);
                startActivity(intent);
            }
        });

        Button otherButton = (Button) view.findViewById(R.id.otherButton);
        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickedActivity.class);
                intent.putExtra(TEA_TYPE_CHOSEN, Tea.teaType.Other);
                startActivity(intent);
            }
        });

        Button anyButton = (Button) view.findViewById(R.id.anyButton);
        anyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeaPickedActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

}
