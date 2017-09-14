package raj.saraogi.vocabularymastervm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Database.DatabaseHelper;
import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 12-05-2016.
 */
public class StarPager extends FragmentPagerAdapter {
    int tabCount;
    String[] name;
    Bundle bundle = new Bundle();
    DatabaseHelper databaseHelper;
    List<Word> arrayList = new ArrayList<Word>();
    int pos;
    //Constructor to the class
    public StarPager(FragmentManager fm, int tabCount, int pos,List<Word> arrayList) {
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
        Bundle bundle =new Bundle();
        bundle.putSerializable("list", (Serializable) arrayList);
        bundle.putInt("pos",position);
        StarWordDetailsFragment starWordDetailsFragment = new StarWordDetailsFragment();
        starWordDetailsFragment.setArguments(bundle);
        return starWordDetailsFragment;
       // BlankFragment blankFragment = new BlankFragment(position);
//        blankFragment.setArguments(bundle);
       // return blankFragment;
        //Returning the current tabs
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
