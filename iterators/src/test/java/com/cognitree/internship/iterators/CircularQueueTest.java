package com.cognitree.internship.iterators;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class CircularQueueTest {

    @Test
    public void testGetLength() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        assertEquals(queue.getLength(), 0);
        queue.slide(1);
        assertEquals(queue.getLength(), 1);
        queue.slide(3);
        assertEquals(queue.getLength(), 2);
    }

    @Test
    public void testGetWindowElements() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.slide(1);
        queue.slide(2);
        queue.slide(3);
        List<Integer> windowElements = queue.getWindowElements();
        assertEquals(Arrays.asList(1, 2, 3), windowElements);
        queue.slide(4);
        windowElements = queue.getWindowElements();
        assertEquals(Arrays.asList(2, 3, 4), windowElements);
    }

    @Test
    void iteratorReturnsCorrectValues() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.slide(1);
        queue.slide(3);
        queue.slide(7);
        Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), 1);
        assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), 3);
        assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), 7);
    }

    @Test
    void iteratorThrowsExceptionWhenQueueEmpty() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.slide(1);
        queue.slide(3);
        Iterator<Integer> iterator = queue.iterator();
        iterator.next();
        iterator.next();
        // No more elements left
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}