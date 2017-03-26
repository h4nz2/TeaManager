package tea_manager.com.example.honza.tea_manager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tea_manager.com.example.honza.tea_manager.Fragments.ShopDetailFragment;
import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.R;

public class ShopDetailActivity extends AppCompatActivity implements ShopDetailFragment.FragmentListener {
    //keys for passing values
    public static final int EDIT_MODE = 1000;
    public static final int ADD_MODE = 1001;
    public static final String SHOP_TO_VIEW = "teaToView";
    public static final String MODE = "mode";

    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        mMode = getIntent().getIntExtra(MODE, -1);
        ShopDetailFragment mainFragment;
        if(mMode == EDIT_MODE){
            getSupportActionBar().setTitle(R.string.shopDetailEdit);
            Shop shop = (Shop) getIntent().getSerializableExtra(SHOP_TO_VIEW);
            mainFragment = ShopDetailFragment.newInstance(MODE, EDIT_MODE, SHOP_TO_VIEW, shop);
        }else if (mMode == ADD_MODE){
            getSupportActionBar().setTitle(R.string.shopDetailAdd);
            mainFragment = ShopDetailFragment.newInstance(MODE, ADD_MODE);
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
