package core.basesyntax.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String validPathToFile
            = "src/main/java/core/basesyntax/files/activities.csv";
    private static final String invalidPathToFile
            = "src/main/java/core/basesyntax/activities.csv";
    private static final String InvalidPathMessage
            = "Can't read from path: ";
    private static CsvFileReader csvFileReader;

    @BeforeClass
    public static void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    public void readWorkabilityOk() {
        csvFileReader.read(validPathToFile);
    }

    @Test
    public void readInvalidPathNotOk() {
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> csvFileReader.read(invalidPathToFile));
        Assert.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
