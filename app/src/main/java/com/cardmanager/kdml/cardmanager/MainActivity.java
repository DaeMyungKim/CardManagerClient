package com.cardmanager.kdml.cardmanager;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StatisticFragment.OnFragmentInteractionListener, TalkListFragment.OnFragmentInteractionListener
{

    private DrawerLayout mDrawerLayout;
    String dbName = "mmssms.db";
    ViewPager viewPager;
    CustomerDatabase cd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view,getResources().getString(R.string.main_refresh), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                selectData();
                signUpUser("tes322t@test.com","testtt");
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void signUpUser (String email,String password)
    {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                FirebaseUser user = task.getResult().getUser();
                User userModel = new User(user.getEmail());
                databaseReference.child("users").child(user.getUid()).setValue(userModel);
            }
        }).addOnFailureListener( e ->{
            Log.d("mstag",e.toString());

        });
    }

    public class User
    {
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String email;
        public User(String _email)
        {
            email = _email;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        cd = CustomerDatabase.getInstance(this);
        if(cd.setTableCustomerInfo() && cd.setCardInfo())
            selectData();

    }
    public void onFragmentInteraction(Uri uri)
    {

    }

    public void selectData()
    {
        try{
            //Cursor result = cr.query(Uri.parse("content://sms"),null, null, null, null);
            /*String sql = "select DATETIME(substr(date,1,10),'UNIXEPOCH','LOCALTIME' as date, address," +
                "case type when '1' then Receive" +
                "when '2' then 'send'" +
                "else type" +
                "end as Type, body" +
                "from sms" +
                "order by date desc";*/
            /*
            result.moveToFirst();
            while(!result.isAfterLast()){
                arr_id_list.add(result.getString(0));
                arrlist.add(result.getString(1));
                result.moveToNext();
            }
            result.close();*/

            //Cursor result = database.rawQuery(sql, null);
            // query(uri, projection(data column명), selection(조건절), selectionArgs(selection 에 ? 가 있을 경우 값 치환), sort order)
            /* String[] projection = new String[]
            {
            People._ID,
            People._COUNT
            };

            String sortOrder = People._COUNT + "ASC";
            * */

            Cards.idsArrList.clear();
            ContentResolver cr = getContentResolver();
            for(int i = 0; i < cd.getCardInfoArrayList().size();i++)
            {
                CardInfo ci = cd.getCardInfoArrayList().get(i);
                ci.setSMSData(cr);

            }

            setupViewPager(viewPager);

        }
        catch(Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    public static final int REQUEST_CODE_LOGIN = 1;
    public static final int REQUEST_CODE_CARD_ADD = 2;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TextView te = (TextView) findViewById(R.id.userName);
        cd = CustomerDatabase.getInstance(this);

        te.setText(getResources().getString(R.string.user_name)+cd.getCustomerName());
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.nav_login:

                return true;
            case R.id.nav_Info_management:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.nav_add_card:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.nav_del_card:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent Data){
        super.onActivityResult(requestCode,resultCode,Data);

        if(requestCode == REQUEST_CODE_LOGIN){
            //Toast toast = Toast.makeText(getBaseContext(),resultCode,Toast.LENGTH_LONG);
            Toast toast = Toast.makeText(getBaseContext(),"로그인 테스트.",Toast.LENGTH_LONG);
            toast.show();
        }
        if(requestCode == REQUEST_CODE_CARD_ADD){
            Toast toast = Toast.makeText(getBaseContext(),"카드추가 테스트",Toast.LENGTH_LONG);
            toast.show();
        }
    }


    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CardListFragment(), getResources().getString(R.string.frg_card_management));
        adapter.addFragment(new TalkListFragment(), getResources().getString(R.string.frg_manager_talk));
        adapter.addFragment(new StatisticFragment(), getResources().getString(R.string.frg_used));
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void onClickLogin(MenuItem item) {
        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
        startActivityForResult(intent,REQUEST_CODE_LOGIN);
    }

    public void onClickAddCard(MenuItem item) {
        Intent intent = new Intent(getBaseContext(),CardAddFormActivity.class);
        startActivityForResult(intent,REQUEST_CODE_CARD_ADD);
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
