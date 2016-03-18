package com.task1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class PrefixMatchesTest {
	private PrefixMatches obj;
	private String[] words;
	
	@Before
	public void init() {
		obj = new PrefixMatches(new RWayTrie());
		
		words = new String[] {"one", "two", "to", "i", "anakonda", "string with spaces"};
		
		obj.add(words);
	}

	@Test
	public void testAdd() {
		assertTrue(obj.add(words) == 6);
		
		assertTrue(obj.add(null) == 0);
	}

	@Test
	public void testContains() {
		assertTrue(obj.contains("one"));
		assertTrue(obj.contains("two"));
		assertTrue(obj.contains("anakonda"));
		assertTrue(obj.contains("string"));
		assertTrue(obj.contains("with"));
		assertTrue(obj.contains("spaces"));
		assertFalse(obj.contains("to"));
		assertFalse(obj.contains("another"));
	}

	@Test
	public void testDelete() {
		
	}

	@Test
	public void testSize() {
		assertEquals(6, obj.size());
	}

	@Test
	public void testWordsWithPrefixStringInt() {
		
	}

	@Test
	public void testWordsWithPrefixString() {
		
	}

	@Test
	public void testLargeAmount() throws Exception {
		PrefixMatches testObject = new PrefixMatches(new RWayTrie());

		BufferedReader reader = new BufferedReader(new FileReader("test/test_resources/words-333333.txt"));

		final String EMPTY_REPLACEMENT = "";
		String content = null;

		while ((content = reader.readLine()) != null) {
			String editedContent = content.replaceAll("[\\t\\d]", EMPTY_REPLACEMENT);
			testObject.add(editedContent);
		}

		reader.close();

		assertTrue(testObject.size() > 100000);

		FileWriter writer = new FileWriter("test/test_resources/words-out.txt");

		Iterable<String> iter = testObject.wordsWithPrefix("a");

		for (String s : iter) {
			writer.write(s + "\n");
		}

		writer.close();
	}

}
