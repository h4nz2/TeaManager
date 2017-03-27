package tea_manager.com.example.honza.tea_manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tea_manager.com.example.honza.tea_manager.Fragments.TeaListFiltersFragment;
import tea_manager.com.example.honza.tea_manager.Fragments.TeaListFragment;
import tea_manager.com.example.honza.tea_manager.R;

public class TeaListActivity extends AppCompatActivity
implements TeaListFiltersFragment.FiltersListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity in create mode
                Intent intent = new Intent(TeaListActivity.this, TeaDetailActivity.class);
                intent.putExtra(TeaDetailActivity.MODE, TeaDetailActivity.ADD_MODE);
                startActivity(intent);
            }
        });

        TeaListFragment mainFragment = TeaListFragment.newInstance(
                TeaListFragment.TYPE_KEY, "Any", TeaListFragment.INFUSIONS_KEY, "Any");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onFiltersSaved(String teaType, String infusions) {
        TeaListFragment filtersFragment = TeaListFragment.newInstance(
                TeaListFragment.TYPE_KEY, teaType, TeaListFragment.INFUSIONS_KEY, infusions);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, filtersFragment)
                .commit();
    }
}
