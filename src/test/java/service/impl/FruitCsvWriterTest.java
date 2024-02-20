package service.impl;

import org.junit.jupiter.api.Test;
import service.Parser;
import service.Writer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FruitCsvWriterTest {
    private static final Writer fruitWriter = new FruitCsvWriter();
    private static final String PATH_FOR_WRITER = "src/main/resources/";
    private static final String TEST_WRITE_FILE_NAME = "TestWriterFile.csv";
    private static final String TEST_TEXT = "Example for Test Writer";

    @Test
    void write_Ok() {
        fruitWriter.write(TEST_TEXT, TEST_WRITE_FILE_NAME);
        String actual = null;
        try {
            actual = Files.readString(Path.of(PATH_FOR_WRITER + TEST_WRITE_FILE_NAME));
        } catch (IOException e) {
            fail("Cant read " + TEST_TEXT + " from " + TEST_WRITE_FILE_NAME);
        }
        String expected = TEST_TEXT;
        assertEquals(actual, TEST_TEXT);
    }

    @Test
    void write_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitWriter.write(null, TEST_WRITE_FILE_NAME);
        });

        assertThrows(RuntimeException.class, () -> {
            fruitWriter.write(null,null);
        });
    }
}