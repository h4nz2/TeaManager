package tea_manager.com.example.honza.tea_manager.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Activities.ChooseTeaActivity;
import tea_manager.com.example.honza.tea_manager.Activities.MainActivity;
import tea_manager.com.example.honza.tea_manager.Activities.TeaPickedActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.BitmapConvert;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;
import tea_manager.com.example.honza.tea_manager.Utility.TeaContentProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeaPickedFragment extends Fragment{
    private List<Tea> mTeaList;
    private Tea.teaType mTeatype;

    private TextView teaNameView;
    private TextView teaTypeView;
    private TextView teaInfusionsView;
    private ImageView teaImageView;

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
        teaImageView = (ImageView) view.findViewById(R.id.teaImage);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.teaLayout);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
            params.gravity = Gravity.CENTER_HORIZONTAL;
            layout.setLayoutParams(params);
        }
        else{
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.teaLayout);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
            params.gravity = Gravity.CENTER;
            layout.setLayoutParams(params);
        }

        Bundle args = getArguments();
        mTeatype = (Tea.teaType) args.getSerializable(ChooseTeaFragment.TEA_TYPE_CHOSEN);

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
        if (cursor.moveToFirst()) {
            do {
                Tea tea = new Tea(
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(Tea.KEY_NAME)),
                        Tea.teaType.values()[cursor.getInt(cursor.getColumnIndex(Tea.KEY_TYPE))],
                        cursor.getInt(cursor.getColumnIndex(Tea.KEY_INFUSIONS)),
                        cursor.getBlob(cursor.getColumnIndex(Tea.KEY_IMAGE))
                );
                mTeaList.add(tea);
            } while (cursor.moveToNext());
        }
        if(mTeaList.size()<=0){
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.noTeaFound)
                    .setNeutralButton(R.string.thatSucks, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), ChooseTeaActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
        else {
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
        return view;
    }

    public void chooseTea(List<Tea> teaList){
        int chosenTeaPosition = (int) (Math.random() * teaList.size());
        Tea chosenTea = mTeaList.get(chosenTeaPosition);

        teaNameView.setText(chosenTea.getName());
        teaTypeView.setText(chosenTea.getType().toString());
        teaInfusionsView.setText("Infusions: " + Integer.toString(chosenTea.getInfusions()));
        teaImageView.setImageBitmap(chosenTea.getImageBitmap());
    }
}
