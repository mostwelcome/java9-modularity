package util;


import internal.AddHelper;

public class Calculator {

    AddHelper helper = new AddHelper();
    public int add(int a, int b) {
       return helper.add(a,b);
    }
}
