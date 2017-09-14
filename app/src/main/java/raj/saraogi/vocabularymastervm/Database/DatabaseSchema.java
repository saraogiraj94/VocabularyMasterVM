package raj.saraogi.vocabularymastervm.Database;

import android.provider.BaseColumns;

/**
 * Created by Raj Saraogi on 10-05-2016.
 */
public class DatabaseSchema {
    public static abstract class Words implements BaseColumns{
        public static final String STAR_WORDS_TABLE ="starwords";
        public static final String STAR_WORD_ID="word_id";
        public static final String STAR_WORD_NAME="word_name";
        public static final String STAR_WORD_DESC="word_desc";
        public static final String STAR_WORD_SYNONYMS="word_synonyms";
        public static final String STAR_WORD_EXAMPLE="word_example";
        public static final String STAR_WORD_USE="word_use";
        public static final String STAR_WORD_IMAGE_PATH="word_image";

    }
    public static abstract class MyD implements BaseColumns{
        public static final String MY_DICTIONARY_TABLE="mydictionary";
        public static final String MY_DICTIONARY_WORD="myd_word";
        public static final String MY_DICTIONARY_DESC="myd_desc";
        public static final String MY_DICTIONARY_SYN="myd_syn";
        public static final String MY_DICTIONARY_EXAMPLE="myd_example" ;
    }
}
