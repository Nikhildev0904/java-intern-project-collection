package com.cognitree.internship.infinite_stream;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularQueueTest {

    @Test
    void enqueueReturnsNullWhenQueueNotNullAndIncrementsLength() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        assertNull(queue.enqueue(1));
        assertEquals(1, queue.getLength());
        assertNull(queue.enqueue(3));
        assertEquals(2, queue.getLength());
    }

    @Test
    void enqueueOverwritesOldValueWhenQueueFullAndReturnsIt() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(3);
        //queue is full
        assertEquals(1, queue.enqueue(2));
        assertEquals(2, queue.getLength());
    }

    @Test
    void iteratorReturnsCorrectValues() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.enqueue(1);
        queue.enqueue(3);
        queue.enqueue(7);
        Iterator<Integer> iterator = queue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
    }

    @Test
    void iteratorThrowsExceptionWhenQueueEmpty() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(3);
        Iterator<Integer> iterator = queue.iterator();
        iterator.next();
        iterator.next();
        // No more elements left
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}