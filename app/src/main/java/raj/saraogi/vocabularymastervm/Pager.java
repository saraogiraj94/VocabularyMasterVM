package raj.saraogi.vocabularymastervm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 14-04-2016.
 */
public class Pager extends FragmentPagerAdapter {
    //integer to count number of tabs
    int tabCount;
    String[] name;
    Bundle bundle = new Bundle();
    ArrayList<Word> arrayList = new ArrayList<Word>();
    int pos;
    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount,int pos) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
        this.pos=pos;
        this.arrayList = arrayList;
        this.bundle=bundle;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
       // bundle.putInt("pos",position);
       // Log.d("pos", String.valueOf(position));
       BlankFragment blankFragment = new BlankFragment();
//        blankFragment.setArguments(bundle);
        return blankFragment;
        //Returning the current tabs
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
