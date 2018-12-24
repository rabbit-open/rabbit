package com.hualala.librealm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hualala.librealm.dao.DogDao;
import com.hualala.librealm.model.Dog;

import java.util.List;

public class RealmActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dog dog = (Dog) getIntent().getSerializableExtra("dogs");
        Log.v("dog:", dog.name + dog.age);
        Log.v("dog:", dog.emails.size() + "");


    }


}
