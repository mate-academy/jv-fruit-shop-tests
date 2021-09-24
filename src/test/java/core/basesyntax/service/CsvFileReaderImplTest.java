package core.basesyntax.service;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static final String validPathToFile
            = "src/test/java/core/basesyntax/filetest/readTest.csv";
    private static final String invalidPathToFile
            = "src/test/java/core/filetest/readTest.csv";
    private static final String InvalidPathMessage
            = "Can't read from path: ";
    private static CsvFileReader csvFileReader;

    @BeforeClass
    public static void setUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    public void readWorkabilityOk() {
        List<String> expected = List.of("fruit,quantity", "banana,10", "apple,10");
        List<String> actual = csvFileReader.read(validPathToFile);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readInvalidPathNotOk() {
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> csvFileReader.read(invalidPathToFile));
        Assert.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
