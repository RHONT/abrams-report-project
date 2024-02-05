package com.abrams.rules;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static methods for filtering out unnecessary files.
 */
public class Rules {
    private static final String _archivers = "\\.zip|\\.rar";
    private static final String _reservedFiles = "Резервная";

    public static boolean isArchive(Path path) {
        Pattern pattern = Pattern.compile(_archivers);
        Matcher matcher = pattern.matcher(path.toString());
        return matcher.find();
    }

    public static boolean isReservedFile(Path path) {
        Pattern pattern = Pattern.compile(_reservedFiles);
        Matcher matcher = pattern.matcher(path.toString());
        return matcher.find();
    }
}
