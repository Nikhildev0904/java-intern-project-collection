package com.cognitree.internship.exp_eval;

import com.cognitree.internship.exp_eval.tokens.Token;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpParserTest {

    @Test
    void testExtractVariablesReturnsCorrectSet() {
        ExpParser parser = new ExpParser("(a+bc)*2+d*c+10");
        Set<String> variables = parser.extractVariables();
        assertEquals(4, variables.size());
        assertTrue(variables.contains("a"));
        assertTrue(variables.contains("bc"));
        assertTrue(variables.contains("c"));
        assertTrue(variables.contains("d"));
    }

    @Test
    void testGetParsedExpressionReturnsCorrectTokensList() {
        ExpParser parser = new ExpParser("(a+b)*2+d");
        List<Token> parsedExpression = parser.getParsedExpression();
        assertEquals(7, parsedExpression.size());
        parser = new ExpParser("a + b*3.14");
        parsedExpression = parser.getParsedExpression();
        assertEquals(5, parsedExpression.size());
    }

    @Test
    void testGetParseExpressionThrowsExceptionWhenInvalidOperatorInExpression() {
        assertThrows(RuntimeException.class, () -> new ExpParser("(a+b)&2+d*"));
    }

    @Test
    void testGetParseExpressionThrowsExceptionWhenInvalidNumberInExpression() {
        assertThrows(RuntimeException.class, () -> new ExpParser("(a+b)*2+d*.1.2"));
    }
}