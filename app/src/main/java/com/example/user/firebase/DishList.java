package com.example.user.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DishList extends ArrayAdapter<Dish> {

    private Activity context;
    private List<Dish> dishList;

    public DishList(Activity context, List<Dish> dishList) {

        super(context, R.layout.list_layout, dishList);
        this.context = context;
        this.dishList = dishList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvName = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) listViewItem.findViewById(R.id.tvPrice);

        Dish dish = dishList.get(position);

        tvName.setText(dish.getName());
        tvPrice.setText(""+dish.getPrice());

        return listViewItem;
    }
}
