package com.cognitree.internship.iterators;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

public class BatchedIteratorTest {

    @Test
    public void testEmptyBatchedIterator() {
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(new ArrayList<>(), 3);
        assertFalse(batchedIterator.hasNext());
    }

    @Test
    public void testBatchedIterator() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(numbers, 3);
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList(1, 2, 3));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList(4, 5, 6));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList(7, 8, 9));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), List.of(10));
        assertFalse(batchedIterator.hasNext());
    }

    @Test
    public void testBatchedIteratorThrowsExceptionWhenIteratorConsumed() {
        List<Integer> numbers = Arrays.asList(1, 2);
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(numbers, 1);
        batchedIterator.next();
        batchedIterator.next();
        //Iterator consumed
        assertThrows(NoSuchElementException.class, () -> batchedIterator.next());
    }

    @Test
    public void testBatchedIteratorWithStrings() {
        List<String> strings = Arrays.asList("a", "b", "c", "d", "e", "f");
        BatchedIterator<String> batchedIterator = new BatchedIterator<>(strings, 2);
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList("a", "b"));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList("c", "d"));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList("e", "f"));
        assertFalse(batchedIterator.hasNext());
    }

    @Test
    public void testBatchedIteratorWithSet() {
        Set<Integer> numbers = new LinkedHashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        BatchedIterator<Integer> batchedIterator = new BatchedIterator<>(numbers, 2);
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList(1, 2));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), Arrays.asList(3, 4));
        assertTrue(batchedIterator.hasNext());
        assertEquals(batchedIterator.next(), List.of(5));
        assertFalse(batchedIterator.hasNext());
    }
}