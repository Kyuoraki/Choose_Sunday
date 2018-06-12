package com.example.admin.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.model.dataBase.DrinksDAO;

public class AddDrinkActivity extends AppCompatActivity {

    private DrinksDAO drinksDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        drinksDAO = new DrinksDAO(this);
        final EditText editTextNameDrink = findViewById(R.id.editTextNameDrink);
        final EditText editTextPriceDrink = findViewById(R.id.editTextPriceDrink);
        Button buttonSaveDrink = findViewById(R.id.buttonSaveDrink);
        buttonSaveDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextNameDrink.getText().toString().equals("")&& editTextPriceDrink.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Пусто.", Toast.LENGTH_SHORT).show();
                } else drinksDAO.save(editTextNameDrink.getText().toString(),Integer.parseInt(editTextPriceDrink.getText().toString()));
            }
        });
    }
    @Override

    public void onDestroy() {

        super.onDestroy();

        drinksDAO.close();

    }

    @Override
    protected void onResume() {
        super.onResume();
        drinksDAO = new DrinksDAO(this);
    }
}
