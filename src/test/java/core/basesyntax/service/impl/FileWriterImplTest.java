package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileWriterImplTest {
    private static final String FILE_PATH = "fileToWrite.csv";
    private static File fileToWrite;
    private BufferedReader reader;
    java.io.FileWriter cleaner;

    @BeforeEach
    public void setUp() {
        try {
            fileToWrite = new File(FILE_PATH);
            fileToWrite.createNewFile();
            reader = new BufferedReader(new FileReader(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cleanUp() {
        try {
            cleaner = new java.io.FileWriter(fileToWrite, false);
            cleaner.write("");
            cleaner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        fileToWrite.delete();
    }

    @Test
    public void writeToNullPathString_NotOk() {
        FileWriter writer = new FileWriterImpl("");
        assertThrows(RuntimeException.class, () -> writer.write("Some text"));
    }

    @Test
    public void write_Ok() {
        String expected = "Some text";
        String actual;
        FileWriter writer = new FileWriterImpl(FILE_PATH);
        writer.write(expected);
        try {
            actual = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }


    @Test
    public void writeMultiline_Ok() {
        FileWriter writer = new FileWriterImpl(FILE_PATH);
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            texts.add("element " + i);
        }
        for (String text : texts) {
            writer.write(text + "\n");
        }
        for (String text : texts) {
            try {
                assertEquals(text, reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    public void writeEmpty_Ok() {
        try {
            FileWriter writer = new FileWriterImpl(FILE_PATH);
            writer.write("");
            assertTrue(reader.readLine() == null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeCreatesFile_Ok() {
        try {
            FileWriter writer = new FileWriterImpl("newFile.csv");
            writer.write("SomeText");
            assertTrue(Files.exists(Paths.get("newFile.csv")));
            Files.delete(Paths.get("newFile.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
