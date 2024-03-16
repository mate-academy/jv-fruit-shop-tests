package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Reader;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReaderImplTest {
    private static final String TEST_FILE = "src/test/resources/test.csv";
    private static final String NOT_EXISTING_FILE = "src/test/resources/notExisting.csv";
    private static final Reader READER = new ReaderImpl();
    private static final String PATH_TO_FILE_IS_EMPTY_MSG = "Path to file is empty!";
    private static final String DATA = "line";
    private static final String EMPTY_STRING = "";

    @Test
    void readFromFile_fromExistingFile_ok() {
        writer(DATA);
        List<String> actualData = READER.readFromFile(TEST_FILE);
        assertLinesMatch(List.of(DATA), actualData);
    }

    @Test
    void readFromFile_readFromEmptyFile_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> READER.readFromFile(TEST_FILE));
        String expectedMessage = "File is empty!";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readFromFile_readFromNotExistingFile_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> READER.readFromFile(NOT_EXISTING_FILE));
        String expectedMessage = "Can't read from file: " + NOT_EXISTING_FILE;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readFromFile_readNullPath_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> READER.readFromFile(null));
        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    @Test
    void readFromFile_readFromEmptyPath_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> READER.readFromFile(EMPTY_STRING));
        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        writer(EMPTY_STRING);
    }

    private void writer(String data) {
        try (FileWriter fileWriter = new FileWriter(TEST_FILE, false)) {
            fileWriter.write(data);
        } catch (Exception e) {
            throw new RuntimeException("Can't write to file: " + TEST_FILE, e);
        }
    }
}
