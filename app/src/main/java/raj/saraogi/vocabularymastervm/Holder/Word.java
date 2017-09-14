package raj.saraogi.vocabularymastervm.Holder;

import java.io.Serializable;

/**
 * Created by Raj Saraogi on 22-04-2016.
 */
public class Word implements Serializable{
    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String acronyms) {
        this.use = acronyms;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String wname,desc,use,synonyms,example,url,id;

    public Word(){

    }

    public Word(String id,String wname,String desc,String acronyms,String synonyms,String example,String url){
        this.id=id;
        this.wname=wname;
        this.desc=desc;
        this.use=acronyms;
        this.synonyms=synonyms;
        this.example=example;
        this.url=url;

    }

    public Word(String name,String d,String s,String e){
        wname=name;
        desc=d;
        example=e;
        synonyms=s;
    }
}
