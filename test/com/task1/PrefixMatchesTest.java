package com.task1;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PrefixMatchesTest {
	private PrefixMatches obj;
	private String[] words;

	@Mock
	private Trie trieMock;

	@InjectMocks
	private PrefixMatches objWithMock;
	
	@Before
	public void init() {
		obj = new PrefixMatches(new RWayTrie());
		
		words = new String[] {"one", "two", "to", "i", "anakonda", "string with spaces"};
		
		obj.add(words);

		objWithMock = new PrefixMatches();

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAdd() {
		assertTrue(obj.add(words) == 6);
		
		assertTrue(obj.add(null) == 0);
	}

	@Test
	public void testContains() {
		String[] strings = {"one", "two", "anakonda", "string",
							"with", "spaces"};
		
		
		for(String s : strings) {
			assertTrue(obj.contains(s));
		}
		
		assertFalse(obj.contains("to"));
		assertFalse(obj.contains("another"));
	}

	@Test
	public void testDelete() {
		
		assertEquals(6, obj.size());
		
		assertTrue(obj.delete("one"));
		
		assertEquals(5, obj.size());
		
		assertFalse(obj.contains("one"));
		
		assertFalse(obj.delete("one"));
		
		assertFalse(obj.delete("another"));
	}

	@Test
	public void testSize() {
		assertEquals(6, obj.size());
	}

	@Test
	public void testWordsWithPrefixStringInt() {
		PrefixMatches testObj = new PrefixMatches();
		
		testObj.add("abc", "abcd", "abce", "abcf", "abcda", "abcxxx");
		
		Iterable<String> result1 = testObj.wordsWithPrefix("abc", 3);
		List<String> expectedResult1 = Arrays.asList("abc", "abcd", "abce", "abcf", "abcda");

		int counter1 = 0;

		for (String s : result1) {
			assertTrue(expectedResult1.contains(s));
			counter1++;
		}

		assertEquals(expectedResult1.size(), counter1);

		Iterable<String> result2 = testObj.wordsWithPrefix("abc", 2);
		List<String> expectedResult2 = Arrays.asList("abc", "abcd", "abce", "abcf");

		int counter2 = 0;

		for (String s : result2) {
			assertTrue(expectedResult2.contains(s));
			counter2++;
		}

		assertEquals(expectedResult2.size(), counter2);
	}

	@Test
	public void testWordsWithPrefixString() {
		Iterable<String> result = obj.wordsWithPrefix("on");
		
		Iterator<String> iterator = result.iterator();
		
		assertTrue(iterator.hasNext());
		
		assertEquals("one", iterator.next());
	}

	@Test
	public void testLargeAmount() throws Exception {
		PrefixMatches testObject = new PrefixMatches(new RWayTrie());

		BufferedReader reader = new BufferedReader(new FileReader("test/test_resources/words-333333.txt"));

		final String EMPTY_REPLACEMENT = "";
		String content;

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

	@Test
	public void testWithMocks() {
		String string = "a long string with spaces";

		objWithMock.add(string);

		verify(trieMock, times(4)).add(any(Tuple.class));
	}
}
