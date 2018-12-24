package com.hualala.librealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hualala.librealm.dao.DogDao;
import com.hualala.librealm.model.Dog;
import com.hualala.librealm.model.DogEmail;

import java.util.List;

import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    private DogDao dao = new DogDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, RealmActivity.class);

        Dog dog = new Dog();
        dog.name = "lhj2";
        dog.age = 3300;
        dog.emails = new RealmList<>();

        DogEmail email = new DogEmail();
        email.address = "test2";
        dog.emails.add(email);

        dao.addDog2(dog);

        dao.update("lhj");

        List<Dog> da=dao.queryAllDog();
        Log.v("da:", da.size() + "");
        Log.v("da:", da.get(0).emails.size() + "");

        //intent.putExtra("dogs", dog);
        //startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}
