package com.task1;

/**
 * Created by Dennis
 *
 * on 3/15/2016.
 */
public class PrefixMatches {
    private Trie trie;

    //TODO
    public int add(String... strings) {
        return 0;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public int size() {
        return trie.size();
    }

    //TODO
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        return null;
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
