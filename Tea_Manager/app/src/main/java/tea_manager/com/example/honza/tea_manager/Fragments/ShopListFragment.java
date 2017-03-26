package tea_manager.com.example.honza.tea_manager.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBshopCRUD;
import tea_manager.com.example.honza.tea_manager.Utility.ShopListAdpater;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShopListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ShopListAdpater mAdapter;
    private List<Shop> mShopList;

    public ShopListFragment() {    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.shopListRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        DBshopCRUD dBshopCRUD = new DBshopCRUD(getActivity());
        mShopList = dBshopCRUD.getAllShops();

        updateUI();

        return view;
    }


    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new ShopListAdpater(getActivity(), mShopList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Create a DBteaCRUD object, and pass it the context of this activity
        DBshopCRUD dbcrud = new DBshopCRUD(getActivity());
        mShopList = dbcrud.getAllShops();
        mAdapter.updateList(mShopList);
        mAdapter.notifyDataSetChanged();
    }
}
