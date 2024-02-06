package com.abrams.parsers;

import com.abrams.etntity.Order;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Path: C:\labaratory\Январь\01\Яркие\Плакаты\test 300x400 5.txt
 * _digitOfMonth: 01
 * _typeWork: Плакаты
 * _fileName: test 300x400 5.txt
 * _squareMeters: 0.3 * 0.4 * 5
 */
public class PathToOrder {
    private static final String _digitExp = "[0-9]{1,}.{1}[0-9]{1,}\\s[0-9]{1,}";
    private static final String _splitExp = "\\D";

    private final Order order;

    public PathToOrder(Path path) {
        String _digitOfMonth = getDirNameUpTwoLevel(path);
        String _typeWork = getDirName(path);
        String _fileName = path.getFileName().toString();
        double _squareMeters = getSquareMeters(_fileName);
        order =new Order(_digitOfMonth, _typeWork, _fileName, _squareMeters);
    }

    private String getDirName(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 2).toString();
    }

    private String getDirNameUpTwoLevel(Path path) {
        int temp = path.getNameCount();
        return path.getName(temp - 4).toString();
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

    public Order getSingleCustomersOrder() {
        return order;
    }
}
