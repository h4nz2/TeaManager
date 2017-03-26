package tea_manager.com.example.honza.tea_manager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tea_manager.com.example.honza.tea_manager.Fragments.ShopListFragment;
import tea_manager.com.example.honza.tea_manager.R;

public class ShopListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity in create mode
                Intent intent = new Intent(ShopListActivity.this, ShopDetailActivity.class);
                intent.putExtra(ShopDetailActivity.MODE, ShopDetailActivity.ADD_MODE);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ShopListFragment mainFragment = new ShopListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();
    }

}
