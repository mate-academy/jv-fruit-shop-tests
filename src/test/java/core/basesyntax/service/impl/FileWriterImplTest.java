package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;

    @BeforeAll
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_pathNotExists_notOk() {
        String path = "usr/home/file.csv";
        String content = "dummy string";
        assertThrows(RuntimeException.class, () -> fileWriter.writeToFile(path, content));
    }

    @Test
    public void writeToFile_fileNotExists_ok() {
        String path = "src/test/resources/test.csv";
        String content = "test,test,test";
        assertDoesNotThrow(() -> fileWriter.writeToFile(path, content));
        File file = new File(path);
        assertTrue(file.exists());
        assertTrue(file.delete());
        assertFalse(file.exists());
    }

    @Test
    public void writeToFile_emptyContents_ok() {
        String path = "src/test/resources/testreport.csv";
        String content = "";
        assertDoesNotThrow(() -> fileWriter.writeToFile(path, content));
        File file = new File(path);
        assertTrue(file.exists());
        String actual;
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            actual = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNull(actual);
    }

    @Test
    public void writeToFile_nullContents_notOk() {
        String path = "src/test/resources/testreport.csv";
        String content = null;
        assertThrows(NullPointerException.class, () -> fileWriter.writeToFile(path, content));
    }

    @Test
    public void writeToFile_regularCase_ok() {
        String path = "src/test/resources/testreport.csv";
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertDoesNotThrow(() -> fileWriter.writeToFile(path, expected));
        File file = new File(path);
        assertTrue(file.exists());
        String actual = "";
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                actual += line + System.lineSeparator();
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }
}
