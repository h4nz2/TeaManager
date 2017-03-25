package tea_manager.com.example.honza.tea_manager.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tea_manager.com.example.honza.tea_manager.Activities.MainActivity;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeaPickerFragment extends Fragment {

    public TeaPickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tea_picker, container, false);

        Button thanksButton = (Button) view.findViewById(R.id.thanksButton);
        thanksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        return view;
    }
}
