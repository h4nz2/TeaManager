package tea_manager.com.example.honza.tea_manager.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.DBHelper;
import tea_manager.com.example.honza.tea_manager.Utility.DBteaCRUD;
import tea_manager.com.example.honza.tea_manager.Utility.TeaListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeaListActivityFragment extends Fragment {
    private TeaListAdapter mAdapter;
    private List<Tea> mTeaList;
    RecyclerView mRecyclerView;

    public TeaListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.teaList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        DBteaCRUD dBteaCRUD = new DBteaCRUD(this.getContext());
        mTeaList = dBteaCRUD.getAllTeas();

        updateUI();
        return view;
    }

    public void updateUI(){
        if(mAdapter == null){
            mAdapter = new TeaListAdapter(mTeaList, this.getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
        else
            mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Create a DBteaCRUD object, and pass it the context of this activity
        DBteaCRUD dbcrud = new DBteaCRUD(this.getContext());
        mTeaList = dbcrud.getAllTeas();
        mAdapter.updateList(mTeaList);
        mAdapter.notifyDataSetChanged();
    }
}
