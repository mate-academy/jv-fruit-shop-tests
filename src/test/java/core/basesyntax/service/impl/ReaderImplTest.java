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
    private static final String DATA = "line";
    private static final String EMPTY_STRING = "";
    private static final String PATH_TO_FILE_IS_EMPTY_MSG = "Path to file is empty!";
    private static final String FILE_IS_EMPTY_MSG = "File is empty!";
    private static final String CAN_T_READ_FROM_FILE_MSG = "Can't read from file: ";
    private static final String CAN_T_WRITE_TO_FILE_MSG = "Can't write to file: ";
    private final Reader reader = new ReaderImpl();

    @AfterEach
    void tearDown() {
        writeToFile(EMPTY_STRING);
    }

    @Test
    void readFromFile_fromExistingFile_ok() {
        writeToFile(DATA);

        List<String> actualData = reader.readFromFile(TEST_FILE);

        assertLinesMatch(List.of(DATA), actualData);
    }

    @Test
    void readFromFile_readFromEmptyFile_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> reader.readFromFile(TEST_FILE));

        String expectedMessage = FILE_IS_EMPTY_MSG;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readFromFile_readFromNotExistingFile_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> reader.readFromFile(NOT_EXISTING_FILE));

        String expectedMessage = CAN_T_READ_FROM_FILE_MSG + NOT_EXISTING_FILE;
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readFromFile_readNullPath_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> reader.readFromFile(null));

        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    @Test
    void readFromFile_readFromEmptyPath_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> reader.readFromFile(EMPTY_STRING));

        assertEquals(PATH_TO_FILE_IS_EMPTY_MSG, exception.getMessage());
    }

    private void writeToFile(String data) {
        try (FileWriter fileWriter = new FileWriter(TEST_FILE, false)) {
            fileWriter.write(data);
        } catch (Exception e) {
            throw new RuntimeException(CAN_T_WRITE_TO_FILE_MSG + TEST_FILE, e);
        }
    }
}
