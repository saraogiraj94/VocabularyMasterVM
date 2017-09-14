package raj.saraogi.vocabularymastervm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 10-05-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "vm.db";

    public String CREATE_STAR_TABLE = "create table " + DatabaseSchema.Words.STAR_WORDS_TABLE
            + " ( " + DatabaseSchema.Words.STAR_WORD_ID + " text , "
            + DatabaseSchema.Words.STAR_WORD_NAME + " text , "
            + DatabaseSchema.Words.STAR_WORD_DESC + " text ,"
            + DatabaseSchema.Words.STAR_WORD_SYNONYMS + " text ,"
            + DatabaseSchema.Words.STAR_WORD_USE + " text ,"
            + DatabaseSchema.Words.STAR_WORD_EXAMPLE + " text ,"
            + DatabaseSchema.Words.STAR_WORD_IMAGE_PATH + " text " + ")";
    public String CREATE_MYD_TABLE = "create table " + DatabaseSchema.MyD.MY_DICTIONARY_TABLE
            + " ( " + DatabaseSchema.MyD.MY_DICTIONARY_WORD + " text ,"
            + DatabaseSchema.MyD.MY_DICTIONARY_DESC + " text ,"
            + DatabaseSchema.MyD.MY_DICTIONARY_SYN + " text ,"
            + DatabaseSchema.MyD.MY_DICTIONARY_EXAMPLE + " text " + ")";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STAR_TABLE);
        db.execSQL(CREATE_MYD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist " + DatabaseSchema.Words.STAR_WORDS_TABLE);
        onCreate(db);
    }

    public boolean addStarWord(String id, String wname, String w_desc, String w_syn, String w_use, String w_example, String w_img_path) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseSchema.Words.STAR_WORD_ID, id);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_NAME, wname);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_DESC, w_desc);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_SYNONYMS, w_syn);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_USE, w_use);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_EXAMPLE, w_example);
            contentValues.put(DatabaseSchema.Words.STAR_WORD_IMAGE_PATH, w_img_path);

            getWritableDatabase().insert(DatabaseSchema.Words.STAR_WORDS_TABLE, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Word> getStarWordsList() {
        try {

            List<Word> list = new ArrayList<>();
            Cursor cursor = getReadableDatabase().rawQuery("select * from " + DatabaseSchema.Words.STAR_WORDS_TABLE + " order by " + DatabaseSchema.Words.STAR_WORD_NAME + " ASC ", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Word object = new Word(cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_NAME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_DESC)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_SYNONYMS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_USE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_EXAMPLE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.Words.STAR_WORD_IMAGE_PATH)));

                cursor.moveToNext();
                list.add(object);
            }

            cursor.close();
            return list;

        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int check(String word) {
        Cursor cursor;
        try {
            cursor = getReadableDatabase().rawQuery("select * from " + DatabaseSchema.Words.STAR_WORDS_TABLE + " where " + DatabaseSchema.Words.STAR_WORD_NAME + " = '" + word + "'", null);
            int i = cursor.getCount();
            Log.e("Cursor", String.valueOf(i));
            return i;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String deleteAStarWord(String word) {
        try {
            Cursor cursor1 = getReadableDatabase().rawQuery("select * from " + DatabaseSchema.Words.STAR_WORDS_TABLE + " where " + DatabaseSchema.Words.STAR_WORD_NAME + " = '" + word + "'", null);
            String s = cursor1.getString(cursor1.getColumnIndex(DatabaseSchema.Words.STAR_WORD_IMAGE_PATH));
            cursor1.close();
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DatabaseSchema.Words.STAR_WORDS_TABLE, DatabaseSchema.Words.STAR_WORD_NAME + " = ?", new String[]{word});
            return s;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addMyDWord(String w, String d, String s, String e) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_WORD, w);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_DESC, d);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_SYN, s);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_EXAMPLE, e);

            getWritableDatabase().insert(DatabaseSchema.MyD.MY_DICTIONARY_TABLE, null, contentValues);
            return true;
        } catch (SQLiteException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public List<Word> getMyDList() {
        try {
            List<Word> list = new ArrayList<>();
            Cursor cursor = getReadableDatabase().rawQuery("select * from " + DatabaseSchema.MyD.MY_DICTIONARY_TABLE + " order by " + DatabaseSchema.MyD.MY_DICTIONARY_WORD + " ASC ", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Word word = new Word(cursor.getString(cursor.getColumnIndex(DatabaseSchema.MyD.MY_DICTIONARY_WORD)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.MyD.MY_DICTIONARY_DESC)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.MyD.MY_DICTIONARY_SYN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseSchema.MyD.MY_DICTIONARY_EXAMPLE)));
                cursor.moveToNext();
                list.add(word);
            }
            cursor.close();
            return list;
        } catch (SQLiteException s) {
            s.printStackTrace();
            return null;
        }
    }

    public Boolean updateMyD(String w,String d,String s,String e){
        try{
            SQLiteDatabase db =this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_WORD, w);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_DESC, d);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_SYN, s);
            contentValues.put(DatabaseSchema.MyD.MY_DICTIONARY_EXAMPLE, e);
            db.update(DatabaseSchema.MyD.MY_DICTIONARY_TABLE,contentValues, DatabaseSchema.MyD.MY_DICTIONARY_WORD +" = ? ",new String[]{w});
              return true;
        }catch (SQLiteException se){
            se.printStackTrace();
            return false;
        }
    }
}