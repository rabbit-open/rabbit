package com.supets.pet.mock.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.supets.commons.utils.SystemUtils;
import com.supets.commons.utils.json.JSonUtil;
import com.supets.pet.mock.ui.version.MockAboutActivity;
import com.supets.pet.mock.ui.version.VersionDTO;
import com.supets.pet.mock.utils.Utils;
import com.supets.pet.mockui.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class TabLayoutBottomActivity extends AppCompatActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_home);

        mTabTl = findViewById(R.id.tl_tab);
        mContentVp = findViewById(R.id.vp_content);

        initContent();
        initTab();

    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = mTabTl.getTabAt(i);
            if (itemTab != null) {
                itemTab.setCustomView(R.layout.item_tab_layout_custom);
                TextView itemTv = itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(tabIndicators.get(i));
            }
        }
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        tabIndicators.add("数据");
        tabIndicators.add("接口");
        tabIndicators.add("工具");
        tabIndicators.add("设置");

        tabFragments = new ArrayList<>();
        tabFragments.add(TabDataFragment.newInstance("数据"));
        tabFragments.add(TabAPIFragment.newInstance("接口"));
        tabFragments.add(TabToolsFragment.newInstance("工具"));
        tabFragments.add(TabSetFragment.newInstance("设置"));

        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


    public void update() {
        try {
            OkHttpUtils.get().url(
                    "https://raw.githubusercontent.com/rabbit-open/rabbit/master/database/version_update.json")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {

                            VersionDTO dto = JSonUtil.fromJson(response, VersionDTO.class);
                            if (dto != null) {
                                String versionBuidCode = SystemUtils.getAppVersionCode();
                                if (!TextUtils.isEmpty(versionBuidCode) && dto.content.version != Integer.parseInt(versionBuidCode)) {
                                    updateData(dto);
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateData(final VersionDTO dto) {
        new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(dto.content.text)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.pushUrl(TabLayoutBottomActivity.this, dto.content.url);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

}
