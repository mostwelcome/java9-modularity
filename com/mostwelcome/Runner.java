package com.mostwelcome;

import com.mostwelcome.util.Calculator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    private static final Logger logger = Logger.getLogger(String.valueOf(Runner.class));


    public static void main(String[] args) {

        Calculator cal = new Calculator();
        int result = cal.add(10,30);
        logger.log(Level.INFO, "Result: {0} ", result);
    }
}
