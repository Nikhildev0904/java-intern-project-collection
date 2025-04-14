package com.cognitree.internship.twostacks.type_checked;

import com.cognitree.internship.twostacks.type_checked.TwoStacks.StackType;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.testng.Assert.*;

public class TwoStacksTest {

    @Test
    public void testPushPopPeekLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(1);
        twoStacks.push("A", StackType.LEFT);
        assertEquals(twoStacks.peek(StackType.LEFT), "A");
        assertEquals(twoStacks.pop(StackType.LEFT), "A");
        assertTrue(twoStacks.isEmpty(StackType.LEFT));
    }

    @Test
    public void testPushPopPeekRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(1);
        twoStacks.push("A", StackType.RIGHT);
        assertEquals(twoStacks.peek(StackType.RIGHT), "A");
        assertEquals(twoStacks.pop(StackType.RIGHT), "A");
        assertTrue(twoStacks.isEmpty(StackType.RIGHT));
    }

    @Test
    public void testIsEmptyLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertTrue(twoStacks.isEmpty(StackType.LEFT));
        twoStacks.push("A", StackType.LEFT);
        assertFalse(twoStacks.isEmpty(StackType.LEFT));
        twoStacks.pop(StackType.LEFT);
        assertTrue(twoStacks.isEmpty(StackType.LEFT));
    }

    @Test
    public void testIsEmptyRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertTrue(twoStacks.isEmpty(StackType.RIGHT));
        twoStacks.push("A", StackType.RIGHT);
        assertFalse(twoStacks.isEmpty(StackType.RIGHT));
        twoStacks.pop(StackType.RIGHT);
        assertTrue(twoStacks.isEmpty(StackType.RIGHT));
    }

    @Test
    public void testSizeLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertEquals(twoStacks.size(StackType.LEFT), 0);
        twoStacks.push("A", StackType.LEFT);
        assertEquals(twoStacks.size(StackType.LEFT), 1);
        twoStacks.push("B", StackType.LEFT);
        assertEquals(twoStacks.size(StackType.LEFT), 2);
    }

    @Test
    public void testSizeRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertEquals(twoStacks.size(StackType.RIGHT), 0);
        twoStacks.push("A", StackType.RIGHT);
        assertEquals(twoStacks.size(StackType.RIGHT), 1);
        twoStacks.push("B", StackType.RIGHT);
        assertEquals(twoStacks.size(StackType.RIGHT), 2);
    }

    @Test
    public void testSizeLeftRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertEquals(twoStacks.size(StackType.LEFT), 0);
        assertEquals(twoStacks.size(StackType.RIGHT), 0);
        twoStacks.push("A", StackType.LEFT);
        assertEquals(twoStacks.size(StackType.LEFT), 1);
        assertEquals(twoStacks.size(StackType.RIGHT), 0);
        twoStacks.push("B", StackType.RIGHT);
        assertEquals(twoStacks.size(StackType.LEFT), 1);
        assertEquals(twoStacks.size(StackType.RIGHT), 1);
        assertTrue(twoStacks.isFull(StackType.LEFT));
        assertTrue(twoStacks.isFull(StackType.RIGHT));
    }

    @Test
    public void testIsFullLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertFalse(twoStacks.isFull(StackType.LEFT));
        twoStacks.push("A", StackType.LEFT);
        assertFalse(twoStacks.isFull(StackType.LEFT));
        twoStacks.push("B", StackType.LEFT);
        assertTrue(twoStacks.isFull(StackType.LEFT));
    }

    @Test
    public void testIsFullRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        assertFalse(twoStacks.isFull(StackType.RIGHT));
        twoStacks.push("A", StackType.RIGHT);
        assertFalse(twoStacks.isFull(StackType.RIGHT));
        twoStacks.push("B", StackType.RIGHT);
        assertTrue(twoStacks.isFull(StackType.RIGHT));
    }

    @Test
    public void testIteratorLeft() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        twoStacks.push("A", StackType.LEFT);
        twoStacks.push("B", StackType.LEFT);
        Iterator<String> iterator = twoStacks.getIterator(StackType.LEFT);
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
    }

    @Test
    public void testIteratorRight() {
        TwoStacks<String> twoStacks = new TwoStacks<>(2);
        twoStacks.push("A", StackType.RIGHT);
        twoStacks.push("B", StackType.RIGHT);
        Iterator<String> iterator = twoStacks.getIterator(StackType.RIGHT);
        assertEquals(iterator.next(), "B");
        assertEquals(iterator.next(), "A");
    }
}