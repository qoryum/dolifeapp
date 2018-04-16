package com.example.hnf.dolifeapp.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.hnf.dolifeapp.Adapter.ViewSwapperAdapter;
import com.example.hnf.dolifeapp.Fragment.MyPicFragment;
import com.example.hnf.dolifeapp.Fragment.RecentFragment;
import com.example.hnf.dolifeapp.R;
import com.example.hnf.mylibrary.view.AdaptableBottomNavigationView;
import com.example.hnf.mylibrary.view.ViewSwapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabMainLayout;
    AppBarLayout abl;
    private ViewPager viewPagerMain;
    FloatingActionButton fabPost;
    private AdaptableBottomNavigationView bottomNavigationView;
    private ViewSwapperAdapter viewSwapperAdapter;
    private ViewSwapper viewSwapper;

    FirebaseUser user;
    String userEmail, userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        userName = userEmail.substring(0, userEmail.indexOf("@"));

        viewPagerMain = findViewById(R.id.viewPagerMain);
        abl = findViewById(R.id.appBarLayout);
        tabMainLayout = findViewById(R.id.tabMainLayout);
        fabPost = findViewById(R.id.fabPost);

        fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        setupViewPager(viewPagerMain);
        tabMainLayout.setupWithViewPager(viewPagerMain);

        bottomNavigationView = (AdaptableBottomNavigationView) findViewById(R.id.view_bottom_navigation);
        viewSwapper = (ViewSwapper) findViewById(R.id.view_swapper);
        viewSwapperAdapter = new ViewSwapperAdapter(getSupportFragmentManager());

        viewSwapper.setAdapter(viewSwapperAdapter);
        bottomNavigationView.setupWithViewSwapper(viewSwapper);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentFragment(), "Timeline");
        adapter.addFragment(new MyPicFragment(), "Stats");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionLogout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
