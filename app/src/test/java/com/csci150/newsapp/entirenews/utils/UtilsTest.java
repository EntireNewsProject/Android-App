package com.csci150.newsapp.entirenews.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void print() {
    }

    @Test
    public void print1() {
    }

    @Test
    public void print2() {
    }

    @Test
    public void createSlug() {
        String input = "This is a     title";
        String expected = "this-is-a-title";
        String output = Utils.createSlug(input);
        assertEquals(expected,output);
    }
}