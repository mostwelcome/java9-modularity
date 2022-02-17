package com.mostwelcome.main;

import util.Calculator;

public class Runner  {
    public static void main(String[] args) {
        System.out.println("hello");

        Calculator cal = new Calculator();
        int result = cal.add(10,30);
        System.out.println(result);
    }
}
