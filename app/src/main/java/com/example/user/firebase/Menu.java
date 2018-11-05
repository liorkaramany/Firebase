package com.example.user.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity implements View.OnCreateContextMenuListener {

    ListView menu;

    DatabaseReference df;

    List<Dish> dishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menu=(ListView) findViewById(R.id.menu);
        dishList = new ArrayList<>();

        df = FirebaseDatabase.getInstance().getReference("dishes");

        menu.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Options");
        menu.add("Edit");
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String id = dishList.get(index).getId();

        String oper = item.getTitle().toString();
        if (oper.equals("Delete"))
        {
            deleteDish(id);
        }
        else if (oper.equals("Edit"))
        {
            editDish(index);
        }

        return super.onContextItemSelected(item);
    }

    public void deleteDish(String id)
    {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("dishes").child(id);
        dr.removeValue();

        Toast.makeText(this, "Dish has been removed", Toast.LENGTH_SHORT).show();
    }

    public void editDish(int index)
    {
        Dish d = dishList.get(index);

        Intent t = new Intent(this, Edit.class);

        //Send the arguments as separate values:
        t.putExtra("id", d.getId());
        t.putExtra("name", d.getName());
        t.putExtra("price", d.getPrice());
        startActivity(t);
    }

    @Override
    protected void onStart() {
        super.onStart();

        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dishList.clear();

                for(DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    Dish dish = dishSnapshot.getValue(Dish.class);

                    dishList.add(dish);
                }

                DishList adapter = new DishList(Menu.this, dishList);
                menu.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        finish();
    }
}
