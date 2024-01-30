package com.abrams.utils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilClass {
    private static final String _notZipExp = "\\.zip|\\.rar";
    private static final String _digitExp = "[0-9]{1,}.{1}[0-9]{1,}\\s[0-9]{1,}";
    private static final String _splitExp = "\\D";

    public static boolean isArchive(Path path) {
        Pattern pattern = Pattern.compile(_notZipExp);
        Matcher matcher = pattern.matcher(path.toString());
        return matcher.find();
    }

    public static double getSquareMeters(String fileName){
        Pattern pattern = Pattern.compile(_digitExp);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            String group = matcher.group();
            Pattern splitPattern=Pattern.compile(_splitExp);
            String[] split = splitPattern.split(group);
            return calcSquareMeters(split);
        }
        return 0;
    }

    private static double calcSquareMeters(String[] inputStrings){
        double[] doubles= Arrays.stream(inputStrings).mapToDouble(Double::valueOf).toArray();
        double square = (doubles[0]/1000d)*(doubles[1]/1000d);
        double amount=doubles[2];
        return square*amount;
    }
}
