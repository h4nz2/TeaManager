package tea_manager.com.example.honza.tea_manager.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import tea_manager.com.example.honza.tea_manager.Activities.TeaDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.BitmapConvert;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;
import tea_manager.com.example.honza.tea_manager.Utility.TeaContentProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeaDetailFragment extends Fragment {
    public static final int MIN_INFUSIONS = 1;
    public static final int MAX_INFUSIONS = 3;
    public static final int REQUEST_IMAGE_CAPTURE = 2000;


    private int mMode;
    private FragmentListener mFragmentListener;
    private Tea mTea;

    private Button submitButton;
    private EditText nameEdit;
    private Spinner typeSpinner;
    private NumberPicker infusionsPicker;
    private ImageView teaImage;


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
        teaImage = (ImageView) view.findViewById(R.id.teaImage);

        //populate spinner
        typeSpinner.setAdapter(new ArrayAdapter<Tea.teaType>(
                this.getContext(), R.layout.my_spinner_item, Tea.teaType.values()));
        infusionsPicker = (NumberPicker) view.findViewById(R.id.teaInfusionsSpinner);

        //set min and max value of number picker
        infusionsPicker.setMinValue(MIN_INFUSIONS);
        infusionsPicker.setMaxValue(MAX_INFUSIONS);
        //add an icon to the ImageView
        teaImage.setImageResource(R.drawable.ic_tea_default_black_24dp);

        //get the mode and set text and other stuff accordingly
        Bundle args = getArguments();
        mMode = args.getInt(TeaDetailActivity.MODE);
        mTea = (Tea) args.getSerializable(TeaDetailActivity.TEA_TO_VIEW);
        fillTeaInfo(mTea);
        if(mMode == TeaDetailActivity.EDIT_MODE){
            submitButton.setText(R.string.saveChanges);
        }
        else {
            submitButton.setText(R.string.submit);
        }

        //set onChangeListener, so that values are kept when the user takes a photo or rotates the screen
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTea.setName(nameEdit.getText().toString());
            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTea.setType(Tea.teaType.valueOf(typeSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        infusionsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mTea.setInfusions(newVal);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTea.setName(nameEdit.getText().toString());
                mTea.setType(Tea.teaType.valueOf(typeSpinner.getSelectedItem().toString()));
                mTea.setInfusions(infusionsPicker.getValue());
                if (teaImage.getDrawingCache() != null)
                    mTea.setImage(teaImage.getDrawingCache());

                ContentValues values = new ContentValues();
                values.put(Tea.KEY_NAME, mTea.getName());
                values.put(Tea.KEY_TYPE, mTea.getType().ordinal());
                values.put(Tea.KEY_INFUSIONS, mTea.getInfusions());
                if(mTea.getImageByte() != null)
                    values.put(Tea.KEY_IMAGE, mTea.getImageByte());
                else
                    values.put(Tea.KEY_IMAGE, (byte[]) null);

                if(mMode == TeaDetailActivity.ADD_MODE) {
                    getContext().getContentResolver().insert(
                            TeaContentProvider.CONTENT_URI,
                            values);
                }else {
                    String[] args = {Integer.toString(mTea.getID())};
                    getContext().getContentResolver().update(
                            TeaContentProvider.CONTENT_URI,
                            values,
                            Tea.KEY_ID + " = ?",
                            args);
                }

                if(mFragmentListener == null)
                    throw new AssertionError();
                mFragmentListener.onFragmentFinish();
            }
        });

        teaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //can I use camera?
                if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
                    return;
                }
                dispatchTakePictureIntent();
            }
        });

        return view;
    }

    private void fillTeaInfo(Tea tea){
        nameEdit.setText(tea.getName());
        infusionsPicker.setValue(tea.getInfusions());
        typeSpinner.setSelection(tea.getType().ordinal());
        if(tea.getImageByte() != null) {
            teaImage.setImageBitmap(tea.getImageBitmap());
        }
    }

    public interface FragmentListener{
       void onFragmentFinish();
    }

    /**
     * This start a camera intent that just takes a picture and returns.
     * OnActivityResult is handled in the activity housing this fragment,
     * to prevent issues when user rotates the screen while taking the picture
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(permissions[0].equals(Manifest.permission.CAMERA)  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            dispatchTakePictureIntent();
        }
    }
}
