package com.hualala.librealm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hualala.librealm.dao.DogDao;
import com.hualala.librealm.utils.RegexUtils;

public class MainActivity extends AppCompatActivity {

    private DogDao dao = new DogDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent(this, RealmActivity.class);
//
//        Dog dog = new Dog();
//        dog.name = "lhj2";
//        dog.age = 3300;
//        dog.emails = new RealmList<>();
//
//        DogEmail email = new DogEmail();
//        email.address = "test2";
//        dog.emails.add(email);
//
//        dao.addDog2(dog);
//
//        dao.update("lhj");
//
//        List<Dog> da=dao.queryAllDog();
//        Log.v("da:", da.size() + "");
//        Log.v("da:", da.get(0).emails.size() + "");

        //intent.putExtra("dogs", dog);
        //startActivity(intent);
        RegexUtils.find("[\\d.]+", "3422错误3.40");
        RegexUtils.find("[\\d.]+", "34.22错误3.40");
        RegexUtils.find("[\\d.]+", "错误");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}
