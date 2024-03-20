package core.basesyntax.service.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final FileReader READER = new FileReaderImpl();
    private static final List<String> STRINGS_FROM_VALID_FILE =
            List.of("b,banana,20", "b,apple,100", "s,banana,100");
    private static final String VALID_FILE_PATH = "src/test/resources/valid-file.csv";
    private static final String VALID_NOT_CSV_FILE_PATH = "src/test/resources/valid-file.txt";
    private static final String NON_EXISTENT_FILE_PATH = "src/test/resources/non-existent-file.txt";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty-file.csv";

    @Test
    public void readValidFile_ok() {
        Assertions.assertEquals(READER.read(VALID_FILE_PATH), STRINGS_FROM_VALID_FILE);
    }

    @Test
    public void readValidNotCsvFile_ok() {
        Assertions.assertEquals(READER.read(VALID_NOT_CSV_FILE_PATH), STRINGS_FROM_VALID_FILE);
    }

    @Test
    public void readEmptyFile_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> READER.read(EMPTY_FILE_PATH));
        Assertions.assertEquals("The file is too short or empty",
                exception.getMessage());
    }

    @Test
    public void readWithNullPath_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> READER.read(null));
        Assertions.assertEquals("Error: The file path is null!",
                exception.getMessage());
    }

    @Test
    public void readWithNonExistentFile_notOk() {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> READER.read(NON_EXISTENT_FILE_PATH));
        Assertions.assertEquals("Can't read data from file " + NON_EXISTENT_FILE_PATH,
                exception.getMessage());
    }
}
