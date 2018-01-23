package com.supets.pet.mock.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class MockUiActivity extends TabLayoutBottomActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
    }
}
