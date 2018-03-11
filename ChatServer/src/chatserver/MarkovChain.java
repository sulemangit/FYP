/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author RMS
 */
public class MarkovChain {

    public String[] getList() {
        return list;
    }

    public void setList(String[] list) {
        this.list = list;
    }

    String text = "";
    Integer order = 3;

    ArrayList<Dictionary> ngrams = new ArrayList<>();
    String[] list;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int contains_word(String word) {
//        System.out.println(ngrams.size());
        for (int i = 0; i < this.ngrams.size(); i++) {
            if (ngrams.get(i).word.equalsIgnoreCase(word)) {
                return i;
            }
        }
        return -1;
    }

    public void gram_by_word() {

        for (int i = 0; i < list.length; i++) {
            String word = list[i];
            Dictionary d_gram = new Dictionary(word);
            int located = contains_word(word);
            String next_word = "";
            if (located == -1) {
                ngrams.add(d_gram);
                if (i < list.length - 1) {
                    next_word = list[i + 1];
                }
                ngrams.get(contains_word(word)).word_arr.add(next_word);
                word = "";
            } else {
                Integer count = ngrams.get(located).getCount();
                count++;
                ngrams.get(located).setCount(count);

                if (i < list.length) {
                    word = list[i];
                }

                ngrams.get(located).word_arr.add(word);
                word = "";
            }
        }
//        for (Dictionary ngram : ngrams) {
//            System.out.println(ngram.toString());
//        }
    }

    public void generate_words_list() {
        list = text.split(" ");
//        System.out.println(text.length());
//        System.out.println(list.length);
    }

    public void grams() {

        for (int i = 0; i <= text.length() - order; i++) {
            String gram = text.substring(i, i + order);
            Dictionary d_gram = new Dictionary(gram);
            int located = contains(ngrams, gram);
            if (located == -1) {
                ngrams.add(d_gram);
                char c;
                if (i + order >= text.length()) {
                    c = text.charAt(i + order - 1);
                } else {
                    c = text.charAt(i + order);
                }

                ngrams.get(contains(ngrams, gram)).arr.add(c);
            } else {
                Integer count = ngrams.get(located).getCount();
                count++;
                ngrams.get(located).setCount(count);
                char c;
                if (i + order >= text.length()) {
                    c = text.charAt(i + order - 1);
                } else {
                    c = text.charAt(i + order);
                }
                ngrams.get(located).arr.add(c);
            }
        }
//        for (Dictionary ngram : ngrams) {
//            System.out.println(ngram.toString());
//        }
    }

    public int contains(ArrayList<Dictionary> arr, String gram) {

        if (arr.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getWord().equalsIgnoreCase(gram)) {
                return i;
            }
        }
        return -1;
    }

    public String markov() {

        Random random = new Random();
        String answer = "";
        int no_of_sentences = random.nextInt(3 - 1 + 1) + 1;
        int no_of_words = random.nextInt(30 - 1 + 1) + 1;
        for (int i = 0; i < no_of_sentences; i++) { // no of sentences
            String current_gram = text.substring(0, order);
            for (int j = 0; j < no_of_words; j++) {

                int index = contains(ngrams, current_gram);
                if (index < 0) {
                    continue;
                }
                int len = ngrams.get(index).arr.size() - 1;
                if (len < 0) {
                    continue;
                }
                int random_index = random.nextInt(len - 0 + 1) + 0;
                String next_gram = ngrams.get(index).arr.get(random_index) + "";
                String result = current_gram + next_gram;
                answer += result + "";
                current_gram = result.substring(result.length() - order, result.length());

            }
        }

        return answer;
    }

    public String markov_by_word(String message) throws IOException {

        String stop_words = FilesUtil.readTextFile("StopWordEnglish.txt");
        Random random = new Random();
        String answer = "";
        MessageCleaning messageCleaning = new MessageCleaning();
        messageCleaning.setMessage(message);
        messageCleaning.setList_of_stopWords(stop_words);
        messageCleaning.removeStopWords();
   
        int no_of_sentences = random.nextInt(3 - 1 + 1) + 1;
        int no_of_words = random.nextInt(10 - 1 + 1) + 1;
        for (int i = 0; i < no_of_sentences; i++) {
            String current_gram_word = messageCleaning.getState();

            for (int j = 0; j < no_of_words; j++) {
                int index = contains_word(current_gram_word);
                if (index < 0) {
                    continue;
                }
                int len = ngrams.get(index).word_arr.size() - 1;
                if (len < 0) {
                    continue;
                }
                int random_index = random.nextInt(len - 0 + 1) + 0;
                String next_gram_word = ngrams.get(index).word_arr.get(random_index);
                String result = next_gram_word;
                current_gram_word = list[random.nextInt(list.length - 1 - 0 + 1) + 0];
                answer += result + " ";
            }
        }
        return answer;
    }

    
}
