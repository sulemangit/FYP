/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.ArrayList;

/**
 *
 * @author RMS
 */
public class Dictionary {
    
    Integer count = 1;
    String word  = "";
    ArrayList<Character> arr  = new ArrayList<>();
    ArrayList<String> word_arr = new ArrayList<>();
    
    public Dictionary(String word) {
    this.word = word;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Dictionary{" + "count=" + count + ", word=" + word + ", arr=" + arr + '}';
    }
    
    
}
