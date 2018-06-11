package com.example.admin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.model.Drink;
import com.example.admin.myapplication.model.dataBase.DrinksDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Drink> drinks;
    private DrinksDAO drinksDAO;
    private PersonAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drinksDAO = new DrinksDAO(this);
        drinks = drinksDAO.getAllDrinks();
        //Находим ссылку на контейнер - виджет
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        //LinearLayoutManager занимается размещением объектов на экране и прокруткой
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Подготавливаем армию клонов
        //Создаём экземпляр адаптера и передаём ему под командование наших клонов. Далее руководит ими он
        mAdapter = new PersonAdapter(drinks);
        //Назначаем вьюхе адаптером наш экземпляр PersonAdapter
        mRecyclerView.setAdapter(mAdapter);
        Button button = findViewById(R.id.button);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDrinkActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Drink drink = drinks.get(new Random().nextInt(drinks.size()));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Курлы!")
                        .setMessage(drink.getName())
                        .setCancelable(false)
                        .setNegativeButton("GO V KB",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
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
        drinks = drinksDAO.getAllDrinks();
        mAdapter = new PersonAdapter(drinks);
        //Назначаем вьюхе адаптером наш экземпляр PersonAdapter
        mRecyclerView.setAdapter(mAdapter);    }


    /*Класс PersonHolder занят тем, что держит на готове ссылки на элементы виджетов,
     которые он с радостью наполнит данными из объекта модели в методе bindCrimе.
     Этот класс используется только адаптером в коде ниже, адаптер дёргает его и поручает
     грязную работу по заполнению виджетов*/
    private class PersonHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public PersonHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        //Метод, связывающий ранее добытые в конструкторе ссылки с данными модели
        public void bindCrime(Drink drink) {
            textView.setText(drink.getName());
        }
    }

    //Наш адаптер, мост между фабрикой клонов и выводом их на экран.
    //Его методы будет дёргать LinearLayoutManager, назныченный вьюшке
    //RecyclerView в методе onCreate нашей активити
    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder> {
        private List<Drink> drinks;

        public PersonAdapter(List<Drink> drinks) {
            this.drinks = drinks;
        }

        //Создаёт пустую вьюшку,оборачивает её в PersonHolder.
        //Дальше забота по наполнению этой вьюшки ложиться именно на объект PersonHolder'а
        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(R.layout.item_view, parent, false);
            return new PersonHolder(view);

        }

        //Дёргает метод холдера при выводе нового элемента списка на экран,
        //передавая ему актуальный объект модели для разбора и представления
        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            holder.bindCrime(drinks.get(position));

        }

        //Возвращает размер хранилища моделей
        @Override
        public int getItemCount() {
            return drinks.size();
        }
    }
}
