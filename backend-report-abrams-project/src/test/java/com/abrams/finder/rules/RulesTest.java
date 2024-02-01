package com.abrams.finder.rules;

import org.junit.jupiter.api.*;

import java.nio.file.Path;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class RulesTest {

    @Test
    void isArchive() {
    }

    @Test
    void isReservedFile() {
        String in="Path\\Резервная копия 100х200 1.txt";
        Path path=Path.of(in);
        Predicate<Path> predicate=Rules::isReservedFile;
        assertTrue(Rules.isReservedFile(path));
        assertTrue(predicate.test(path));
    }
}