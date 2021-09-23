package core.basesyntax.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderTest {
    private static final String validPathToFile
            = "src/main/java/core/basesyntax/files/activities.csv";
    private static final String invalidPathToFile
            = "src/main/java/core/basesyntax/activities.csv";
    private static final String InvalidPathMessage
            = "Can't read from path: ";
    private CsvFileReader csvFileReader;

    @BeforeEach
    void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    void readWorkabilityOk() {
        csvFileReader.read(validPathToFile);
    }

    @Test
    void readInvalidPathNotOk() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> csvFileReader.read(invalidPathToFile));
        Assertions.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
