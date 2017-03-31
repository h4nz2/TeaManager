package tea_manager.com.example.honza.tea_manager.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tea_manager.com.example.honza.tea_manager.Activities.TeaDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Tea;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * Created by Honza on 24/03/2017.
 */

public class TeaListAdapter extends RecyclerView.Adapter<TeaListAdapter.ViewHolder> {
    private List<Tea> mTeaList;
    private final Context mContext;

    public TeaListAdapter(List<Tea> list, Context context){
        this.mTeaList = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_row_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populateRow(mTeaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTeaList.size();
    }

    @Override
    public long getItemId(int position) {
        return mTeaList.get(position).getID();
    }

    public void updateList(List<Tea> newList){
        mTeaList.clear();
        mTeaList.addAll(newList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView teaNameView;
        private TextView teaTypeView;
        private TextView teaInfusionsView;

        public ViewHolder(View view) {
            super(view);
            teaNameView = (TextView) view.findViewById(R.id.teaNameEditText);
            teaTypeView = (TextView) view.findViewById(R.id.teaTypeSpinner);
            teaInfusionsView = (TextView) view.findViewById(R.id.teaInfusionsSpinner);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void populateRow(Tea tea){
            teaNameView.setText(tea.getName());
            teaTypeView.setText(tea.getType().toString() + ",");
            teaInfusionsView.setText("Infusions: " + Integer.toString(tea.getInfusions()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, TeaDetailActivity.class);
            intent.putExtra(TeaDetailActivity.TEA_TO_VIEW, mTeaList.get(getAdapterPosition()));
            intent.putExtra(TeaDetailActivity.MODE, TeaDetailActivity.EDIT_MODE);
            mContext.startActivity(intent);
        }


        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Remove tea ?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DBteaCRUD dBteaCRUD = new DBteaCRUD(mContext);
                    dBteaCRUD.deleteTea(mTeaList.get(getAdapterPosition()));
                    updateList(dBteaCRUD.getAllTeas());
                    notifyDataSetChanged();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialogBuilder.show();
            return true;
        }
    }
}
