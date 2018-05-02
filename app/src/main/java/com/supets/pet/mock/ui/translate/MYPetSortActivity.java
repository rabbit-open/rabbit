package com.supets.pet.mock.ui.translate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.supets.pet.mock.db.WordDao;
import com.supets.pet.mockui.R;

import java.util.ArrayList;


public class MYPetSortActivity extends FragmentActivity implements SideBar.OnTouchingLetterChangedListener, AdapterView.OnItemClickListener {

    private SideBar sideBar;
    private ListView sortListView;
    private SortAdapter adapter;

    public ArrayList<SortModel> map;
    private WordDao wordDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_android);

        wordDao = new WordDao(this);
        map = wordDao.select();

        sortListView = findViewById(R.id.country_lvcountry);
        sideBar = findViewById(R.id.sidrbar);
        TextView tip = findViewById(R.id.dialog);
        sideBar.setTextView(tip);
        sideBar.setOnTouchingLetterChangedListener(this);
        sortListView.setOnItemClickListener(this);

        adapter = new SortAdapter(this);
        sortListView.setAdapter(adapter);

        adapter.updateListView(map);

        EditText input = findViewById(R.id.input);
        findViewById(R.id.search).setOnClickListener(view -> {
            String words = input.getText().toString();
            if (!TextUtils.isEmpty(words)) {
                requestData(words.toLowerCase());
            } else {
                adapter.updateListView(wordDao.select());
            }
        });

    }

    private void requestData(String s) {
        adapter.updateListView(wordDao.selectName(s));
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            sortListView.setSelection(position);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SortModel model = (SortModel) parent.getAdapter().getItem(position);
        if (model != null) {
            Intent intent = new Intent(this, TranslateActivity.class);
            intent.putExtra("content", model.name);
            startActivity(intent);
        }
    }
}
