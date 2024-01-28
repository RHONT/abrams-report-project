package com.abrams.finder;

import java.io.IOException;

public class RunProgram {
    public static void main(String[] args) throws IOException {
        Finder finder = new Finder("Яркие","C:/labaratory/dirTest");
//        finder.PrintToConsole();
        ExcelWriter.writeExcel(finder.someMethod());
    }
}
