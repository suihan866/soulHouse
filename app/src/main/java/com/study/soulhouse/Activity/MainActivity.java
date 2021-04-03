package com.study.soulhouse.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.study.soulhouse.R;
import com.study.soulhouse.SearchActivity;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.adapter.MyPagerAdapter;
import com.study.soulhouse.radioFragment;
import com.study.soulhouse.recommendFragment;
import com.study.soulhouse.finalDate.api_url;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //为TabLayout设置标签
        TabLayout tab=findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("推荐"));
        tab.addTab(tab.newTab().setText("本地"));
        //标签数组
        String Tab[]=new String[]{"推荐","本地"};
        //Fragment列表
        Fragment recommend=new recommendFragment();
        Fragment local=new radioFragment();
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(recommend);
        fragments.add(local);
        //给viewPager添加数据
        FragmentManager frag=getSupportFragmentManager();
        ViewPager viewPager=findViewById(R.id.viewPager);
        MyPagerAdapter adapter=new MyPagerAdapter(frag,fragments,Tab);
        viewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定
        tab.setupWithViewPager(viewPager);
        //搜索功能
        SearchView searchView=findViewById(R.id.main_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("url", api_url.music_url+query);
                startActivity(intent);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
