package com.mostwelcome.util;

import com.mostwelcome.internal.AddHelper;

public class Calculator {

    AddHelper helper = new AddHelper();
    public int add(int a, int b) {
       return helper.add(a,b);
    }
}
