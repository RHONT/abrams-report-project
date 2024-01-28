package com.abrams.finder;

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
            double[] doubles= Arrays.stream(split).mapToDouble(Double::valueOf).toArray();
            return ((doubles[0]/1000d)*(doubles[1]/1000d))*doubles[2];
        }
        return 0;
    }
}
