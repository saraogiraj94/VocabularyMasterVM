package raj.saraogi.vocabularymastervm.About;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.Serializable;

import raj.saraogi.vocabularymastervm.R;
import raj.saraogi.vocabularymastervm.SlidePageFragment;

public class AboutApp extends AppCompatActivity {
    ViewPager viewPager ;
    private PagerAdapter pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        Toolbar toolbar = (Toolbar)findViewById(R.id.to9);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("About VM");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewPager=(ViewPager)findViewById(R.id.pager9);
        pAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pAdapter);

    }
    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Bundle b =new Bundle();
            b.putInt("pos",pos);
            SlideShows slideShows = new SlideShows();
            slideShows.setArguments(b);
           // slidePageFragment.setArguments(bundle);
            return slideShows;
            //return SlidePageFragment.newInstance(pos);
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
