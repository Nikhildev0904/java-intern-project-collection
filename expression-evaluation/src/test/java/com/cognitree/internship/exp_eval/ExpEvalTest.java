package com.cognitree.internship.exp_eval;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpEvalTest {

    @Test
    void testExtractVariablesReturnsCorrectSet() {
        ExpEval evaluator = new ExpEval("(a+b)*2+d*c+10");
        Set<String> variables = evaluator.getVariables();
        assertEquals(4, variables.size());
        assertTrue(variables.contains("a"));
        assertTrue(variables.contains("b"));
        assertTrue(variables.contains("c"));
        assertTrue(variables.contains("d"));
    }

    @Test
    void testCalculateReturnsCorrectResult() {
        ExpEval evaluator = new ExpEval("(a+b)*2+d*c+10");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 2.0);
        variables.put("b", 3.3);
        variables.put("c", 4.0);
        variables.put("d", 5.0);
        assertEquals(40.6, evaluator.calculate(variables));
    }


    @Test
    void testCalculateThrowsExceptionForInsufficientOperands() {
        ExpEval evaluator = new ExpEval("(a+b)*2+d*c+");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 2.0);
        variables.put("b", 3.3);
        variables.put("c", 4.0);
        variables.put("d", 5.0);
        assertThrows(RuntimeException.class, () -> evaluator.calculate(variables));
    }

    @Test
    void testCalculateThrowsExceptionForUndefinedVariable() {
        ExpEval evaluator = new ExpEval("(a+b)*2+d*c*10");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 2.0);
        variables.put("b", 3.3);
        variables.put("c", 4.0);
        assertThrows(RuntimeException.class, () -> evaluator.calculate(variables));
    }

    @Test
    void testCalculateThrowsArithmeticExceptionForZeroDivision() {
        ExpEval evaluator = new ExpEval("a/b");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 2.0);
        variables.put("b", 0.0);
        assertThrows(ArithmeticException.class, () -> evaluator.calculate(variables));
    }

    @Test
    void testCalculateHandlesNestedExpressionsCorrectly() {
        ExpEval evaluator = new ExpEval("((a+b)*c)^d");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 1.0);
        variables.put("b", 2.0);
        variables.put("c", 3.0);
        variables.put("d", 2.0);
        assertEquals(81.0, evaluator.calculate(variables));
    }

    @Test
    void testCalculateHandlesNegativeNumbersCorrectly() {
        ExpEval evaluator = new ExpEval("a-b*c");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", -2.0);
        variables.put("b", 3.0);
        variables.put("c", -4.0);
        assertEquals(10.0, evaluator.calculate(variables));
    }

    @Test
    void testParenthesesAffectOperatorPrecedenceCorrectly() {
        ExpEval evaluator1 = new ExpEval("a+b*c");
        ExpEval evaluator2 = new ExpEval("(a+b)*c");
        Map<String, Double> variables = new HashMap<>();
        variables.put("a", 2.0);
        variables.put("b", 3.0);
        variables.put("c", 4.0);
        assertEquals(14.0, evaluator1.calculate(variables));
        assertEquals(20.0, evaluator2.calculate(variables));
    }
}