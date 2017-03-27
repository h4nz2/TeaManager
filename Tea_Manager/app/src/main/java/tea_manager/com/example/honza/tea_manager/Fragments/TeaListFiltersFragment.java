package tea_manager.com.example.honza.tea_manager.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeaListFiltersFragment extends android.support.v4.app.DialogFragment {
    public static final String ANY = "Any";

    private Button submitButton;
    private Spinner teaTypeSpinner;
    private Spinner teaInfusionsSpinner;
    private List<String> mTypeFilterValues;
    private final String[] infusionsSpinnerValues = {"1", "2", "3", "All"};
    private FiltersListener mFiltersListener;

    public TeaListFiltersFragment() {
        // Required empty public constructor
    }

    public static TeaListFiltersFragment newInstance(
            String keyType, int teaType, String keyInfusions, int infusions) {

        Bundle args = new Bundle();
        args.putSerializable(keyType, teaType);
        args.putInt(keyInfusions, infusions);

        TeaListFiltersFragment fragment = new TeaListFiltersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof TeaListFiltersFragment.FiltersListener))
            throw new AssertionError();
        mFiltersListener = (FiltersListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tea_list_filters, container, false);

        teaTypeSpinner = (Spinner) view.findViewById(R.id.teaTypeSpinner);
        teaInfusionsSpinner = (Spinner) view.findViewById(R.id.teaInfusionsSpinner);
        //populate spinners
        Tea.teaType[] teaTypeArray = Tea.teaType.values();
        mTypeFilterValues = new ArrayList<String>();
        for(int i = 0; i < teaTypeArray.length; i++){
            if(i >= teaTypeArray.length ) break;
            mTypeFilterValues.add(teaTypeArray[i].toString());
        }
        mTypeFilterValues.add(ANY);
        teaTypeSpinner.setAdapter(new ArrayAdapter<String>(
                this.getContext(), R.layout.my_spinner_item, mTypeFilterValues));
        teaInfusionsSpinner.setAdapter(new ArrayAdapter<String>(
                this.getContext(), R.layout.my_spinner_item, infusionsSpinnerValues));

        Bundle args = getArguments();
        teaTypeSpinner.setSelection(args.getInt(TeaListFragment.TYPE_KEY));
        teaInfusionsSpinner.setSelection(args.getInt(TeaListFragment.INFUSIONS_KEY));


        updateFilters(3, 3);

        submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFiltersListener.onFiltersSaved(
                        teaTypeSpinner.getSelectedItem().toString(),
                        teaInfusionsSpinner.getSelectedItem().toString());
                dismiss();
            }
        });
        return view;
    }

    public void updateFilters(int teaType, int teaInfusions){
        teaInfusionsSpinner.setSelection(teaInfusions);
        teaTypeSpinner.setSelection(teaType);
    }

    public interface FiltersListener{
        void onFiltersSaved(String teaType, String infusions);
    }

}
