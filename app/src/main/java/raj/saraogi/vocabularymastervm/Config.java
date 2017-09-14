package raj.saraogi.vocabularymastervm;

import java.util.ArrayList;

import raj.saraogi.vocabularymastervm.Holder.Word;

/**
 * Created by Raj Saraogi on 22-04-2016.
 */
public class Config {
    static final String TAG_IMAGE_URL="Url";
    static final String TAG_WORD_NAME="Name";
    static final String TAG_WORD_DESC ="Desc";
    static final String TAG_WORD_USE   = "Use";
    static final String TAG_WORD_SYNONYMS = "Synonym";
    static final String TAG_WORD_EXAMPLE ="Example";
    static final String Word_Url="http://myandroiddevelopment.esy.es/words.php";
    static final String TAG_WORD_ID="Id";

    static final String TAG_USER_NAME="Name";
    static final String TAG_USER_EMAIL="Email";
    static final String TAG_USER_DATE="Date";
    static final String TAG_USER_SELECTED="Selected";
    public static ArrayList<Word> arrayList =new ArrayList<Word>();

}
