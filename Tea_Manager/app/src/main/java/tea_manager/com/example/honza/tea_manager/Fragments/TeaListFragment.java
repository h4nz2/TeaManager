package tea_manager.com.example.honza.tea_manager.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;
import tea_manager.com.example.honza.tea_manager.Utility.TeaContentProvider;
import tea_manager.com.example.honza.tea_manager.Utility.TeaListAdapter;


public class TeaListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String FILTERS_TAG = "filtersTag";
    public static final String TYPE_KEY = "typeKey";
    public static final String INFUSIONS_KEY = "infusionsKey";

    private TeaListAdapter mAdapter;
    private Button mFiltersButton;

    private RecyclerView recyclerView;
    private TextView infusionsTextView;
    private TextView typeTextView;

    //this will store filters for the database(or maybe not, we will see)
    private int teaTypeFilter;
    private int teaInfusionsFilter;

    public TeaListFragment() {
    }

    public static TeaListFragment newInstance(
            String keyType, String teaType, String keyInfusions, String infusions) {

        Bundle args = new Bundle();
        args.putString(keyType, teaType);
        args.putString(keyInfusions, infusions);

        TeaListFragment fragment = new TeaListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_list, container, false);

        mFiltersButton = (Button) view.findViewById(R.id.filtersButton);
        mFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeaListFiltersFragment filtersFragment = TeaListFiltersFragment.newInstance(
                        TYPE_KEY, teaTypeFilter, INFUSIONS_KEY, teaInfusionsFilter);
                filtersFragment.show(getFragmentManager(), FILTERS_TAG);
            }
        });

        typeTextView = (TextView) view.findViewById(R.id.teaTypeFiltersText);
        infusionsTextView = (TextView) view.findViewById(R.id.teaInfusionsFiltersText);
        recyclerView = (RecyclerView) view.findViewById(R.id.teaList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new TeaListAdapter(getContext(), null);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        /*
        * musis obnovit filtry a pak naprogramuj nacitani z filtru
        * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        * */
        super.onResume();

        //get type and infusons and update filters text
        Bundle args = getArguments();
        String type = args.getString(TYPE_KEY);
        String infusions = args.getString(INFUSIONS_KEY);
        typeTextView.setText(type);
        infusionsTextView.setText(infusions);

        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), TeaContentProvider.CONTENT_URI, null, null, null, Tea.KEY_NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
