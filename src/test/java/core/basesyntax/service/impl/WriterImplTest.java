package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String NOT_EXISTING_FILE = "src/test/resources/notExisting.csv";
    private static final Writer WRITER = new WriterImpl();
    private static final String PATH_TO_FILE_IS_EMPTY_MSG = "Path to file is empty!";
    private static final String DATA = "1,2,3";
    private static final String EMPTY_STRING = "";
    private static final String REPORT_IS_EMPTY_MSG = "Report is empty!";

    @Test
    void writeToFolder_toFilledFile_ok() {
        writer("4,5,6");
        String expected = DATA;
        WRITER.writeToFile(expected, TEST_FILE);
        String actual = reader(TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFolder_writeToEmptyFile_ok() {
        String expected = DATA;
        WRITER.writeToFile(expected, TEST_FILE);
        String actual = reader(TEST_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFolder_writeToNotExistingFile_Ok() {
        String expected = DATA;
        WRITER.writeToFile(expected, NOT_EXISTING_FILE);
        String actual = reader(NOT_EXISTING_FILE);
        assertEquals(expected, actual);
    }

    @Test
    void writeToFolder_writeEmptyData_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> WRITER.writeToFile(EMPTY_STRING, TEST_FILE));
        assertEquals(REPORT_IS_EMPTY_MSG, exception.getMessage());
    }

    @Test
    void writeToFolder_writeNullData_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                    () -> WRITER.writeToFile(null, TEST_FILE));
        assertEquals(REPORT_IS_EMPTY_MSG, exception.getMessage());
    }

    @Test
    void writeToFolder_writeToEmptyPath_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> WRITER.writeToFile(DATA, EMPTY_STRING));
        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    @Test
    void writeToFolder_writeNullPath_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                    () -> WRITER.writeToFile(DATA,null));
        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        writer(EMPTY_STRING);
        try {
            Files.deleteIfExists(Path.of(NOT_EXISTING_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file: " + NOT_EXISTING_FILE, e);
        }
    }

    private String reader(String pathToFile) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(pathToFile))) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + pathToFile, e);
        }
    }

    private void writer(String data) {
        try (FileWriter fileWriter = new FileWriter(TEST_FILE, false)) {
            fileWriter.write(data);
        } catch (Exception e) {
            throw new RuntimeException("Can't write to file: " + TEST_FILE, e);
        }
    }
}
