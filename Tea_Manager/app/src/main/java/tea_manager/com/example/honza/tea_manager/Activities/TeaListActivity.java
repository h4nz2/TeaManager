package tea_manager.com.example.honza.tea_manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tea_manager.com.example.honza.tea_manager.Fragments.TeaListActivityFragment;
import tea_manager.com.example.honza.tea_manager.R;

public class TeaListActivity extends AppCompatActivity {

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

        TeaListActivityFragment mainFragment = new TeaListActivityFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
