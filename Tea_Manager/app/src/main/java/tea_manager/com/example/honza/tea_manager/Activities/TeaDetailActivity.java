package tea_manager.com.example.honza.tea_manager.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tea_manager.com.example.honza.tea_manager.Fragments.TeaDetailFragment;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

import static tea_manager.com.example.honza.tea_manager.Fragments.TeaDetailFragment.REQUEST_IMAGE_CAPTURE;

public class TeaDetailActivity extends AppCompatActivity implements TeaDetailFragment.FragmentListener{
    //keys for passing values
    public static final int EDIT_MODE = 1000;
    public static final int ADD_MODE = 1001;
    public static final String TEA_TO_VIEW = "teaToView";
    public static final String MODE = "mode";

    private int mMode;
    private Tea mTea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_detail);

        mTea = new Tea(-1, "", Tea.teaType.Black, 1, null);
        mMode = getIntent().getIntExtra(MODE, -1);
        //startFragment();
    }

    @Override
    public void onFragmentFinish() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*this is a terrible solution to camera rotate problem
         (before when the camera was rotating while taking the picture,
         app would lose reference to the fragment that started the camera intent
         and thus data would not be passed as onActivityResult would not be called
         in the new fragment(re-created because of screen rotation))*/
        if (resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mTea.setImage(imageBitmap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startFragment();
    }

    private void startFragment(){
        TeaDetailFragment mainFragment;
        if(mMode == EDIT_MODE){
            getSupportActionBar().setTitle(R.string.teaDetailEdit);
            mTea = (Tea) getIntent().getSerializableExtra(TEA_TO_VIEW);
            mainFragment = TeaDetailFragment.newInstance(MODE, EDIT_MODE, TEA_TO_VIEW, mTea);
        }else if (mMode == ADD_MODE){
            getSupportActionBar().setTitle(R.string.teaDetailAdd);
            mainFragment = TeaDetailFragment.newInstance(MODE, ADD_MODE, TEA_TO_VIEW, mTea);
        }else{
            mainFragment = null;
            finish();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragment, mainFragment)
                .commit();
    }
}
