package tea_manager.com.example.honza.tea_manager.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import tea_manager.com.example.honza.tea_manager.Activities.ChooseTeaActivity;
import tea_manager.com.example.honza.tea_manager.Activities.MainActivity;
import tea_manager.com.example.honza.tea_manager.Activities.TeaPickedActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeaPickedFragment extends Fragment {
    private List<Tea> mTeaList;

    private TextView teaNameView;
    private TextView teaTypeView;
    private TextView teaInfusionsView;

    public TeaPickedFragment() {
    }

    public static TeaPickedFragment newInstance(String keyType, Tea.teaType teaType) {

        Bundle args = new Bundle();
        args.putSerializable(keyType, teaType);

        TeaPickedFragment fragment = new TeaPickedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_picked, container, false);
        teaNameView = (TextView) view.findViewById(R.id.teaNameText);
        teaTypeView = (TextView) view.findViewById(R.id.teaTypeText);
        teaInfusionsView = (TextView) view.findViewById(R.id.teaInfusionsText);

        DBteaCRUD dBteaCRUD = new DBteaCRUD(getActivity());
        Bundle args = getArguments();

        Tea.teaType chosenType = (Tea.teaType) args.getSerializable(ChooseTeaFragment.TEA_TYPE_CHOSEN);
        if(chosenType != null)
            mTeaList = dBteaCRUD.getTeaGroup(chosenType);
        else
            mTeaList = dBteaCRUD.getAllTeas();
        chooseTea(mTeaList);

        Button thanksButton = (Button) view.findViewById(R.id.thanksButton);
        thanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Button chooseAnotherbutton = (Button) view.findViewById(R.id.chooseAnotherButton);
        chooseAnotherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseTeaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return view;
    }

    public void chooseTea(List<Tea> teaList){
        int chosenTeaPosition = (int) (Math.random() * teaList.size());
        Tea chosenTea = mTeaList.get(chosenTeaPosition);

        teaNameView.setText(chosenTea.getName());
        teaTypeView.setText(chosenTea.getType().toString());
        teaInfusionsView.setText("Infusions: " + Integer.toString(chosenTea.getInfusions()));
    }
}
