package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileReaderImplTest {
    private static final String FILE_PATH = "fileToRead.csv";
    private static File fileToRead;
    FileWriter cleaner;

    @BeforeEach
    public void initialize() {
        try {
            fileToRead = new File(FILE_PATH);
            fileToRead.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cleanUp() {
        try {
            cleaner = new FileWriter(fileToRead, false);
            cleaner.write("");
            cleaner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        fileToRead.delete();
    }

    @Test
    public void readNullPathString_NotOk() {
        FileReader reader = new FileReaderImpl("");
        assertThrows(RuntimeException.class, reader::read);
    }

    @Test
    public void readNonexistentFile_NotOk() {
        javaIoWrite("Some text", FILE_PATH);
        FileReader reader = new FileReaderImpl("wrongFile.csv");
        assertThrows(RuntimeException.class, reader::read);
    }

    @Test
    public void readReturnsTypeList_Ok() {
        javaIoWrite("Some text", FILE_PATH);
        FileReader reader = new FileReaderImpl(FILE_PATH);
        assertInstanceOf(List.class, reader.read());
    }

    @Test
    public void read_Ok() {
        String expected = "Some text";
        javaIoWrite(expected, FILE_PATH);
        FileReader reader = new FileReaderImpl(FILE_PATH);
        String actual = reader.read().get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void readConsistent_Ok() {
        String text = "Some text";
        javaIoWrite(text, FILE_PATH);
        FileReader reader = new FileReaderImpl(FILE_PATH);
        String actual1 = reader.read().get(0);
        String actual2 = reader.read().get(0);
        assertEquals(actual1, actual2);
    }

    @Test
    public void readMultiline_Ok() {
        List<String> texts = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            texts.add("element " + i);
        }
        for (String text : texts) {
            javaIoWrite(text + "\n", FILE_PATH);
        }
        FileReader reader = new FileReaderImpl(FILE_PATH);
        List<String> readTexts = reader.read();
        for (int i = 0; i < texts.size(); ++i) {
            assertEquals(texts.get(i), readTexts.get(i));
        }
    }

    private static void javaIoWrite(String text, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}