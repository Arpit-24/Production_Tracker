package com.example.arpit.productiontracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arpit on 06-07-2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context context;
    private List<ProductionItem> itemList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, qtyProduced, qtyTarget;
        public MyViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.item_detail);
            qtyProduced = (TextView) view.findViewById(R.id.item_qty);
            qtyTarget = (TextView) view.findViewById(R.id.item_target);
        }
    }
    public ItemsAdapter(Context context, List<ProductionItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.production_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductionItem productionItem = itemList.get(position);
        holder.itemName.setText(productionItem.getItemName());
        holder.qtyProduced.setText(""+productionItem.getQtyProduced()+"");
        holder.qtyTarget.setText(""+productionItem.getQtyTarget()+"");
        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetTargetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("itemName", holder.itemName.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        holder.qtyProduced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetTargetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("itemName", holder.itemName.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        holder.qtyTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetTargetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("itemName", holder.itemName.getText().toString());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
