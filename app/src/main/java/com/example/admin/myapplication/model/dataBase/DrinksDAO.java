package com.example.admin.myapplication.model.dataBase;

import android.content.Context;

import com.example.admin.myapplication.model.Drink;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DrinksDAO {
    private Realm mRealm;

    public DrinksDAO(Context context) {
        mRealm = Realm.getInstance(context);
    }

    public List<Drink> getAllDrinks(){
        mRealm.beginTransaction();
        RealmResults<Drink> drinks = mRealm.where(Drink.class).findAll();
        mRealm.commitTransaction();
        return drinks;
    }

    public void save(String name, int price){
        mRealm.beginTransaction();
        Drink drink= mRealm.createObject(Drink.class);
        drink.setName(name);
        drink.setPrice(price);
        mRealm.commitTransaction();
    }

    public void close (){
        mRealm.close();
    }
}
