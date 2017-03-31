package tea_manager.com.example.honza.tea_manager.Fragments;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import tea_manager.com.example.honza.tea_manager.Activities.ShopDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBshopCRUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopDetailFragment extends Fragment{
    private EditText nameEdit;
    private TextView openFromText;
    private TextView openToText;
    private LinearLayout openFromColumn;
    private LinearLayout openToColumn;
    private Button submitButton;

    private int mMode;
    private FragmentListener mFragmentListener;
    private Shop mShop;


    public ShopDetailFragment() {
        // Required empty public constructor
    }

    public static ShopDetailFragment newInstance(String keyMode, int mode) {

        Bundle args = new Bundle();
        args.putInt(keyMode, mode);

        ShopDetailFragment fragment = new ShopDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ShopDetailFragment newInstance(String keyMode, int mode, String keyShop, Shop shop) {

        Bundle args = new Bundle();
        args.putInt(keyMode, mode);
        args.putSerializable(keyShop, shop);

        ShopDetailFragment fragment = new ShopDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_shop_detail, container, false);

        //get all widgets
        submitButton = (Button) view.findViewById(R.id.submitButton);
        nameEdit = (EditText) view.findViewById(R.id.shopNameEdit);
        openFromText = (TextView) view.findViewById(R.id.shopOpenFromText);
        openToText = (TextView) view.findViewById(R.id.shopOpenToText);
        openFromColumn = (LinearLayout) view.findViewById(R.id.shopOpenFromColumn);
        openToColumn = (LinearLayout) view.findViewById(R.id.shopOpenToColumn);

        //get the mode and set text and other stuff accordingly
        Bundle args = getArguments();
        mMode = args.getInt(ShopDetailActivity.MODE);
        if(mMode == ShopDetailActivity.EDIT_MODE){
            submitButton.setText(R.string.saveChanges);
            mShop = (Shop) args.getSerializable(ShopDetailActivity.SHOP_TO_VIEW);
            fillShopInfo(mShop);
        }
        else {
            submitButton.setText(R.string.submit);
            mShop = new Shop();
        }

        //set opening time
        openFromColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog  timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mShop.getOpeningHours().setFromHour(hourOfDay);
                                mShop.getOpeningHours().setFromMinute(minute);
                                fillShopInfo(mShop);
                            }
                },
                        mShop.getOpeningHours().getFromHour(),
                        mShop.getOpeningHours().getFromMinute(),
                        true);
                timePickerDialog.show();
            }
        });
        //set closing time
        openToColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog  timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mShop.getOpeningHours().setToHour(hourOfDay);
                                mShop.getOpeningHours().setToMinute(minute);
                                fillShopInfo(mShop);
                            }
                        },
                        mShop.getOpeningHours().getToHour(),
                        mShop.getOpeningHours().getToMinute(),
                        true);
                timePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShop.setName(nameEdit.getText().toString());
                mShop.setOpeningHours(new Shop.OpeningHours(
                        mShop.getOpeningHours().getFromHour(),
                        mShop.getOpeningHours().getFromMinute(),
                        mShop.getOpeningHours().getToHour(),
                        mShop.getOpeningHours().getToMinute()));
                DBshopCRUD dBshopCRUD = new DBshopCRUD(getContext());

                if(mMode == ShopDetailActivity.ADD_MODE) {
                    dBshopCRUD.addShop(mShop);
                }else {
                    dBshopCRUD.updateShop(mShop);
                }

                if(mFragmentListener == null)
                    throw new AssertionError();
                mFragmentListener.onFragmentFinish();
            }
        });
        return view;
    }

    private void fillShopInfo(Shop shop){
        nameEdit.setText(shop.getName());
        openFromText.setText(shop.getOpeningHours().openFromToString());
        openToText.setText(shop.getOpeningHours().openToToString());
    }

    public interface FragmentListener{
        void onFragmentFinish();
    }

}
