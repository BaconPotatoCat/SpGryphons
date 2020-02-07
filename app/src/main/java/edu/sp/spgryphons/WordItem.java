package edu.sp.spgryphons;

public class WordItem {
    private int mId;
    private String mWord;
    private String mDate;

    public WordItem(){
    }

    public int getId(){
        return this.mId;
    }
    public String getWord(){
        return this.mWord;
    }
    public String getDate(){return this.mDate;}
    public void setId(int id){
        this.mId=id;
    }
    public void  setWord(String word){
        this.mWord = word;
    }
    public void setmDate(String date){this.mDate = date;}

}

