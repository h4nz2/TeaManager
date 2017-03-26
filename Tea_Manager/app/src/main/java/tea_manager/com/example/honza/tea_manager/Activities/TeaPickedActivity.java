package tea_manager.com.example.honza.tea_manager.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tea_manager.com.example.honza.tea_manager.Fragments.ChooseTeaFragment;
import tea_manager.com.example.honza.tea_manager.Fragments.TeaPickedFragment;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

public class TeaPickedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_picked);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tea.teaType chosenType = (Tea.teaType) getIntent().getSerializableExtra(ChooseTeaFragment.TEA_TYPE_CHOSEN);
        TeaPickedFragment mainFragment = TeaPickedFragment.newInstance(ChooseTeaFragment.TEA_TYPE_CHOSEN, chosenType);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();
    }

}
