package tea_manager.com.example.honza.tea_manager.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import tea_manager.com.example.honza.tea_manager.Activities.TeaDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeaDetailFragment extends Fragment {
    public static final int MIN_INFUSIONS = 1;
    public static final int MAX_INFUSIONS = 3;

    private int mMode;
    private FragmentListener mFragmentListener;
    private Tea mTea;

    private Button submitButton;
    private EditText nameEdit;
    private Spinner typeSpinner;
    private NumberPicker infusionsPicker;


    public TeaDetailFragment() {
        // Required empty public constructor
    }

    public static TeaDetailFragment newInstance(String key, int mode) {
        
        Bundle args = new Bundle();
        args.putInt(key, mode);
        
        TeaDetailFragment fragment = new TeaDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static TeaDetailFragment newInstance(String keyMode, int mode, String keyTea, Tea tea) {

        Bundle args = new Bundle();
        args.putInt(keyMode, mode);
        args.putSerializable(keyTea, tea);

        TeaDetailFragment fragment = new TeaDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof FragmentListener)) throw new AssertionError();
        mFragmentListener = (FragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tea_detail, container, false);

        //get all widgets
        submitButton = (Button) view.findViewById(R.id.submitButton);
        nameEdit = (EditText) view.findViewById(R.id.teaNameEditText);
        typeSpinner = (Spinner) view.findViewById(R.id.teaTypeSpinner);
        //populate spinner
        typeSpinner.setAdapter(new ArrayAdapter<Tea.teaType>(
                this.getContext(), R.layout.my_spinner_item, Tea.teaType.values()));
        infusionsPicker = (NumberPicker) view.findViewById(R.id.teaInfusionsSpinner);
        //set min and max value of number picker
        infusionsPicker.setMinValue(MIN_INFUSIONS);
        infusionsPicker.setMaxValue(MAX_INFUSIONS);

        //get the mode and set text and other stuff accordingly
        Bundle args = getArguments();
        mMode = args.getInt(TeaDetailActivity.MODE);
        if(mMode == TeaDetailActivity.EDIT_MODE){
            submitButton.setText(R.string.saveChanges);
            mTea = (Tea) args.getSerializable(TeaDetailActivity.TEA_TO_VIEW);
            fillTeaInfo(mTea);
        }
        else {
            submitButton.setText(R.string.submit);
            mTea = new Tea();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTea.setName(nameEdit.getText().toString());
                mTea.setType(Tea.teaType.valueOf(typeSpinner.getSelectedItem().toString()));
                mTea.setInfusions(infusionsPicker.getValue());
                DBteaCRUD dBteaCRUD = new DBteaCRUD(getContext());

                if(mMode == TeaDetailActivity.ADD_MODE) {
                    dBteaCRUD.addTea(mTea);
                }else {
                    dBteaCRUD.updateTea(mTea);
                }

                if(mFragmentListener == null)
                    throw new AssertionError();
                mFragmentListener.onFragmentFinish();
            }
        });
        return view;
    }

    private void fillTeaInfo(Tea tea){
        nameEdit.setText(tea.getName());
        infusionsPicker.setValue(tea.getInfusions());
        typeSpinner.setSelection(tea.getType().ordinal());
    }

    public interface FragmentListener{
       void onFragmentFinish();
    }

}
