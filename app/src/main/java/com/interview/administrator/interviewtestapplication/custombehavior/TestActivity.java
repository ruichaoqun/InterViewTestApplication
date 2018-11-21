package com.interview.administrator.interviewtestapplication.custombehavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.interview.administrator.interviewtestapplication.R;
import com.interview.administrator.interviewtestapplication.UiUtils;
import com.interview.administrator.interviewtestapplication.custombehavior.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, CustomHeadLayout.OnOffsetChangedListener {
    private Toolbar mToolbar;
    private CustomHeadLayout layout;
    private ImageView imageView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    CustomHeadLayout customHeadLayout;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_test);
        mToolbar = findViewById(R.id.toolbar);
        layout = findViewById(R.id.head_layout);
        title = findViewById(R.id.tv_app_bar_title);
        imageView = findViewById(R.id.image);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setPadding(0, UiUtils.getStatusBarHeight(),0,0);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        initViewPager();
        customHeadLayout = findViewById(R.id.head_layout);
        customHeadLayout.addOnOffsetChangedListener(this);
        title.setText("Read The fucking Source Code");
        title.setVisibility(View.INVISIBLE);
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        fragmentList.add(new ItemFragment());
        fragmentList.add(new ItemFragment());
        fragmentList.add(new ItemFragment());
        stringList.add("音乐");
        stringList.add("动态");
        stringList.add("关于我");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,stringList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onOffsetChanged(CustomHeadLayout headLayout, int verticalOffset, float rate) {
        if(rate == 0 && title.getVisibility() == View.INVISIBLE){
            title.setVisibility(View.VISIBLE);
        }else if(rate != 0 && title.getVisibility() == View.VISIBLE){
            title.setVisibility(View.INVISIBLE);
        }
    }
}
