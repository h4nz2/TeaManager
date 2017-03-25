package tea_manager.com.example.honza.tea_manager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tea_manager.com.example.honza.tea_manager.Fragments.TeaPickerFragment;
import tea_manager.com.example.honza.tea_manager.R;

public class TeaPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_picker);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TeaPickerFragment mainFragment = new TeaPickerFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragment, mainFragment)
                .commit();
    }

}
