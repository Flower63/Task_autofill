package com.task1;
/**
 * Created by Dennis
 *
 * on 3/15/2016.
 */
public interface Trie {
    void add(Tuple tuple);

    boolean contains(String word);

    boolean delete(String word);

    Iterable<String> words();

    Iterable<String> wordsWithPrefix(String prefix);

    int size();
}
