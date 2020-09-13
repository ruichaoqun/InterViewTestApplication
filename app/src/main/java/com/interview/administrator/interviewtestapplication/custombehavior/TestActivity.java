package com.interview.administrator.interviewtestapplication.custombehavior;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.interview.administrator.interviewtestapplication.R;
import com.interview.administrator.interviewtestapplication.UiUtils;
import com.interview.administrator.interviewtestapplication.custombehavior.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, CustomHeadLayout.OnOffsetChangedListener {
    private CustomHeadLayout layout;
    private ImageView imageView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    CustomHeadLayout customHeadLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ImmersionBar.with(this).transparentBar().init();
        layout = findViewById(R.id.head_layout);
        imageView = findViewById(R.id.image);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        initViewPager();
        customHeadLayout = findViewById(R.id.head_layout);
        customHeadLayout.addOnOffsetChangedListener(this);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) customHeadLayout.getLayoutParams();
                ((CustomBehavior)params.getBehavior()).expand((CoordinatorLayout) customHeadLayout.getParent(),customHeadLayout);
            }
        });
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
//        Log.w("AAAAAAA","rate-->"+rate);
        if(rate == 0){
            ImmersionBar.with(this).statusBarDarkFont(true).init();
        }else{
            ImmersionBar.with(this).statusBarDarkFont(false).init();
        }
    }
}
