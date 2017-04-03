package tea_manager.com.example.honza.tea_manager.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Activities.ChooseTeaActivity;
import tea_manager.com.example.honza.tea_manager.Activities.MainActivity;
import tea_manager.com.example.honza.tea_manager.Activities.TeaPickedActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;
import tea_manager.com.example.honza.tea_manager.Utility.TeaContentProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeaPickedFragment extends Fragment
implements LoaderManager.LoaderCallbacks{
    private List<Tea> mTeaList;
    private Tea.teaType mTeatype;

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

        Bundle args = getArguments();
        mTeatype = (Tea.teaType) args.getSerializable(ChooseTeaFragment.TEA_TYPE_CHOSEN);

        getLoaderManager().initLoader(1, null, this);

        String[] whereArgs = new String[1];
        Cursor cursor;
        if(mTeatype == null)
            cursor = getContext().getContentResolver().query(
                    TeaContentProvider.CONTENT_URI, null, null, null, null);
        else {
            whereArgs[0] = Integer.toString(mTeatype.ordinal());
            cursor = getContext().getContentResolver().query(
                    TeaContentProvider.CONTENT_URI, null, Tea.KEY_TYPE + " = ?", whereArgs, null);
        }

        mTeaList = new ArrayList<Tea>();
        //here it need checking if there are any teas in the db meeting the chosen criteria
        if (cursor.moveToFirst()) {
            do {
                Tea tea = new Tea(
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Tea.KEY_NAME)),
                        Tea.teaType.values()[cursor.getInt(cursor.getColumnIndex(Tea.KEY_TYPE))],
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_INFUSIONS))
                );
                mTeaList.add(tea);
            } while (cursor.moveToNext());
        }

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

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        String[] whereArgs = new String[1];
        if(mTeatype == null)
            whereArgs[0] = "*";
        else
            whereArgs[0] = Integer.toString(mTeatype.ordinal());
        return new Loader(getContext());
        //getActivity(), TeaContentProvider.CONTENT_URI, null, Tea.KEY_TYPE + " = ?", whereArgs, null
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
