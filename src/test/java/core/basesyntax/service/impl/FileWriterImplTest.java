package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class FileWriterImplTest {
    private static final String FILE_PATH = "fileToWrite.csv";
    private static File fileToWrite;
    private BufferedReader reader;

    @BeforeEach
    public void setUp() throws IOException {
        fileToWrite = new File(FILE_PATH);
        if (fileToWrite.exists()) {
            fileToWrite.delete();
        }
        fileToWrite.createNewFile();
        reader = new BufferedReader(new FileReader(FILE_PATH));
    }

    @AfterEach
    public void cleanUp() throws IOException {
        if (reader != null) {
            reader.close();
        }
        fileToWrite.delete();
        fileToWrite.createNewFile();
        reader = new BufferedReader(new FileReader(FILE_PATH));
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
    public void write_Ok() throws IOException {
        String expected = "Some text";
        String actual;
        FileWriter writer = new FileWriterImpl(FILE_PATH);
        writer.write(expected);
        actual = reader.readLine();
        assertEquals(expected, actual);
    }

    @Test
    public void writeMultiline_Ok() throws IOException {
        FileWriter writer = new FileWriterImpl(FILE_PATH);
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            texts.add("element " + i);
        }
        for (String text : texts) {
            writer.write(text + "\n");
        }
        for (String text : texts) {
            assertEquals(text, reader.readLine());
        }
    }

    @Test
    public void writeEmpty_Ok() throws IOException {
        FileWriter writer = new FileWriterImpl(FILE_PATH);
        writer.write("");
        assertTrue(reader.readLine() == null);
    }

    @Test
    public void writeCreatesFile_Ok() throws IOException {
        FileWriter writer = new FileWriterImpl("newFile.csv");
        writer.write("SomeText");
        assertTrue(Files.exists(Paths.get("newFile.csv")));
        Files.delete(Paths.get("newFile.csv"));
    }
}
