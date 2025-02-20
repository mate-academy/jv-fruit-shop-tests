package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void correctWritingToFileIfTextIsEmpty_Ok() {
        String fileName = "fileToTest.csv";
        String text = "";

        fileWriter.writeToFile(fileName, text);
        List<String> expected = new ArrayList<>();

        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data from file " + fileName);
        }

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void correctWritingToFileIfTextIsNotEmpty_Ok() {
        String fileName = "file.csv";
        String text = "Hello";

        fileWriter.writeToFile(fileName, text);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + fileName);
        }

        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + fileName);
        }

        assertEquals(expected, actual);
    }

    @Test
    public void fileFound_Ok() {
        String fileName = "fileToTest.csv";
        String text = "";

        assertDoesNotThrow(() -> fileWriter.writeToFile(fileName, text));
    }

    @Test
    public void fileFound_NotOk() {
        String fileName = "";
        String text = "";

        assertThrows(RuntimeException.class, () -> {
            fileWriter.writeToFile(fileName,text);
        });
    }
}
