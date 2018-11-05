package com.example.user.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main extends AppCompatActivity {

    DatabaseReference df;
    EditText name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df=FirebaseDatabase.getInstance().getReference("dishes");

        name = (EditText) findViewById(R.id.name);
        price = (EditText) findViewById(R.id.price);
    }

    public void push(View view) {
        String n = name.getText().toString();
        String p = price.getText().toString();

        if (!n.isEmpty() && !p.isEmpty() && !p.equals("."))
        {
            String id = df.push().getKey();
            Dish d = new Dish(id, n, Double.parseDouble(p));

            df.child(id).setValue(d);

            Toast.makeText(this, "Upload successfull", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "You didn't enter all the information", Toast.LENGTH_SHORT).show();
    }

    public void next(View view) {
        Intent t = new Intent(this, Menu.class);
        startActivity(t);
    }
}
