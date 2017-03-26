package tea_manager.com.example.honza.tea_manager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tea_manager.com.example.honza.tea_manager.Fragments.TeaDetailFragment;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

public class TeaDetailActivity extends AppCompatActivity implements TeaDetailFragment.FragmentListener{
    //keys for passing values
    public static final int EDIT_MODE = 1000;
    public static final int ADD_MODE = 1001;
    public static final String TEA_TO_VIEW = "teaToView";
    public static final String MODE = "mode";

    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_detail);

        mMode = getIntent().getIntExtra(MODE, -1);
        TeaDetailFragment mainFragment;
        if(mMode == EDIT_MODE){
            getSupportActionBar().setTitle(R.string.teaDetailEdit);
            Tea tea = (Tea) getIntent().getSerializableExtra(TEA_TO_VIEW);
            mainFragment = TeaDetailFragment.newInstance(MODE, EDIT_MODE, TEA_TO_VIEW, tea);
        }else if (mMode == ADD_MODE){
            getSupportActionBar().setTitle(R.string.teaDetailAdd);
            mainFragment = TeaDetailFragment.newInstance(MODE, ADD_MODE);
        }else{
            mainFragment = null;
            finish();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();
    }

    @Override
    public void onFragmentFinish() {
        finish();
    }
}
