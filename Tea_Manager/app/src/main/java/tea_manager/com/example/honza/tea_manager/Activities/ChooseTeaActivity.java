package tea_manager.com.example.honza.tea_manager.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tea_manager.com.example.honza.tea_manager.Fragments.ChooseTeaActivityFragment;
import tea_manager.com.example.honza.tea_manager.R;

public class ChooseTeaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tea);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ChooseTeaActivityFragment mainFragment = new ChooseTeaActivityFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragment, mainFragment)
                .commit();
    }

}
