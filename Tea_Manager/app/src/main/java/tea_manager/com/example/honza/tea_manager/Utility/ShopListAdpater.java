package tea_manager.com.example.honza.tea_manager.Utility;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tea_manager.com.example.honza.tea_manager.Activities.ShopDetailActivity;
import tea_manager.com.example.honza.tea_manager.Objects.Shop;
import tea_manager.com.example.honza.tea_manager.R;

/**
 * Created by Honza on 25/03/2017.
 */

public class ShopListAdpater extends RecyclerView.Adapter<ShopListAdpater.ViewHolder> {
    private List<Shop> mShopList;
    private final Context mContext;

    public ShopListAdpater(Context context, List<Shop> list){
        mShopList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_row_layout, parent, false);
        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.populateRow(mShopList.get(position));
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    @Override
    public long getItemId(int position) {
        return mShopList.get(position).getID();
    }

    public void updateList(List<Shop> newList){
        mShopList.clear();
        mShopList.addAll(newList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView shopNameView;
        private TextView shopOpeningHoursView;

        public ViewHolder(View view) {
            super(view);
            shopNameView = (TextView) view.findViewById(R.id.shopName);
            shopOpeningHoursView = (TextView) view.findViewById(R.id.shopOpeningHours);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void populateRow(Shop shop){
            shopNameView.setText(shop.getName());
            shopOpeningHoursView.setText(shop.getOpeningHours().toString());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ShopDetailActivity.class);
            intent.putExtra(ShopDetailActivity.SHOP_TO_VIEW, mShopList.get(getAdapterPosition()));
            intent.putExtra(ShopDetailActivity.MODE, ShopDetailActivity.EDIT_MODE);
            mContext.startActivity(intent);
        }


        @Override
        public boolean onLongClick(View v) {
            DBshopCRUD dBshopCRUD = new DBshopCRUD(mContext);
            dBshopCRUD.deleteShop(mShopList.get(getAdapterPosition()));
            updateList(dBshopCRUD.getAllShops());
            notifyDataSetChanged();
            return true;
        }
    }
}
