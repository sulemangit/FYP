/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author RMS
 */
public class MessageCleaning {

    String message;

    String[] list_of_stopWords;
    ArrayList<String> content_words = new ArrayList<>();

    public ArrayList<String> getContent_words() {
        return content_words;
    }

    public void setContent_words(ArrayList<String> content_words) {
        this.content_words = content_words;
    }

    public String[] getList_of_stopWords() {
        return list_of_stopWords;
    }

    public void setList_of_stopWords(String stop_words) {
        this.list_of_stopWords = stop_words.split(" ");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void removeStopWords() {

        String[] list_of_words = this.message.split(" ");

        for (int i = 0; i < list_of_stopWords.length; i++) {
            for (int j = 0; j < list_of_words.length; j++) {
                if (!list_of_stopWords.equals(list_of_words)) {
                    content_words.add(list_of_words[j]);
                }
            }
        }
    }

    public String getState() {
        
        Random rand = new Random();
        int len = getContent_words().size() - 1;
        int index = rand.nextInt(len - 0 + 1) + 0;
        return getContent_words().get(index);
    }
}
