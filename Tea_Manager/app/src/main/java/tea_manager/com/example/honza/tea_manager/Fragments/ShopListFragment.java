package tea_manager.com.example.honza.tea_manager.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tea_manager.com.example.honza.tea_manager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShopListFragment extends Fragment {

    public ShopListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_list, container, false);
    }
}
