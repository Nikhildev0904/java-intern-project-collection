package com.cognitree.internship.twostacks.individualops;

import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public class TwoStacksTest {
    @Test
    public void testPushPopPeekLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushLeft("A");
        assertEquals(twoStacks.peekLeft(), "A");
        assertEquals(twoStacks.popLeft(), "A");
        assertTrue(twoStacks.isEmptyLeft());
    }

    @Test
    public void testPushPopPeekRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushRight("A");
        assertEquals(twoStacks.peekRight(), "A");
        assertEquals(twoStacks.popRight(), "A");
        assertTrue(twoStacks.isEmptyRight());
    }

    @Test
    public void testSizeLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertEquals(twoStacks.sizeLeft(), 0);
        twoStacks.pushLeft("A");
        assertEquals(twoStacks.sizeLeft(), 1);
        twoStacks.pushLeft("B");
        assertEquals(twoStacks.sizeLeft(), 2);
    }

    @Test
    public void testSizeRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertEquals(twoStacks.sizeRight(), 0);
        twoStacks.pushRight("A");
        assertEquals(twoStacks.sizeRight(), 1);
        twoStacks.pushRight("B");
        assertEquals(twoStacks.sizeRight(), 2);
    }

    @Test
    public void testIsFullLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushRight("A");
        assertFalse(twoStacks.isFullLeft());
        for (int i = 0; i < 9; i++) {
            twoStacks.pushLeft("A");
        }
        assertTrue(twoStacks.isFullLeft());
    }

    @Test
    public void testIsFullRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushLeft("A");
        assertFalse(twoStacks.isFullRight());
        for (int i = 0; i < 9; i++) {
            twoStacks.pushRight("A");
        }
        assertTrue(twoStacks.isFullRight());
    }

    @Test
    public void testIsEmptyLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertTrue(twoStacks.isEmptyLeft());
        twoStacks.pushLeft("A");
        assertFalse(twoStacks.isEmptyLeft());
        twoStacks.popLeft();
        assertTrue(twoStacks.isEmptyLeft());
    }

    @Test
    public void testIsEmptyRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertTrue(twoStacks.isEmptyRight());
        twoStacks.pushRight("A");
        assertFalse(twoStacks.isEmptyRight());
        twoStacks.popRight();
        assertTrue(twoStacks.isEmptyRight());
    }

    @Test
    public void testSizeLeftRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        assertEquals(twoStacks.sizeLeft(), 0);
        assertEquals(twoStacks.sizeRight(), 0);
        twoStacks.pushLeft("A");
        assertEquals(twoStacks.sizeLeft(), 1);
        assertEquals(twoStacks.sizeRight(), 0);
        twoStacks.pushRight("B");
        assertEquals(twoStacks.sizeLeft(), 1);
        assertEquals(twoStacks.sizeRight(), 1);
    }

    @Test
    public void testGetIteratorLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushLeft("A");
        twoStacks.pushLeft("B");
        Iterator<String> iterator = twoStacks.getIteratorLeft();
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetIteratorRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(10);
        twoStacks.pushRight("A");
        twoStacks.pushRight("B");
        Iterator<String> iterator = twoStacks.getIteratorRight();
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
        assertFalse(iterator.hasNext());
    }
}