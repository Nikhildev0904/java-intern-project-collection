package com.cognitree.internship.iterators;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class SlidingWindowIteratorTest {

    @Test
    public void testSlidingWindowIterator() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(numbers, 3);
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(1, 2, 3));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(2, 3, 4));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(3, 4, 5));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(4, 5, 6));
        assertFalse(slidingWindowIterator.hasNext());
    }


    @Test
    public void testSlidingWindowIteratorWithStrings() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e", "f");
        SlidingWindowIterator<String> slidingWindowIterator = new SlidingWindowIterator<>(strings, 3);
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList("a", "b", "c"));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList("b", "c", "d"));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList("c", "d", "e"));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList("d", "e", "f"));
        assertFalse(slidingWindowIterator.hasNext());
    }

    @Test
    public void testSlidingWindowIteratorWithSet() {
        Set<Integer> numbers = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(numbers, 3);
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(1, 2, 3));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(2, 3, 4));
        assertTrue(slidingWindowIterator.hasNext());
        assertEquals(slidingWindowIterator.next(), Arrays.asList(3, 4, 5));
        assertFalse(slidingWindowIterator.hasNext());
    }

    @Test
    public void testEmptySlidingWindowIterator() {
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(new ArrayList<>(), 3);
        assertFalse(slidingWindowIterator.hasNext());
        assertThrows(NoSuchElementException.class, slidingWindowIterator::next);
    }

    @Test
    public void testSlidingWindowIteratorThrowsExceptionWhenIteratorConsumed() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        SlidingWindowIterator<Integer> slidingWindowIterator = new SlidingWindowIterator<>(numbers, 3);
        assertTrue(slidingWindowIterator.hasNext());
        slidingWindowIterator.next();
        assertFalse(slidingWindowIterator.hasNext());
        assertThrows(NoSuchElementException.class, slidingWindowIterator::next);
    }
}