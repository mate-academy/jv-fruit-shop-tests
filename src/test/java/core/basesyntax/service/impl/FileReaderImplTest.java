package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final List<String> STRINGS_FROM_VALID_FILE =
            List.of("b,banana,20", "b,apple,100", "s,banana,100");
    private static final String VALID_FILE_PATH = "src/test/resources/valid-file.csv";
    private static final String VALID_NOT_CSV_FILE_PATH = "src/test/resources/valid-file.txt";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/non-existent-file.txt";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-file.csv";
    private FileReader reader;

    @BeforeEach
    public void initReader() {
        reader = new FileReaderImpl();
    }

    @Test
    public void readValidFile_ok() {
        assertEquals(reader.read(VALID_FILE_PATH), STRINGS_FROM_VALID_FILE);
    }

    @Test
    public void readValidNotCsvFile_ok() {
        assertEquals(reader.read(VALID_NOT_CSV_FILE_PATH), STRINGS_FROM_VALID_FILE);
    }

    @Test
    public void readEmptyFile_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> reader.read(EMPTY_FILE_PATH));
        assertEquals("The file is too short or empty",
                exception.getMessage());
    }

    @Test
    public void readWithNullPath_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> reader.read(null));
        assertEquals("Error: The file path is null!",
                exception.getMessage());
    }

    @Test
    public void readWithNonExistentFile_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> reader.read(NON_EXISTENT_FILE_PATH));
        assertEquals("Can't read data from file " + NON_EXISTENT_FILE_PATH,
                exception.getMessage());
    }
}
