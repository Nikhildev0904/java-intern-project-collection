package com.cognitree.internship.twostacks.unified_stack;

import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public class TwoStacksTest {

    @Test
    public void testPushPopPeekLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.getLeftStack().push("A");
        assertEquals(twoStacks.getLeftStack().peek(), "A");
        assertEquals(twoStacks.getLeftStack().pop(), "A");
        assertTrue(twoStacks.getLeftStack().isEmpty());
    }

    @Test
    public void testPushPopPeekRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.getRightStack().push("A");
        assertEquals(twoStacks.getRightStack().peek(), "A");
        assertEquals(twoStacks.getRightStack().pop(), "A");
        assertTrue(twoStacks.getRightStack().isEmpty());
    }

    @Test
    public void testIsEmptyLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertTrue(twoStacks.getLeftStack().isEmpty());
        twoStacks.getLeftStack().push("A");
        assertFalse(twoStacks.getLeftStack().isEmpty());
        twoStacks.getLeftStack().pop();
        assertTrue(twoStacks.getLeftStack().isEmpty());
    }

    @Test
    public void testIsEmptyRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertTrue(twoStacks.getRightStack().isEmpty());
        twoStacks.getRightStack().push("A");
        assertFalse(twoStacks.getRightStack().isEmpty());
        twoStacks.getRightStack().pop();
        assertTrue(twoStacks.getRightStack().isEmpty());
    }

    @Test
    public void testSizeLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertEquals(twoStacks.getLeftStack().size(), 0);
        twoStacks.getLeftStack().push("A");
        assertEquals(twoStacks.getLeftStack().size(), 1);
        twoStacks.getLeftStack().push("B");
        assertEquals(twoStacks.getLeftStack().size(), 2);
    }

    @Test
    public void testSizeRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertEquals(twoStacks.getRightStack().size(), 0);
        twoStacks.getRightStack().push("A");
        assertEquals(twoStacks.getRightStack().size(), 1);
        twoStacks.getRightStack().push("B");
        assertEquals(twoStacks.getRightStack().size(), 2);
    }

    @Test
    public void testIsFullLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertFalse(twoStacks.getLeftStack().isFull());
        twoStacks.getLeftStack().push("A");
        assertFalse(twoStacks.getLeftStack().isFull());
        twoStacks.getLeftStack().push("B");
        assertTrue(twoStacks.getLeftStack().isFull());
    }

    @Test
    public void testSizeLeftRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertTrue(twoStacks.getLeftStack().isEmpty());
        twoStacks.getLeftStack().push("A");
        assertTrue(twoStacks.getRightStack().isEmpty());
        twoStacks.getLeftStack().push("B");
        assertTrue(twoStacks.getLeftStack().isFull());
        assertTrue(twoStacks.getRightStack().isFull());
    }

    @Test
    public void testIsFullRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertFalse(twoStacks.getRightStack().isFull());
        twoStacks.getRightStack().push("A");
        assertFalse(twoStacks.getRightStack().isFull());
        twoStacks.getRightStack().push("B");
        assertTrue(twoStacks.getRightStack().isFull());
    }

    @Test
    public void testIteratorLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.getLeftStack().push("A");
        twoStacks.getLeftStack().push("B");
        Iterator<String> iterator = twoStacks.getLeftIterator();
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.getRightStack().push("A");
        twoStacks.getRightStack().push("B");
        Iterator<String> iterator = twoStacks.getRightIterator();
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
        assertFalse(iterator.hasNext());
    }
}
