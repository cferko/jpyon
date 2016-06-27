package com.x5e.jpyon;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.*;

/**
 * Created by darin on 6/26/16.
 */
public class TestToken {

    @Test
    public void testOne() throws Exception {
        String input = "['hello',37]";
        byte[] bytes = input.getBytes();
        Token token = Token.readOne(bytes,0);
        System.out.println(token.toString());
    }

    @Test
    public void testMany() throws Exception {
        String input = "[37,-3,+1,[]]";
        byte[] bytes = input.getBytes();
        List<Token> tokens = Token.readMany(bytes);
        assertEquals(tokens.get(0),new Token('['));
        assertEquals(tokens.get(1),new Token(Token.NUMBER,37));
        assertEquals(tokens.get(2),new Token(','));
        assertEquals(tokens.get(3),new Token(Token.NUMBER,-3));
        assertEquals(tokens.get(5),new Token(Token.NUMBER,1));
        assertEquals(tokens.get(7),new Token('['));
        assertEquals(tokens.get(8),new Token(']'));
        assertEquals(tokens.get(9),new Token(']'));
    }

    @Test
    public void testSyntax() throws Exception {
        List<Token> tokens = Token.readMany("[]{},:=()");
        assertEquals(tokens.remove(0),new Token('['));
        assertEquals(tokens.remove(0),new Token(']'));
        assertEquals(tokens.remove(0),new Token('{'));
        assertEquals(tokens.remove(0),new Token('}'));
        assertEquals(tokens.remove(0),new Token(','));
        assertEquals(tokens.remove(0),new Token(':'));
        assertEquals(tokens.remove(0),new Token('='));
        assertEquals(tokens.remove(0),new Token('('));
        assertEquals(tokens.remove(0),new Token(')'));
    }


    @Test
    public void testComments() throws Exception {
        List<Token> tokens = Token.readMany("[4#3\n,9/* foo \n */ 0 // cheese fries \n22 ");
        assertEquals(tokens.remove(0),new Token('['));
        assertEquals(tokens.remove(0),new Token(Token.NUMBER,4));
        assertEquals(tokens.remove(0),new Token(','));
        assertEquals(tokens.remove(0),new Token(Token.NUMBER,9));
        assertEquals(tokens.remove(0),new Token(Token.NUMBER,0));
        assertEquals(tokens.remove(0),new Token(Token.NUMBER,22));
        assertEquals(tokens.remove(0),new Token(Token.END));
    }
}