package tea_manager.com.example.honza.tea_manager.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
    private final Context mContext;
    private TeaCursorWrapper mCursor;

    public TeaListAdapter(Context context, Cursor cursor){
        this.mContext = context;
        swapCursor(cursor);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tea_row_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // When the cursor can't move to given position, it will crash and burn.
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            throw new IllegalStateException();
        }

        Tea tea = mCursor.getTea();

        holder.populateRow(tea);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        if (mCursor == null || !mCursor.moveToPosition(position)) {
            return 0;
        }
        int idColumnIndex = mCursor.getColumnIndex(Tea.KEY_ID);
        return mCursor.getLong(idColumnIndex);
    }

    public void swapCursor(Cursor cursor) {
            /*If the given cursor isn't null, we can use it to fetch Device objects.
            Otherwise we have to notify the adapter that there is no cursor and thus no data.*/
        if (cursor != null) {
            mCursor = new TeaCursorWrapper(cursor);
            notifyDataSetChanged();
        } else {
            mCursor = null;
            notifyItemRangeRemoved(0, getItemCount());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView teaNameView;
        private TextView teaTypeView;
        private TextView teaInfusionsView;
        private int id;

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
            id = tea.getID();
        }

        @Override
        public void onClick(View v) {
            mCursor.moveToPosition(getAdapterPosition());
            TeaCursorWrapper teaCursorWrapper = new TeaCursorWrapper(mCursor);
            Intent intent = new Intent(mContext, TeaDetailActivity.class);
            intent.putExtra(TeaDetailActivity.TEA_TO_VIEW, teaCursorWrapper.getTea());
            intent.putExtra(TeaDetailActivity.MODE, TeaDetailActivity.EDIT_MODE);
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            final View view = v;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setTitle("Remove tea ?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] args = {Integer.toString(id)};
                    view.getContext().getContentResolver().delete(TeaContentProvider.CONTENT_URI, Tea.KEY_ID + " = ?", args);
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
